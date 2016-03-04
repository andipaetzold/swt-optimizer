package de.andipaetzold.swt.optimizer.frontend;

import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerStatus;
import javafx.beans.property.SimpleStringProperty;

public class OptimizerTableRow {
    /// Constructor ///
    public OptimizerTableRow(String optimizer) {
        this(optimizer, OptimizerStatus.WAITING, null);
    }

    public OptimizerTableRow(String optimizer, OptimizerStatus status) {
        this(optimizer, status, null);
    }

    public OptimizerTableRow(String optimizer, OptimizerStatus status, Double result) {
        setOptimizer(optimizer);
        setStatus(status);
        setResult(result);
    }

    /// Optimizer ///
    private SimpleStringProperty optimizer = new SimpleStringProperty();

    public String getOptimizer() {
        return optimizer.get();
    }

    public void setOptimizer(String optimizer) {
        this.optimizer.set(optimizer);
    }

    public SimpleStringProperty optimizerProperty() {
        return optimizer;
    }

    /// Status ///
    private SimpleStringProperty status = new SimpleStringProperty();

    public String getStatus() {
        return status.get();
    }

    public void setStatus(OptimizerStatus status) {
        this.status.set(status.toString());
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    /// Result ///
    private SimpleStringProperty result = new SimpleStringProperty();

    public String getResult() {
        return result.get();
    }

    public void setResult(Double result) {
        if (result == null) {
            this.result.set("");
        } else {
            this.result.set(result.toString());
        }
    }

    public SimpleStringProperty resultProperty() {
        return result;
    }
}
