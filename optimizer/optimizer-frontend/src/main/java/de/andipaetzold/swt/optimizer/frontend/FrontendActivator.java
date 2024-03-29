package de.andipaetzold.swt.optimizer.frontend;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.EventAdmin;

public class FrontendActivator implements BundleActivator {
    private FrontendWindow frontendWindow;

    /// Start ///
    @Override
    public void start(BundleContext context) throws Exception {
        JavaFxUtils.initJavaFx();
        JavaFxUtils.runAndWait(() -> startUI(context));
    }

    private void startUI(BundleContext context) {
        frontendWindow = new FrontendWindow();
        frontendWindow.show();
        frontendWindow.addOnCloseEventHandler(evt -> {
            try {
                context.getBundle().stop();
                stopUI(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // Set EventAdmin
        ServiceReference<EventAdmin> ref = context.getServiceReference(EventAdmin.class);
        frontendWindow.getController().setEventAdmin(context.getService(ref));

        // Event Handler
        FrontendEventHandler.register(context, frontendWindow.getController());
    }

    /// Stop ///
    @Override
    public void stop(BundleContext context) throws Exception {
        JavaFxUtils.runAndWait(() -> stopUI(context));
    }

    private void stopUI(BundleContext context) {
        frontendWindow.close();
    }

}
