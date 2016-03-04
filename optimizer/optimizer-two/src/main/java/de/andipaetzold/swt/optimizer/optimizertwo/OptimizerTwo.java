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
            Thread.sleep(new Random().nextInt(5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setResult(new Random().nextDouble());
        setStatus(OptimizerStatus.FINISHED);
    }
}
