package de.andipaetzold.swt.optimizer.optimizerbase;

import de.andipaetzold.swt.optimizer.optimizerbase.event.OptimizerListener;

public interface Optimizer {
    public String getOptimizerType();

    public void optimize(double value);

    public Double getResult();

    public OptimizerStatus getStatus();

    public void addOptimizerListener(OptimizerListener listener);

    public void removeOptimizerListener(OptimizerListener listener);
}
