package de.andipaetzold.swt.optimizer.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.andipaetzold.swt.optimizer.manager.util.Tuple;
import de.andipaetzold.swt.optimizer.optimizerbase.Optimizer;
import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerStatus;
import de.andipaetzold.swt.optimizer.optimizerbase.event.OptimizerListener;
import de.andipaetzold.swt.optimizer.optimizerbase.event.OptimizerResultEvent;
import de.andipaetzold.swt.optimizer.optimizerbase.event.OptimizerStatusChangedEvent;
import de.andipaetzold.swt.optimizer.optimizerbase.factory.OptimizerFactory;

public class Manager implements OptimizeMethod, OptimizerListener {
    private Map<OptimizerFactory, Tuple<Thread, Double>> factories = new HashMap<>();

    private List<FrontendInterface> frontends = new ArrayList<>();

    /// Optimizer ///
    public void addOptimizer(OptimizerFactory factory) {
        // add
        factories.put(factory, new Tuple<Thread, Double>(null, null));

        // send to listeners
        for (FrontendInterface frontend : frontends) {
            frontend.addOptimizer(factory.getOptimizerType());
        }

        // start if running
        if (isRunning()) {
            startOptimizer(factory);
        }
    }

    public void removeOptimizer(OptimizerFactory factory) {
        // remove
        factories.remove(factory);

        // send to listeners
        for (FrontendInterface frontend : frontends) {
            frontend.removeOptimizer(factory.getOptimizerType());
        }
    }

    /// Status Listener ///
    public void addFrontend(FrontendInterface frontend) {
        frontends.add(frontend);

        // add all optimizers to list
        for (Map.Entry<OptimizerFactory, Tuple<Thread, Double>> entry : factories.entrySet()) {
            String factoryName = entry.getKey().getOptimizerType();
            frontend.addOptimizer(factoryName);

            // Thread
            Thread t = entry.getValue().item1;
            if (t == null) {
                frontend.setOptimizerStatus(factoryName, OptimizerStatus.WAITING);
            } else if (t.isAlive()) {
                frontend.setOptimizerStatus(factoryName, OptimizerStatus.RUNNING);
            } else {
                frontend.setOptimizerStatus(factoryName, OptimizerStatus.FINISHED);
            }

            // Result
            frontend.setOptimizerResult(factoryName, entry.getValue().item2);
        }

        // set optimizer method
        frontend.setOptimizeMethod(this);

    }

    public void removeFrontend(FrontendInterface frontend) {
        frontends.remove(frontend);

        // remove optimizer method
        frontend.setOptimizeMethod(null);
    }

    /// Optimize ///
    private double startValue;

    @Override
    public void optimize(double value) {
        if (isRunning()) {
            return;
        }

        startValue = value;
        for (Map.Entry<OptimizerFactory, Tuple<Thread, Double>> entry : factories.entrySet()) {
            // set result
            entry.getValue().item2 = null;

            // start
            startOptimizer(entry.getKey());
        }
    }

    private void startOptimizer(OptimizerFactory factory) {
        Thread t = new Thread(() -> {
            Optimizer optimizer = factory.createOptimizer();

            // set result to null
            for (FrontendInterface frontend : frontends) {
                frontend.setOptimizerResult(optimizer.getOptimizerType(), null);
            }

            // add listener and start optimizing
            optimizer.addOptimizerListener(this);
            optimizer.optimize(startValue);
        });
        t.start();

        factories.get(factory).item1 = t;
    }

    /// Handle Optimizer///
    @Override
    public void handleOptimizerStatusChanged(OptimizerStatusChangedEvent event) {
        // check if in map
        if (!factories.containsKey(event.getSource().getFactory())) {
            return;
        }

        // send status
        for (FrontendInterface frontend : frontends) {
            frontend.setOptimizerStatus(event.getSource().getOptimizerType(), event.getStatus());
        }
    }

    @Override
    public void handleOptimizerResult(OptimizerResultEvent event) {
        // check if in map
        if (!factories.containsKey(event.getSource().getFactory())) {
            return;
        }

        // set result
        factories.get(event.getSource().getFactory()).item2 = event.getResult();

        // send result
        for (FrontendInterface frontend : frontends) {
            frontend.setOptimizerResult(event.getSource().getOptimizerType(), event.getResult());
        }
    }

    /// Running ///
    private boolean isRunning() {
        for (Tuple<Thread, Double> entry : factories.values()) {
            if (entry.item1 != null && entry.item1.isAlive()) {
                return true;
            }
        }
        return false;
    }
}
