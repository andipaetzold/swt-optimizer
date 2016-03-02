package de.andipaetzold.swt.optimizer.frontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Spinner;

public class FrontendController {
    @FXML
    private Spinner<Double> inputSpinner;

    @FXML
    private Button startButton;

    @FXML
    private ListView<String> optimizerList;

    @FXML
    private Label statusLabel;
    @FXML
    private ProgressBar progress;
}
