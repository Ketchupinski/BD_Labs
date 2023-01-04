package com.university.entity_lab.utilities;

import com.university.entity_lab.TouristApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneSwitcher {

    private static SceneSwitcher sceneSwitcher;

    private SceneSwitcher() {
    }

    public static SceneSwitcher getInstance() {
        if (sceneSwitcher == null) {
            sceneSwitcher = new SceneSwitcher();
        }
        return sceneSwitcher;
    }

    public static final String MAIN_VIEW = "main-view.fxml";

    public static final String ADD_TOUR_VIEW = "add-tour-view.fxml";

    public void changeScene(String fxml, ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TouristApplication.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
