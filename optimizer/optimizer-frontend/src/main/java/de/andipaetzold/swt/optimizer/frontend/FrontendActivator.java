package de.andipaetzold.swt.optimizer.frontend;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class FrontendActivator implements BundleActivator {
    private FrontendWindow frontendWindow;

    @Override
    public void start(BundleContext arg0) throws Exception {
        JavaFxUtils.initJavaFx();
        JavaFxUtils.runAndWait(() -> startUI(arg0));
    }

    @Override
    public void stop(BundleContext arg0) throws Exception {
        JavaFxUtils.runAndWait(() -> stopUI(arg0));
    }

    private void startUI(BundleContext ctx) {
        frontendWindow = new FrontendWindow();
        frontendWindow.show();

        frontendWindow.show();
        frontendWindow.addOnCloseEventHandler(evt -> {
            try {
                ctx.getBundle().stop();
                stopUI(ctx);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void stopUI(BundleContext ctx) {
        frontendWindow.close();
    }

}
