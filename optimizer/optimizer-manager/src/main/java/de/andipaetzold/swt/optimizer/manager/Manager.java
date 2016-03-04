package de.andipaetzold.swt.optimizer.manager;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import de.andipaetzold.swt.optimizer.optimizerbase.Optimizer;
import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerFactory;
import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerStatus;

public class Manager {
    private final List<OptimizerFactory> factories = new ArrayList<>();

    public Manager(EventAdmin eventAdmin) {
        this.eventAdmin = eventAdmin;
    }

    /// Optimizer ///
    public void addOptimizer(OptimizerFactory factory) {
        factories.add(factory);
        sendAddOptimizer(factory.getOptimizerType());

        // start if running
        if (isRunning()) {
            startOptimizer(factory);
        }
    }

    public void removeOptimizer(OptimizerFactory factory) {
        factories.remove(factory);
        sendRemoveOptimizer(factory.getOptimizerType());
    }

    /// Optimize ///
    private final List<Optimizer> optimizers = new ArrayList<>();
    private double startValue;

    public void optimize(double value) {
        if (isRunning()) {
            return;
        }

        startValue = value;

        optimizers.clear();
        for (OptimizerFactory factory : factories) {
            startOptimizer(factory);
        }
    }

    private void startOptimizer(OptimizerFactory factory) {
        Optimizer optimizer = factory.createOptimizer();
        optimizers.add(optimizer);

        new Thread(() -> {
            optimizer.optimize(startValue);
        }).start();
    }

    /// Running ///
    private boolean isRunning() {
        for (Optimizer optimizer : optimizers) {
            if (optimizer.getStatus() == OptimizerStatus.RUNNING) {
                return true;
            }
        }
        return false;
    }

    /// Event Admin ///
    private EventAdmin eventAdmin;

    private void sendAddOptimizer(String optimizer) {
        Dictionary<String, Object> props = new Hashtable<>();
        props.put("optimizer", optimizer);
        eventAdmin.sendEvent(new Event("de/andipaetzold/swt/optimizer/frontend/ADD", props));
    }

    private void sendRemoveOptimizer(String optimizer) {
        // send to listeners
        Dictionary<String, Object> props = new Hashtable<>();
        props.put("optimizer", optimizer);
        eventAdmin.sendEvent(new Event("de/andipaetzold/swt/optimizer/frontend/REMOVE", props));
    }

    public void resendOptimizerStatus() {
        for (OptimizerFactory factory : factories) {
            sendAddOptimizer(factory.getOptimizerType());
        }

        for (Optimizer optimizer : optimizers) {
            optimizer.resend();
        }
    }
}
