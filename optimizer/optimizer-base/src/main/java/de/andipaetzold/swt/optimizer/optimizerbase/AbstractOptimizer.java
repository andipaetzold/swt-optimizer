package de.andipaetzold.swt.optimizer.optimizerbase;

public abstract class AbstractOptimizer implements Optimizer {
    private final OptimizerFactory optimizerFactory;

    public AbstractOptimizer(OptimizerFactory optimizerFactory) {
        this.optimizerFactory = optimizerFactory;
    }

    public String getOptimizerType() {
        return optimizerFactory.getOptimizerType();
    }
}
