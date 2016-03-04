package de.andipaetzold.swt.optimizer.optimizerone;

import org.osgi.service.event.EventAdmin;

import de.andipaetzold.swt.optimizer.optimizerbase.Optimizer;
import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerFactory;

public class OptimizerOneFactory implements OptimizerFactory {
    private EventAdmin eventAdmin;

    public OptimizerOneFactory(EventAdmin eventAdmin) {
        this.eventAdmin = eventAdmin;
    }

    @Override
    public Optimizer createOptimizer() {
        return new OptimizerOne(eventAdmin, this);
    }

    @Override
    public String getOptimizerType() {
        return "Two";
    }
}
