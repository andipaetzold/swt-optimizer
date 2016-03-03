package de.andipaetzold.swt.optimizer.optimizerbase;

import java.util.ArrayList;
import java.util.List;

import de.andipaetzold.swt.optimizer.optimizerbase.event.OptimizerListener;
import de.andipaetzold.swt.optimizer.optimizerbase.event.OptimizerResultEvent;
import de.andipaetzold.swt.optimizer.optimizerbase.event.OptimizerStatusChangedEvent;
import de.andipaetzold.swt.optimizer.optimizerbase.factory.OptimizerFactory;

public abstract class AbstractOptimizer implements Optimizer {

    public AbstractOptimizer(OptimizerFactory optimizerFactory) {
        this.factory = optimizerFactory;
    }

    /// Factory ///
    private final OptimizerFactory factory;

    @Override
    public String getOptimizerType() {
        return factory.getOptimizerType();
    }

    @Override
    public OptimizerFactory getFactory() {
        return factory;
    }

    /// Listeners ///
    private List<OptimizerListener> listeners = new ArrayList<>();

    @Override
    public void addOptimizerListener(OptimizerListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeOptimizerListener(OptimizerListener listener) {
        listeners.remove(listener);
    }

    /// Result ///
    private double result = 0;

    @Override
    public double getResult() {
        return result;
    }

    protected void setResult(double result) {
        this.result = result;

        for (OptimizerListener listener : listeners) {
            listener.handleOptimizerResult(new OptimizerResultEvent(this, result));
        }
    }

    /// Status ///
    private OptimizerStatus status;

    @Override
    public OptimizerStatus getStatus() {
        return status;
    }

    protected void setStatus(OptimizerStatus status) {
        this.status = status;

        for (OptimizerListener listener : listeners) {
            listener.handleOptimizerStatusChanged(new OptimizerStatusChangedEvent(this, status));
        }
    }
}
