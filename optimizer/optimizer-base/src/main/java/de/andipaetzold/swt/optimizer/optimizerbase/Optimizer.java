package de.andipaetzold.swt.optimizer.optimizerbase;

public interface Optimizer {
    public String getOptimizerType();

    public void optimize(double value);

    public Double getResult();

    public OptimizerStatus getStatus();

    public void resend();
}
