package de.andipaetzold.swt.optimizer.manager;

import java.util.ArrayList;
import java.util.List;

import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerFactory;

public class Manager {
    private List<OptimizerFactory> optimizers = new ArrayList<>();

    public Manager() {
    }

    public void addOptimizer(OptimizerFactory optimizerFactory) {
        optimizers.add(optimizerFactory);
    }

    public void removeOptimizer(OptimizerFactory optimizerFactory) {
        optimizers.remove(optimizerFactory);
    }

    public double optimize(double value) {
        for (OptimizerFactory optimizer : optimizers) {
            value = optimizer.createOptimizer().optimize(value);
        }
        return value;
    }
}
