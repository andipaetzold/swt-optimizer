package de.andipaetzold.swt.optimizer.optimizerone;

import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerFactory;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class OptimizerOneActivator implements BundleActivator {
	private ServiceRegistration<OptimizerFactory> registration;
	
    @Override
    public void start(BundleContext bundleContext) throws Exception {
    	System.out.println("Register Optimizer One");
        registration = bundleContext.registerService(OptimizerFactory.class, new OptimizerOneFactory(), null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
    	System.out.println("Unregister Optimizer One");
    	registration.unregister();
    }
}
