package de.andipaetzold.swt.optimizer.optimizerthree;

import de.andipaetzold.swt.optimizer.optimizerbase.Optimizer;
import de.andipaetzold.swt.optimizer.optimizerbase.factory.AbstractOptimizerFactory;

public class OptimizerThreeFactory extends AbstractOptimizerFactory {
    @Override
    public Optimizer createOptimizer() {
        return new OptimizerThree(this);
    }

    @Override
    public String getOptimizerType() {
        return "Three";
    }
}
