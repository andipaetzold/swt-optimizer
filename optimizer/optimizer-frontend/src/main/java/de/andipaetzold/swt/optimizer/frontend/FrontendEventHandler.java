package de.andipaetzold.swt.optimizer.frontend;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.framework.BundleContext;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;

import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerStatus;

public class FrontendEventHandler implements EventHandler {
    public static void register(BundleContext context, FrontendController controller) {
        String[] topics = new String[] { "de/andipaetzold/swt/optimizer/frontend/*" };

        Dictionary<String, Object> props = new Hashtable<String, Object>();
        props.put(EventConstants.EVENT_TOPIC, topics);

        context.registerService(EventHandler.class.getName(), new FrontendEventHandler(controller), props);
    }

    private FrontendController controller;

    private FrontendEventHandler(FrontendController controller) {
        this.controller = controller;
    }

    @Override
    public void handleEvent(Event event) {
        System.out.println(event.getTopic());
        switch (event.getTopic()) {
            case "de/andipaetzold/swt/optimizer/frontend/ADD":
                controller.addOptimizer(event.getProperty("optimizer").toString());
                break;
            case "de/andipaetzold/swt/optimizer/frontend/REMOVE":
                controller.removeOptimizer(event.getProperty("optimizer").toString());
                break;
            case "de/andipaetzold/swt/optimizer/frontend/STATUS":
                controller.setOptimizerStatus(event.getProperty("optimizer").toString(),
                        (OptimizerStatus) event.getProperty("status"));
                break;
            case "de/andipaetzold/swt/optimizer/frontend/RESULT":
                controller.setOptimizerResult(event.getProperty("optimizer").toString(),
                        event.getProperty("result") == null ? null : (Double) event.getProperty("result"));
                break;
        }
    }
}
