package de.andipaetzold.swt.optimizer.optimizerbase.event;

import de.andipaetzold.swt.optimizer.optimizerbase.Optimizer;
import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerStatus;

public class OptimizerStatusChangedEvent {
    private Optimizer source;
    private OptimizerStatus status;

    public OptimizerStatusChangedEvent(Optimizer source, OptimizerStatus status) {
        setSource(source);
        setStatus(status);
    }

    public Optimizer getSource() {
        return source;
    }

    private void setSource(Optimizer source) {
        this.source = source;
    }

    public OptimizerStatus getStatus() {
        return status;
    }

    private void setStatus(OptimizerStatus status) {
        this.status = status;
    }
}
