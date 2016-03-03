package de.andipaetzold.swt.optimizer.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.andipaetzold.swt.optimizer.optimizerbase.Optimizer;
import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerFactory;
import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerStatus;
import de.andipaetzold.swt.optimizer.optimizerbase.event.OptimizerListener;
import de.andipaetzold.swt.optimizer.optimizerbase.event.OptimizerResultEvent;
import de.andipaetzold.swt.optimizer.optimizerbase.event.OptimizerStatusChangedEvent;

public class Manager implements OptimizeMethod, OptimizerListener {
    private List<OptimizerFactory> optimizers = new ArrayList<>();
    private List<FrontendInterface> frontends = new ArrayList<>();

    public Manager() {
    }

    /// Optimizer ///
    public void addOptimizer(OptimizerFactory optimizerFactory) {
        optimizers.add(optimizerFactory);

        // update listeners
        for (FrontendInterface frontend : frontends) {
            frontend.addOptimizer(optimizerFactory.getOptimizerType());
        }
    }

    public void removeOptimizer(OptimizerFactory optimizerFactory) {
        optimizers.remove(optimizerFactory);

        // update listeners
        for (FrontendInterface frontend : frontends) {
            frontend.removeOptimizer(optimizerFactory.getOptimizerType());
        }
    }

    /// Status ///
    private void setStatus(String status, double progress) {
        for (FrontendInterface listener : frontends) {
            listener.setStatus(status);
            listener.setProgress(progress);
        }
    }

    /// Status Listener ///
    public void addFrontend(FrontendInterface frontend) {
        frontends.add(frontend);

        // add all optimizers to list
        for (OptimizerFactory optimizer : optimizers) {
            frontend.addOptimizer(optimizer.getOptimizerType());
        }

        // set status
        frontend.setStatus("ready");
        frontend.setProgress(0);

        // set optimizer method
        frontend.setOptimizeMethod(this);

    }

    public void removeFrontend(FrontendInterface frontend) {
        frontends.remove(frontend);

        // remove optimizer method
        frontend.setOptimizeMethod(null);
    }

    /// Optimize ///
    private OptimizerStatus status = OptimizerStatus.WAITING;

    @Override
    public void optimize(double value) {
        if (status != OptimizerStatus.WAITING) {
            return;
        }

        setStatus("optimizing...", 0);
        results = new HashMap<>();

        for (OptimizerFactory factory : optimizers) {
            new Thread(() -> {
                Optimizer optimizer = factory.createOptimizer();
                optimizer.addOptimizerListener(this);
                optimizer.optimize(value);
            }).start();

        }
    }

    private Map<String, Double> results;

    @Override
    public void handleOptimizerStatusChanged(OptimizerStatusChangedEvent event) {
    }

    @Override
    public void handleOptimizerResult(OptimizerResultEvent event) {
        results.put(event.getSource().getOptimizerType(), event.getResult());

        setStatus("optimizing...", Math.min(1, results.size() / (double) optimizers.size()));

        if (results.size() == optimizers.size()) {
            setStatus("done", 1);
            for (FrontendInterface frontend : frontends) {
                frontend.handleResults(results);
            }
            status = OptimizerStatus.WAITING;
        }
    }

}
