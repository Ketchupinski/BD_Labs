package com.clinic.util;

import com.clinic.MainApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public final class SceneSwitcher {

    private SceneSwitcher() {
    }

    public static final String LOGIN_VIEW = "login-view.fxml";

    public static final String LOGIN_STYLE = "login-style.css";
    public static final String REGISTRATION_VIEW = "registration-view.fxml";
    public static final String REGISTRATION_STYLE = "registration-style.css";
    public static final String MAIN_VIEW = "main-view.fxml";

    public static final String MAIN_STYLE = "main-style.css";

    public static final String CREATE_APPLICATION_VIEW = "create-application-view.fxml";
    public static final String CREATE_APPLICATION_STYLE = "create-application-style.css";
    public static final String VISIT_INFO_VIEW = "visit-info-view.fxml";
    public static final String VISIT_INFO_STYLE = "visit-info-style.css";

    public static final String PROFILE_VIEW = "profile-view.fxml";

    public static final String PROFILE_STYLE = "profile-style.css";

    public static final String END_VISIT_VIEW = "end-visit-view.fxml";

    public static final String END_VISIT_STYLE = "end-visit-style.css";


    public static void changeScene(String fxml, String css, ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxml));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource(css)).toExternalForm());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
