package de.andipaetzold.swt.optimizer.frontend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class FrontendWindow {
    private Stage stage;
    private Parent root = null;
    private FrontendController controller;

    private List<EventHandler<WindowEvent>> onCloseHandlers = new ArrayList<>();

    public FrontendWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(this.getClass().getResource("FrontendWindow.fxml"));
            loader.setClassLoader(this.getClass().getClassLoader());
            root = loader.load();
            controller = loader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void show() {
        if (stage == null) {
            stage = new Stage();
            stage.setTitle("Optimizer");
            stage.setScene(new Scene(root, 320, 240));
            stage.setResizable(false);
            stage.setOnCloseRequest(evt -> {
                onCloseHandlers.forEach(h -> h.handle(evt));
            });
        }
        stage.show();
    }

    public void close() {
        if (stage != null) {
            stage.close();
        }
    }

    public void addOnCloseEventHandler(EventHandler<WindowEvent> event) {
        onCloseHandlers.add(event);
    }

    public void removeOnCloseEventHandler(EventHandler<WindowEvent> event) {
        onCloseHandlers.remove(event);
    }

    public FrontendController getController() {
        return controller;
    }
}
