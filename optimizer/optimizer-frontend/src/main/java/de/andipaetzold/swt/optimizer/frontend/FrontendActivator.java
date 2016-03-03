package de.andipaetzold.swt.optimizer.frontend;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import de.andipaetzold.swt.optimizer.manager.Manager;

public class FrontendActivator implements BundleActivator {
    private FrontendWindow frontendWindow;

    @Override
    public void start(BundleContext context) throws Exception {
        JavaFxUtils.initJavaFx();
        JavaFxUtils.runAndWait(() -> startUI(context));
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        JavaFxUtils.runAndWait(() -> stopUI(context));
    }

    private void startUI(BundleContext context) {
        frontendWindow = new FrontendWindow();
        frontendWindow.show();
        frontendWindow.addOnCloseEventHandler(evt -> {
            try {
                ServiceReference<Manager> managerReference = context.getServiceReference(Manager.class);
                Manager manager = context.getServiceObjects(managerReference).getService();
                manager.removeStatusListener(frontendWindow.getController());
            } catch (Exception e) {
                System.out.println("Problem removing frontend from manager");
            }

            try {
                context.getBundle().stop();
                stopUI(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // add to manager
        try {
            ServiceReference<Manager> managerReference = context.getServiceReference(Manager.class);
            Manager manager = context.getServiceObjects(managerReference).getService();
            manager.addStatusListener(frontendWindow.getController());
        } catch (Exception e) {
            System.out.println("Problem adding frontend to manager");
        }
    }

    private void stopUI(BundleContext context) {
        frontendWindow.close();
    }

}
