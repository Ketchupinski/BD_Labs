package com.clinic;

import com.clinic.service.DBService;
import com.clinic.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;
import java.time.LocalDate;

public class CreateApplicationController {
    @FXML
    public Label titleLabel;
    @FXML
    public DatePicker dateField;
    @FXML
    public TextArea problemField;
    @FXML
    public ComboBox<String> doctorField;
    @FXML
    public Button createButton;
    @FXML
    public Button backButton;

    @FXML
    public void initialize() {
        doctorField.getItems().addAll(DBService.getAllDoctors());
    }

    @FXML
    public void onCreateButtonClick(ActionEvent actionEvent) {
        if (dateField.getValue() == null || problemField.getText().isEmpty() || doctorField.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Fill all fields");
            alert.showAndWait();
        } else if (dateField.getValue().isBefore(LocalDate.now())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Date can't be before today");
            alert.showAndWait();
        } else {
            DBService.createApplication(dateField.getValue(), problemField.getText(), doctorField.getValue());
            try {
                SceneSwitcher.changeScene(SceneSwitcher.MAIN_VIEW, SceneSwitcher.MAIN_STYLE, actionEvent);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    @FXML
    public void onBackButtonClick(ActionEvent actionEvent) {
        try {
            SceneSwitcher.changeScene(SceneSwitcher.MAIN_VIEW, SceneSwitcher.MAIN_STYLE, actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
