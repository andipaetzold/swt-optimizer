package de.andipaetzold.swt.optimizer.manager;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerFactory;

public class OptimizerTracker implements ServiceTrackerCustomizer<OptimizerFactory, OptimizerFactory> {
    public OptimizerTracker(BundleContext bundleContext, Manager manager) {
        this.bundleContext = bundleContext;
        this.manager = manager;
    }

    /// Events ///
    private BundleContext bundleContext;

    @Override
    public OptimizerFactory addingService(ServiceReference<OptimizerFactory> reference) {
        OptimizerFactory of = bundleContext.getService(reference);
        addService(of);
        return of;
    }

    @Override
    public void modifiedService(ServiceReference<OptimizerFactory> reference, OptimizerFactory service) {
        removeService(service);
        addService(service);
    }

    @Override
    public void removedService(ServiceReference<OptimizerFactory> reference, OptimizerFactory service) {
        removeService(service);
    }

    /// Manager ///
    private Manager manager;

    public void addService(OptimizerFactory service) {
        manager.addOptimizer(service);
    }

    public void removeService(OptimizerFactory service) {
        manager.removeOptimizer(service);
    }
}
