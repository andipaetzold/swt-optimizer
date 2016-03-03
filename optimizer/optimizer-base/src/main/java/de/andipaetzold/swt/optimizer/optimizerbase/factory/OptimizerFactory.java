package de.andipaetzold.swt.optimizer.optimizerbase.factory;

import de.andipaetzold.swt.optimizer.optimizerbase.Optimizer;

public interface OptimizerFactory {
    public Optimizer createOptimizer();

    public String getOptimizerType();
}
