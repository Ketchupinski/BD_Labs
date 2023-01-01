package com.clinic;

import com.clinic.service.DBService;
import com.clinic.util.FileService;
import com.clinic.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;

public class RegistrationController {
    @FXML
    public Label titleLabel;
    @FXML
    public Button backToLoginButton;
    @FXML
    public Label errorLabel;
    @FXML
    public Button registerButton;
    @FXML
    public TextField emailField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField surnameField;
    @FXML
    public PasswordField passField;
    @FXML
    public Button selectPhotoButton;

    private File photoFile;

    @FXML
    public void initialize() {
        errorLabel.setVisible(false);
    }

    @FXML
    public void onBackToLoginClick(ActionEvent actionEvent) {
        try {
            SceneSwitcher.changeScene(SceneSwitcher.LOGIN_VIEW, SceneSwitcher.LOGIN_STYLE, actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onRegisterButtonClick(ActionEvent actionEvent) {
        if (!emailField.getText().isEmpty() && !nameField.getText().isEmpty() && !surnameField.getText().isEmpty() && !passField.getText().isEmpty()
                && !emailField.getText().equals("") && !nameField.getText().equals("") && !surnameField.getText().equals("") && !passField.getText().equals("")
                && photoFile != null) {
            File destFile = FileService.copyPhotoToProjectDirectory(photoFile);
            if (destFile != null) {
                boolean isLiquidInfo = DBService.getInstance().registerUser(emailField.getText(), nameField.getText(), surnameField.getText(), passField.getText(), destFile.getPath());
                if (isLiquidInfo) {
                    try {
                        SceneSwitcher.changeScene(SceneSwitcher.LOGIN_VIEW, SceneSwitcher.LOGIN_STYLE, actionEvent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    errorLabel.setVisible(true);
                }
            } else {
                errorLabel.setVisible(true);
            }
        } else {
            errorLabel.setVisible(true);
        }
    }

    @FXML
    public void onSelectPhotoButtonClick(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        photoFile = fc.showOpenDialog(null);
    }
}
