package de.andipaetzold.swt.optimizer.manager;

import java.util.ArrayList;
import java.util.List;

import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerFactory;

public class Manager {
    private List<OptimizerFactory> optimizers = new ArrayList<>();
    private List<StatusListener> listeners = new ArrayList<>();

    public Manager() {
    }

    public void addOptimizer(OptimizerFactory optimizerFactory) {
        optimizers.add(optimizerFactory);

        // update listeners
        for (StatusListener listener : listeners) {
            System.out.println(listener);
            listener.addOptimizer(optimizerFactory.getOptimizerType());
        }
    }

    public void removeOptimizer(OptimizerFactory optimizerFactory) {
        optimizers.remove(optimizerFactory);

        // update listeners
        for (StatusListener listener : listeners) {
            listener.removeOptimizer(optimizerFactory.getOptimizerType());
        }
    }

    public double optimize(double value) {
        for (OptimizerFactory optimizer : optimizers) {
            value = optimizer.createOptimizer().optimize(value);
        }
        return value;
    }

    public void addStatusListener(StatusListener listener) {
        listeners.add(listener);
    }

    public void removeStatusListener(StatusListener listener) {
        listeners.remove(listener);
    }
}
