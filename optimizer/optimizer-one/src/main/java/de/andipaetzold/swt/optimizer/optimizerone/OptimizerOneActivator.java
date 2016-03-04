package de.andipaetzold.swt.optimizer.optimizerone;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.event.EventAdmin;

import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerFactory;

public class OptimizerOneActivator implements BundleActivator {
    private ServiceRegistration<OptimizerFactory> registration;

    @Override
    public void start(BundleContext context) throws Exception {
        // EventAdmin
        ServiceReference<EventAdmin> ref = context.getServiceReference(EventAdmin.class);
        EventAdmin eventAdmin = context.getService(ref);

        // Register
        registration = context.registerService(OptimizerFactory.class, new OptimizerOneFactory(eventAdmin), null);
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        registration.unregister();
    }
}
