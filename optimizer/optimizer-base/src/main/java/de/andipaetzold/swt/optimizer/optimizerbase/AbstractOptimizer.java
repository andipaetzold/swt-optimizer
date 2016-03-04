package de.andipaetzold.swt.optimizer.optimizerbase;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

public abstract class AbstractOptimizer implements Optimizer {
    private EventAdmin eventAdmin;

    public AbstractOptimizer(EventAdmin eventAdmin, OptimizerFactory optimizerFactory) {
        this.eventAdmin = eventAdmin;
        this.factory = optimizerFactory;

        setResult(null);
    }

    /// Factory ///
    private final OptimizerFactory factory;

    @Override
    public String getOptimizerType() {
        return factory.getOptimizerType();
    }

    /// Result ///
    private Double result = null;

    @Override
    public Double getResult() {
        return result;
    }

    protected void setResult(Double result) {
        this.result = result;

        // send result
        Dictionary<String, Object> props = new Hashtable<>();
        props.put("optimizer", getOptimizerType());
        if (result != null) {
            props.put("result", result);
        }
        eventAdmin.sendEvent(new Event("de/andipaetzold/swt/optimizer/frontend/RESULT", props));
    }

    /// Status ///
    private OptimizerStatus status;

    @Override
    public OptimizerStatus getStatus() {
        return status;
    }

    protected void setStatus(OptimizerStatus status) {
        this.status = status;

        // send status
        Dictionary<String, Object> props = new Hashtable<>();
        props.put("optimizer", getOptimizerType());
        props.put("status", status);
        eventAdmin.sendEvent(new Event("de/andipaetzold/swt/optimizer/frontend/STATUS", props));
    }

    /// Resend ///
    @Override
    public void resend() {
        setResult(result);
        setStatus(status);
    }
}
