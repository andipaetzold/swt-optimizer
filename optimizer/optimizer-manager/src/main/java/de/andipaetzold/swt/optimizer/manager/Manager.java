package de.andipaetzold.swt.optimizer.manager;

import java.util.ArrayList;
import java.util.List;

import de.andipaetzold.swt.optimizer.optimizerbase.Optimizer;
import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerFactory;
import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerStatus;
import de.andipaetzold.swt.optimizer.optimizerbase.event.OptimizerListener;
import de.andipaetzold.swt.optimizer.optimizerbase.event.OptimizerResultEvent;
import de.andipaetzold.swt.optimizer.optimizerbase.event.OptimizerStatusChangedEvent;

public class Manager implements OptimizeMethod, OptimizerListener {
    private final List<OptimizerFactory> factories = new ArrayList<>();

    private final List<FrontendInterface> frontends = new ArrayList<>();

    /// Optimizer ///
    public void addOptimizer(OptimizerFactory factory) {
        // add
        factories.add(factory);

        // send to listeners
        for (FrontendInterface frontend : frontends) {
            frontend.addOptimizer(factory.getOptimizerType());
        }

        // start if running
        if (isRunning()) {
            startOptimizer(factory);
        }
    }

    public void removeOptimizer(OptimizerFactory factory) {
        // remove
        factories.remove(factory);

        // send to listeners
        for (FrontendInterface frontend : frontends) {
            frontend.removeOptimizer(factory.getOptimizerType());
        }
    }

    /// Status Listener ///
    public void addFrontend(FrontendInterface frontend) {
        frontends.add(frontend);

        // add all optimizers to list
        for (OptimizerFactory factory : factories) {
            frontend.addOptimizer(factory.getOptimizerType());
        }

        // set optimizer status
        for (Optimizer optimizer : optimizers) {
            frontend.setOptimizerStatus(optimizer.getOptimizerType(), optimizer.getStatus());
            frontend.setOptimizerResult(optimizer.getOptimizerType(), optimizer.getResult());
        }

        // set optimizer method
        frontend.setOptimizeMethod(this);

    }

    public void removeFrontend(FrontendInterface frontend) {
        frontends.remove(frontend);

        // remove optimizer method
        frontend.setOptimizeMethod(null);
    }

    /// Optimize ///
    private final List<Optimizer> optimizers = new ArrayList<>();
    private double startValue;

    @Override
    public void optimize(double value) {
        if (isRunning()) {
            return;
        }

        optimizers.clear();

        startValue = value;
        for (OptimizerFactory factory : factories) {
            startOptimizer(factory);
        }
    }

    private void startOptimizer(OptimizerFactory factory) {
        // set result to null
        for (FrontendInterface frontend : frontends) {
            frontend.setOptimizerResult(factory.getOptimizerType(), null);
        }

        // create optimizer
        Optimizer optimizer = factory.createOptimizer();
        optimizer.addOptimizerListener(this);
        optimizers.add(optimizer);

        // optimize
        new Thread(() -> {
            optimizer.optimize(startValue);
        }).start();
    }

    /// Handle Optimizer///
    @Override
    public void handleOptimizerStatusChanged(OptimizerStatusChangedEvent event) {
        // send status
        for (FrontendInterface frontend : frontends) {
            frontend.setOptimizerStatus(event.getSource().getOptimizerType(), event.getStatus());
        }
    }

    @Override
    public void handleOptimizerResult(OptimizerResultEvent event) {
        // send result
        for (FrontendInterface frontend : frontends) {
            frontend.setOptimizerResult(event.getSource().getOptimizerType(), event.getResult());
        }
    }

    /// Running ///
    private boolean isRunning() {
        for (Optimizer optimizer : optimizers) {
            if (optimizer.getStatus() == OptimizerStatus.RUNNING) {
                return true;
            }
        }
        return false;
    }
}
