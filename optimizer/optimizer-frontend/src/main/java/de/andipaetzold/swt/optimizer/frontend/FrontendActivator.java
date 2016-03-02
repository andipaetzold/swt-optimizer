package de.andipaetzold.swt.optimizer.frontend;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

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
                context.getBundle().stop();
                stopUI(context);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void stopUI(BundleContext context) {
        frontendWindow.close();
    }

}
