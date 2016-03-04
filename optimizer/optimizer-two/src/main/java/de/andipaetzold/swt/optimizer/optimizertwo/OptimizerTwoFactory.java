package de.andipaetzold.swt.optimizer.optimizertwo;

import org.osgi.service.event.EventAdmin;

import de.andipaetzold.swt.optimizer.optimizerbase.Optimizer;
import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerFactory;

public class OptimizerTwoFactory implements OptimizerFactory {
    private EventAdmin eventAdmin;

    public OptimizerTwoFactory(EventAdmin eventAdmin) {
        this.eventAdmin = eventAdmin;
    }

    @Override
    public Optimizer createOptimizer() {
        return new OptimizerTwo(eventAdmin, this);
    }

    @Override
    public String getOptimizerType() {
        return "Two";
    }
}
