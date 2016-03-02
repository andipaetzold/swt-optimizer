package de.andipaetzold.swt.optimizer.optimizerone;

import de.andipaetzold.swt.optimizer.optimizerbase.Optimizer;
import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerFactory;

public class OptimizerOneFactory implements OptimizerFactory {
    @Override
    public Optimizer createOptimizer() {
        return new OptimizerOne(this);
    }

    @Override
    public String getOptimizerType() {
        return "One";
    }
}
