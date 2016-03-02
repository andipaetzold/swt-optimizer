package de.andipaetzold.swt.optimizer.optimizerone;

import java.util.Random;

import de.andipaetzold.swt.optimizer.optimizerbase.AbstractOptimizer;
import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerFactory;

public class OptimizerOne extends AbstractOptimizer {
    public OptimizerOne(OptimizerFactory optimizerFactory) {
        super(optimizerFactory);
    }

    public double optimize(double value) {
    	try {
			Thread.sleep(new Random().nextInt(5000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	return new Random().nextDouble();
    }
}
