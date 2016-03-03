package de.andipaetzold.swt.optimizer.manager;

import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerStatus;

public interface FrontendInterface {
    public void setOptimizerResult(String optimizer, Double result);

    public void setOptimizerStatus(String optimizer, OptimizerStatus status);

    public void addOptimizer(String optimizer);

    public void removeOptimizer(String optimizer);

    public void setOptimizeMethod(OptimizeMethod optimizeMethod);
}
