package de.andipaetzold.swt.optimizer.manager;

import java.util.ArrayList;
import java.util.List;

import de.andipaetzold.swt.optimizer.optimizerbase.Optimizer;
import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerFactory;

public class Manager {
    private List<OptimizerFactory> optimizers = new ArrayList<>();
    private List<FrontendInterface> listeners = new ArrayList<>();

    public Manager() {
    }

    public void addOptimizer(OptimizerFactory optimizerFactory) {
        optimizers.add(optimizerFactory);

        // update listeners
        for (FrontendInterface listener : listeners) {
            listener.addOptimizer(optimizerFactory.getOptimizerType());
        }
    }

    public void removeOptimizer(OptimizerFactory optimizerFactory) {
        optimizers.remove(optimizerFactory);

        // update listeners
        for (FrontendInterface listener : listeners) {
            listener.removeOptimizer(optimizerFactory.getOptimizerType());
        }
    }

    public double optimize(double value) {
        int cur = 0;
        int max = optimizers.size();
        for (OptimizerFactory optimizer : optimizers) {
            setStatus(optimizer.getOptimizerType() + "...", cur / max);
            value = optimizer.createOptimizer().optimize(value);
        }
        setStatus("Done", 1);
        return value;
    }

    private void setStatus(String status, double progress) {
        for (FrontendInterface listener : listeners) {
            listener.setStatus(status);
            listener.setProgress(progress);
        }
    }

    public void addStatusListener(FrontendInterface listener) {
        listeners.add(listener);

        // add all optimizers to list
        for (OptimizerFactory optimizer : optimizers) {
            listener.addOptimizer(optimizer.getOptimizerType());
        }

        // set status
        listener.setStatus("Ready");
        listener.setProgress(0);

        // set optimizer method
        Optimizer optimizer = new Optimizer() {
            @Override
            public double optimize(double value) {
                return Manager.this.optimize(value);
            }

            @Override
            public String getOptimizerType() {
                return null;
            }
        };
        listener.setOptimizeMethod(optimizer);

    }

    public void removeStatusListener(FrontendInterface listener) {
        listeners.remove(listener);

        // remove optimizer method
        listener.setOptimizeMethod(null);
    }
}
