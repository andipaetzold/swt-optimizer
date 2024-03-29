package de.andipaetzold.swt.optimizer.optimizerone;

import java.util.Random;

import org.osgi.service.event.EventAdmin;

import de.andipaetzold.swt.optimizer.optimizerbase.AbstractOptimizer;
import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerFactory;
import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerStatus;

public class OptimizerOne extends AbstractOptimizer {
    public OptimizerOne(EventAdmin eventAdmin, OptimizerFactory optimizerFactory) {
        super(eventAdmin, optimizerFactory);
    }

    @Override
    public void optimize(double value) {
        setStatus(OptimizerStatus.RUNNING);

        try {
            Thread.sleep(1000 + new Random().nextInt(5000));
        } catch (InterruptedException e) {
        }

        setResult(value * new Random().nextDouble());
        setStatus(OptimizerStatus.FINISHED);
    }
}
