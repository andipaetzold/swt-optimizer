package de.andipaetzold.swt.optimizer.manager;

public interface StatusListener {
    public void setProgress(double value);

    public void setStatus(String value);

    public void addOptimizer(String optimizer);

    public void removeOptimizer(String optimizer);
}
