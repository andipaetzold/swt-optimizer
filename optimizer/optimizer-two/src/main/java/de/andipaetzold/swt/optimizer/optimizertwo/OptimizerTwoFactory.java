package de.andipaetzold.swt.optimizer.optimizertwo;

import de.andipaetzold.swt.optimizer.optimizerbase.Optimizer;
import de.andipaetzold.swt.optimizer.optimizerbase.factory.AbstractOptimizerFactory;

public class OptimizerTwoFactory extends AbstractOptimizerFactory {
    @Override
    public Optimizer createOptimizer() {
        return new OptimizerTwo(this);
    }

    @Override
    public String getOptimizerType() {
        return "Two";
    }
}
