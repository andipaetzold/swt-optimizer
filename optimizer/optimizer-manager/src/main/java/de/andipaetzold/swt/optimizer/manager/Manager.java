package de.andipaetzold.swt.optimizer.manager;

import java.util.ArrayList;
import java.util.List;

import de.andipaetzold.swt.optimizer.optimizerbase.Optimizer;
import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerFactory;
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

        // send to listeners
        for (FrontendInterface frontend : frontends) {
            frontend.addOptimizer(optimizerFactory.getOptimizerType());
        }
    }

    public void removeOptimizer(OptimizerFactory optimizerFactory) {
        optimizers.remove(optimizerFactory);

        // send to listeners
        for (FrontendInterface frontend : frontends) {
            frontend.removeOptimizer(optimizerFactory.getOptimizerType());
        }

    }

    /// Status Listener ///
    public void addFrontend(FrontendInterface frontend) {
        frontends.add(frontend);

        // add all optimizers to list
        for (OptimizerFactory optimizer : optimizers) {
            frontend.addOptimizer(optimizer.getOptimizerType());
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
    private List<Thread> optimizerThreads = new ArrayList<>();

    @Override
    public void optimize(double value) {
        for (Thread thread : optimizerThreads) {
            if (thread.isAlive()) {
                return;
            }
        }

        optimizerThreads.clear();
        for (OptimizerFactory factory : optimizers) {
            Thread t = new Thread(() -> {
                Optimizer optimizer = factory.createOptimizer();

                // set result to null
                for (FrontendInterface frontend : frontends) {
                    frontend.setOptimizerResult(optimizer.getOptimizerType(), null);
                }

                // add listener and start optimizing
                optimizer.addOptimizerListener(this);
                optimizer.optimize(value);
            });
            optimizerThreads.add(t);
            t.start();
        }
    }

    @Override
    public void handleOptimizerStatusChanged(OptimizerStatusChangedEvent event) {
        for (FrontendInterface frontend : frontends) {
            frontend.setOptimizerStatus(event.getSource().getOptimizerType(), event.getStatus());
        }
    }

    @Override
    public void handleOptimizerResult(OptimizerResultEvent event) {
        for (FrontendInterface frontend : frontends) {
            frontend.setOptimizerResult(event.getSource().getOptimizerType(), event.getResult());
        }
    }

}
