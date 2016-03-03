package de.andipaetzold.swt.optimizer.manager;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

import de.andipaetzold.swt.optimizer.optimizerbase.factory.OptimizerFactory;

public class ManagerActivator implements BundleActivator {

    private ServiceTracker<OptimizerFactory, OptimizerFactory> optimizerTracker;
    private ServiceRegistration<Manager> registration;

    @Override
    public void start(BundleContext context) throws Exception {
        Manager manager = new Manager();

        optimizerTracker = new ServiceTracker<>(context, OptimizerFactory.class,
                new OptimizerTracker(context, manager));
        optimizerTracker.open();

        registration = context.registerService(Manager.class, manager, null);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        optimizerTracker.close();
        registration.unregister();
    }

}
