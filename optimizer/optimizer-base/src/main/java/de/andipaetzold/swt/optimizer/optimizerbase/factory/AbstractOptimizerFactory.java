package de.andipaetzold.swt.optimizer.optimizerbase.factory;

public abstract class AbstractOptimizerFactory implements OptimizerFactory {
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof OptimizerFactory) {
            OptimizerFactory factory = (OptimizerFactory) obj;
            return (factory.getOptimizerType().equals(this.getOptimizerType()));
        }
        return false;
    }
}
