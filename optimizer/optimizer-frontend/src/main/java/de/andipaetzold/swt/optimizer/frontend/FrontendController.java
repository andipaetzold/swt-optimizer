package de.andipaetzold.swt.optimizer.frontend;

import java.net.URL;
import java.util.ResourceBundle;

import de.andipaetzold.swt.optimizer.manager.FrontendInterface;
import de.andipaetzold.swt.optimizer.optimizerbase.Optimizer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class FrontendController implements Initializable, FrontendInterface {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JavaFxUtils.runLater(() -> {
            // spinner
            SpinnerValueFactory<Double> factory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE,
                    0, 0.1);
            inputSpinner.setValueFactory(factory);

            // list
            optimizerList.setItems(observableOptimizerList);
        });
    }

    @FXML
    private Spinner<Double> inputSpinner;

    public double getInputSpinnerValue() {
        return inputSpinner.getValue();
    }

    @FXML
    private Button startButton;

    @FXML
    private void optimize() {
        if (optimizer != null) {
            System.out.println(optimizer.optimize(getInputSpinnerValue()));
        }
    }

    private Optimizer optimizer = null;

    @Override
    public void setOptimizeMethod(Optimizer optimizer) {
        this.optimizer = optimizer;
    }

    @FXML
    private ListView<String> optimizerList;
    private ObservableList<String> observableOptimizerList = FXCollections.observableArrayList();

    @Override
    public void addOptimizer(String optimizer) {
        JavaFxUtils.runLater(() -> {
            observableOptimizerList.add(optimizer);
        });
    }

    @Override
    public void removeOptimizer(String optimizer) {
        JavaFxUtils.runLater(() -> {
            observableOptimizerList.remove(optimizer);
        });
    }

    @FXML
    private Label statusLabel;
    @FXML
    private ProgressBar progress;

    @Override
    public void setProgress(double value) {
        JavaFxUtils.runLater(() -> {
            progress.setProgress(value);
        });
    }

    @Override
    public void setStatus(String value) {
        JavaFxUtils.runLater(() -> {
            statusLabel.setText(value);
        });
    }
}
