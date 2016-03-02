package de.andipaetzold.swt.optimizer.optimizerbase;

public interface OptimizerFactory {
    public Optimizer createOptimizer();

    public String getOptimizerType();
}
