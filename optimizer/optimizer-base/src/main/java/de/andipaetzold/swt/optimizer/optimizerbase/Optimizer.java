package de.andipaetzold.swt.optimizer.optimizerbase;

public interface Optimizer {
    public String getOptimizerType();

    public double optimize(double value);
}
