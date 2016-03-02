package de.andipaetzold.swt.optimizer.manager;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import de.andipaetzold.swt.optimizer.optimizerbase.*;

public class OptimizerTrackerCustomizer implements ServiceTrackerCustomizer<OptimizerFactory, OptimizerFactory> {
	private BundleContext bundleContext;

	public OptimizerTrackerCustomizer(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}
	
	@Override
	public OptimizerFactory addingService(ServiceReference<OptimizerFactory> reference) {
		OptimizerFactory of = bundleContext.getService(reference);
		System.out.println("added");
		// TODO: adding
		return of;
	}

	@Override
	public void modifiedService(ServiceReference<OptimizerFactory> reference, OptimizerFactory service) {
		System.out.println("modified");
		// TODO: modified		
	}

	@Override
	public void removedService(ServiceReference<OptimizerFactory> reference, OptimizerFactory service) {
		System.out.println("removed");
		// TODO: removed
	}

}
