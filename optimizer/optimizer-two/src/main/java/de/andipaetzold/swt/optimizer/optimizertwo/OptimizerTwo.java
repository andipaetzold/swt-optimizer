package de.andipaetzold.swt.optimizer.optimizertwo;

import java.util.Random;

import org.osgi.service.event.EventAdmin;

import de.andipaetzold.swt.optimizer.optimizerbase.AbstractOptimizer;
import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerFactory;
import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerStatus;

public class OptimizerTwo extends AbstractOptimizer {
    public OptimizerTwo(EventAdmin eventAdmin, OptimizerFactory optimizerFactory) {
        super(eventAdmin, optimizerFactory);
    }

    @Override
    public void optimize(double value) {
        setStatus(OptimizerStatus.RUNNING);

        try {
            Thread.sleep(2000 + new Random().nextInt(10000));
        } catch (InterruptedException e) {
        }

        setResult(value * new Random().nextDouble());
        setStatus(OptimizerStatus.FINISHED);
    }
}
