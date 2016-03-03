package de.andipaetzold.swt.optimizer.optimizertwo;

import java.util.Random;

import de.andipaetzold.swt.optimizer.optimizerbase.AbstractOptimizer;
import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerStatus;
import de.andipaetzold.swt.optimizer.optimizerbase.factory.OptimizerFactory;

public class OptimizerTwo extends AbstractOptimizer {
    public OptimizerTwo(OptimizerFactory optimizerFactory) {
        super(optimizerFactory);
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
