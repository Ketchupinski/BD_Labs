package com.clinic;

import com.clinic.util.GlobalInfo;
import com.clinic.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class ProfileController {
    @FXML
    public Button backButton;
    @FXML
    public ImageView patientPhotoView;
    @FXML
    public Label firstNameLabel;
    @FXML
    public Label lastNameLabel;
    @FXML
    public Label emailLabel;

    @FXML
    public void initialize() {
        firstNameLabel.setText(GlobalInfo.PATIENT.getName());
        lastNameLabel.setText(GlobalInfo.PATIENT.getLastName());
        emailLabel.setText(GlobalInfo.PATIENT.getEmail());
        if (GlobalInfo.PATIENT.getPhotoPath() != null && !GlobalInfo.PATIENT.getPhotoPath().isEmpty()) {
            File file = new File(GlobalInfo.PATIENT.getPhotoPath());
            Image image = new Image(file.toURI().toString());
            patientPhotoView.setImage(image);
        } else {
            patientPhotoView.setVisible(false);
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
