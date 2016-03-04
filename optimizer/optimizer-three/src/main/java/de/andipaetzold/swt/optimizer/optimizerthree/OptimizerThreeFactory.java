package de.andipaetzold.swt.optimizer.optimizerthree;

import org.osgi.service.event.EventAdmin;

import de.andipaetzold.swt.optimizer.optimizerbase.Optimizer;
import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerFactory;

public class OptimizerThreeFactory implements OptimizerFactory {
    private EventAdmin eventAdmin;

    public OptimizerThreeFactory(EventAdmin eventAdmin) {
        this.eventAdmin = eventAdmin;
    }

    @Override
    public Optimizer createOptimizer() {
        return new OptimizerThree(eventAdmin, this);
    }

    @Override
    public String getOptimizerType() {
        return "Two";
    }
}
