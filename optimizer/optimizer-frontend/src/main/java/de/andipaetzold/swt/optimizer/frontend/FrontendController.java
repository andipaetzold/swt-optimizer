package de.andipaetzold.swt.optimizer.frontend;

import java.net.URL;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.ResourceBundle;

import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import de.andipaetzold.swt.optimizer.optimizerbase.OptimizerStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class FrontendController implements Initializable {
    /// Event Admin ///
    private EventAdmin eventAdmin;

    public void setEventAdmin(EventAdmin eventAdmin) {
        this.eventAdmin = eventAdmin;
    }

    /// initialize ///
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JavaFxUtils.runAndWait(() -> {
            // spinner
            SpinnerValueFactory<Double> factory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE,
                    0, 0.1);
            inputSpinner.setValueFactory(factory);

            // table
            optimizerColumn.setCellValueFactory(new PropertyValueFactory<>("optimizer"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
            resultColumn.setCellValueFactory(new PropertyValueFactory<>("result"));
            optimizerTable.setItems(observableOptimizerStatus);

            // status / progress
            progress.setProgress(0);
        });
    }

    /// Spinner ///
    @FXML
    private Spinner<Double> inputSpinner;

    public double getInputSpinnerValue() {
        return inputSpinner.getValue();
    }

    /// Start Button ///
    @FXML
    private Button startButton;

    @FXML
    private void startButtonClicked() {
        if (eventAdmin != null) {
            Dictionary<String, Object> props = new Hashtable<>();
            props.put("value", getInputSpinnerValue());
            eventAdmin.sendEvent(new Event("de/andipaetzold/swt/optimizer/manager/OPTIMIZE", props));
        }
    }

    /// Optimizer Table ///
    @FXML
    private TableView<OptimizerTableRow> optimizerTable;
    private ObservableList<OptimizerTableRow> observableOptimizerStatus = FXCollections.observableArrayList();

    @FXML
    private TableColumn<OptimizerTableRow, String> optimizerColumn;
    @FXML
    private TableColumn<OptimizerTableRow, String> statusColumn;
    @FXML
    private TableColumn<OptimizerTableRow, String> resultColumn;

    public void setOptimizerResult(String optimizer, Double result) {
        OptimizerTableRow optimizerStatus = getOptimizerStatus(optimizer);
        if (optimizerStatus != null) {
            optimizerStatus.setResult(result);
        }
    }

    public void setOptimizerStatus(String optimizer, OptimizerStatus status) {
        OptimizerTableRow optimizerStatus = getOptimizerStatus(optimizer);
        if (optimizerStatus != null) {
            optimizerStatus.setStatus(status);
            recalcProgress();
        }
    }

    public void addOptimizer(String optimizer) {
        OptimizerTableRow status = getOptimizerStatus(optimizer);
        if (status == null) {
            observableOptimizerStatus.add(new OptimizerTableRow(optimizer));
            recalcProgress();
        }
    }

    public void removeOptimizer(String optimizer) {
        OptimizerTableRow optimizerStatus = getOptimizerStatus(optimizer);
        if (optimizerStatus != null) {
            observableOptimizerStatus.remove(optimizerStatus);
            recalcProgress();
        }
    }

    private OptimizerTableRow getOptimizerStatus(String optimizer) {
        for (OptimizerTableRow optimizerStatus : observableOptimizerStatus) {
            if (optimizerStatus.getOptimizer().equals(optimizer)) {
                return optimizerStatus;
            }
        }
        return null;
    }

    /// Progress ///
    @FXML
    private ProgressIndicator progress;

    private void recalcProgress() {
        int sum = observableOptimizerStatus.size();
        int finished = 0;
        for (OptimizerTableRow observableStatus : observableOptimizerStatus) {
            if (observableStatus.getStatus().equals(OptimizerStatus.FINISHED.toString())) {
                finished++;
            }
        }
        double result = finished / (double) sum;

        JavaFxUtils.runAndWait(() -> {
            progress.setProgress(result);
        });
    }
}
