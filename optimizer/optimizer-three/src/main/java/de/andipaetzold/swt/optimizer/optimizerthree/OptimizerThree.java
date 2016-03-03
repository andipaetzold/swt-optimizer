package de.andipaetzold.swt.optimizer.optimizerthree;

import java.util.Random;

import de.andipaetzold.swt.optimizer.optimizerbase.AbstractOptimizer;
import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerFactory;
import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerStatus;

public class OptimizerThree extends AbstractOptimizer {
    public OptimizerThree(OptimizerFactory optimizerFactory) {
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
