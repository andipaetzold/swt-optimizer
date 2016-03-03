package de.andipaetzold.swt.optimizer.optimizerbase;

import de.andipaetzold.swt.optimizer.optimizerbase.event.OptimizerListener;
import de.andipaetzold.swt.optimizer.optimizerbase.factory.OptimizerFactory;

public interface Optimizer {
    public String getOptimizerType();

    public void optimize(double value);

    public double getResult();

    public OptimizerStatus getStatus();

    public void addOptimizerListener(OptimizerListener listener);

    public void removeOptimizerListener(OptimizerListener listener);

    public OptimizerFactory getFactory();
}
