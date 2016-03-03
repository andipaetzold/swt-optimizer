package de.andipaetzold.swt.optimizer.manager;

import de.andipaetzold.swt.optimizer.optimizerbase.Optimizer;

public interface FrontendInterface {
    public void setOptimizeMethod(Optimizer optimizer);

    public void setProgress(double value);

    public void setStatus(String value);

    public void addOptimizer(String optimizer);

    public void removeOptimizer(String optimizer);
}
