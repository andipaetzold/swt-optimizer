package de.andipaetzold.swt.optimizer.optimizerbase.event;

import java.util.EventListener;

public interface OptimizerListener extends EventListener {
    public void handleOptimizerStatusChanged(OptimizerStatusChangedEvent event);

    public void handleOptimizerResult(OptimizerResultEvent event);
}
