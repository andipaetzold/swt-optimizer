package de.andipaetzold.swt.optimizer.manager;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.EventAdmin;
import org.osgi.util.tracker.ServiceTracker;

import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerFactory;

public class ManagerActivator implements BundleActivator {
    private ServiceTracker<OptimizerFactory, OptimizerFactory> optimizerTracker;

    @Override
    public void start(BundleContext context) throws Exception {
        // EventAdmin
        ServiceReference<EventAdmin> ref = context.getServiceReference(EventAdmin.class);
        EventAdmin eventAdmin = context.getService(ref);

        // Manager
        Manager manager = new Manager(eventAdmin);

        // Tracker
        optimizerTracker = new ServiceTracker<>(context, OptimizerFactory.class,
                new OptimizerTracker(context, manager));
        optimizerTracker.open();

        // Event Handler
        ManagerEventHandler.register(context, manager);
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        optimizerTracker.close();
    }
}
