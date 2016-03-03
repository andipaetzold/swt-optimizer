package de.andipaetzold.swt.optimizer.frontend;

import java.net.URL;
import java.util.ResourceBundle;

import de.andipaetzold.swt.optimizer.manager.StatusListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Spinner;

public class FrontendController implements Initializable, StatusListener {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        JavaFxUtils.runLater(() -> {
            optimizerList.setItems(observableOptimizerList);
        });
    }

    @FXML
    private Spinner<Double> inputSpinner;

    @FXML
    private Button startButton;

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
