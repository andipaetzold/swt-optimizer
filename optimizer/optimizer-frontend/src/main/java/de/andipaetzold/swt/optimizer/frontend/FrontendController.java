package de.andipaetzold.swt.optimizer.frontend;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import de.andipaetzold.swt.optimizer.manager.FrontendInterface;
import de.andipaetzold.swt.optimizer.manager.OptimizeMethod;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

public class FrontendController implements Initializable, FrontendInterface {
    /// initialize ///
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JavaFxUtils.runAndWait(() -> {
            // spinner
            SpinnerValueFactory<Double> factory = new SpinnerValueFactory.DoubleSpinnerValueFactory(0, Double.MAX_VALUE,
                    0, 0.1);
            inputSpinner.setValueFactory(factory);

            // list
            optimizerList.setItems(observableOptimizerList);
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
        if (optimizeMethod != null) {
            optimizeMethod.optimize(getInputSpinnerValue());
        }
    }

    /// Optimize Method ///
    private OptimizeMethod optimizeMethod = null;

    @Override
    public void setOptimizeMethod(OptimizeMethod optimizeMethod) {
        this.optimizeMethod = optimizeMethod;
    }

    /// Optimizer List ///
    @FXML
    private ListView<String> optimizerList;
    private ObservableList<String> observableOptimizerList = FXCollections.observableArrayList();

    @Override
    public void addOptimizer(String optimizer) {
        JavaFxUtils.runAndWait(() -> {
            observableOptimizerList.add(optimizer);
        });
    }

    @Override
    public void removeOptimizer(String optimizer) {
        JavaFxUtils.runAndWait(() -> {
            observableOptimizerList.remove(optimizer);
        });
    }

    /// Status ///
    @FXML
    private Label statusLabel;
    @FXML
    private ProgressBar progress;

    @Override
    public void setProgress(double value) {
        JavaFxUtils.runAndWait(() -> {
            progress.setProgress(value);
        });
    }

    @Override
    public void setStatus(String value) {
        JavaFxUtils.runAndWait(() -> {
            statusLabel.setText(value);
        });
    }

    /// Result ///
    @Override
    public void handleResults(Map<String, Double> results) {
        // Text
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, Double> result : results.entrySet()) {
            sb.append(result.getKey());
            sb.append(": ");
            sb.append(result.getValue());
            sb.append("\n");
        }

        // Alert
        JavaFxUtils.runAndWait(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Result");
            alert.setHeaderText(null);
            alert.setContentText(sb.toString());
            alert.showAndWait();
        });
    }
}
