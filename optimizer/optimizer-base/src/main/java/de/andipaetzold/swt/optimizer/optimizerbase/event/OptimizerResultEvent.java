package de.andipaetzold.swt.optimizer.optimizerbase.event;

import de.andipaetzold.swt.optimizer.optimizerbase.Optimizer;

public class OptimizerResultEvent {
    private Optimizer source;
    private double result;

    public OptimizerResultEvent(Optimizer source, double result) {
        setSource(source);
        setResult(result);
    }

    public Optimizer getSource() {
        return source;
    }

    private void setSource(Optimizer source) {
        this.source = source;
    }

    public double getResult() {
        return result;
    }

    private void setResult(double result) {
        this.result = result;
    }
}
