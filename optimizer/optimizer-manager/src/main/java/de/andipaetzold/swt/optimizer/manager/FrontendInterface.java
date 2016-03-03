package de.andipaetzold.swt.optimizer.manager;

import java.util.Map;

public interface FrontendInterface {
    public void setProgress(double value);

    public void setStatus(String value);

    public void addOptimizer(String optimizer);

    public void removeOptimizer(String optimizer);

    public void setOptimizeMethod(OptimizeMethod optimizeMethod);

    public void handleResults(Map<String, Double> results);
}
