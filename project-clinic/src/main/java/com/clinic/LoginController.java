package com.clinic;

import com.clinic.service.DBService;
import com.clinic.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class LoginController {
    @FXML
    public TextField emailInputField;
    @FXML
    public Text emailTextLabel;
    @FXML
    public Text passwordTextLabel;
    @FXML
    public PasswordField passInputField;
    @FXML
    public Label wrongInputLabel;
    @FXML
    public Button loginButton;
    @FXML
    public Button registrationButton;
    @FXML
    public AnchorPane anchorPain;
    @FXML
    public Label tittleLabel;

    @FXML
    public void initialize() {
        wrongInputLabel.setVisible(false);
    }

    @FXML
    public void onRegisterButtonClick(ActionEvent actionEvent) {
        try {
            SceneSwitcher.changeScene(SceneSwitcher.REGISTRATION_VIEW, SceneSwitcher.REGISTRATION_STYLE, actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onLoginButtonClick(ActionEvent actionEvent) {
        if (!emailInputField.getText().isEmpty() && !passInputField.getText().isEmpty()
        && !emailInputField.getText().equals("") && !passInputField.getText().equals("")) {
            boolean isLiquidInfo = DBService.getInstance().loginUser(emailInputField.getText(), passInputField.getText());
            if (isLiquidInfo) {
                try {
                    SceneSwitcher.changeScene(SceneSwitcher.MAIN_VIEW, SceneSwitcher.MAIN_STYLE, actionEvent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                wrongInputLabel.setVisible(true);
            }
        }
        else {
            wrongInputLabel.setVisible(true);
        }
    }
}