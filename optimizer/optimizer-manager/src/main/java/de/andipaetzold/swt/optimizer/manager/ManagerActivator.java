package de.andipaetzold.swt.optimizer.manager;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.util.tracker.ServiceTracker;

import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerFactory;

public class ManagerActivator implements BundleActivator {

	private ServiceTracker<OptimizerFactory, OptimizerFactory> optimizerTracker;
	
	@Override
	public void start(BundleContext context) throws Exception {
		optimizerTracker = new ServiceTracker<>(context, OptimizerFactory.class, new OptimizerTrackerCustomizer(context));
		optimizerTracker.open();

	}

	@Override
	public void stop(BundleContext context) throws Exception {
		optimizerTracker.close();
	}

}
