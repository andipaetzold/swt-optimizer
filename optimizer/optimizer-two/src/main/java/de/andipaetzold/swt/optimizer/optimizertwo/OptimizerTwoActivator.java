package de.andipaetzold.swt.optimizer.optimizertwo;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import de.andipaetzold.swt.optimizer.optimizerbase.factory.OptimizerFactory;

public class OptimizerTwoActivator implements BundleActivator {
    private ServiceRegistration<OptimizerFactory> registration;

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        registration = bundleContext.registerService(OptimizerFactory.class, new OptimizerTwoFactory(), null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        registration.unregister();
    }
}
