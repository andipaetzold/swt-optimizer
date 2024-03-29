package de.andipaetzold.swt.optimizer.manager;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

public class ManagerEventHandler implements EventHandler {
    public static void register(BundleContext context, Manager manager) {
        String[] topics = new String[] { "de/andipaetzold/swt/optimizer/manager/*",
                "org/osgi/framework/BundleEvent/STARTED" };

        Dictionary<String, Object> props = new Hashtable<String, Object>();
        props.put(EventConstants.EVENT_TOPIC, topics);

        context.registerService(EventHandler.class.getName(), new ManagerEventHandler(manager), props);
    }

    private Manager manager;

    private ManagerEventHandler(Manager manager) {
        this.manager = manager;
    }

    @Override
    public void handleEvent(Event event) {
        switch (event.getTopic()) {
            case "de/andipaetzold/swt/optimizer/manager/OPTIMIZE":
                manager.optimize((double) event.getProperty("value"));
                break;
            case "org/osgi/framework/BundleEvent/STARTED":
                if (event.getProperty("bundle.symbolicName").equals("de.andipaetzold.swt.optimizer-frontend")) {
                    manager.resendOptimizerStatus();
                }
                break;
        }
    }
}
