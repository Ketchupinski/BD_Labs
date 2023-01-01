package com.clinic;

import com.clinic.service.DBService;
import com.clinic.service.Status;
import com.clinic.util.GlobalInfo;
import com.clinic.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EndVisitController {
    @FXML
    public Label titleLabel;
    @FXML
    public TextArea treatmentBox;
    @FXML
    public TextField diagnoseField;

    @FXML
    public void onDoneButtonClick(ActionEvent actionEvent) {
        if (diagnoseField.getText().isEmpty() || treatmentBox.getText().isEmpty()) {
            titleLabel.setText("Please fill all fields");
        } else {
            GlobalInfo.VISIT.setStatus(Status.DONE);
            GlobalInfo.VISIT.setDiagnosis(diagnoseField.getText());
            GlobalInfo.VISIT.setTreatment(treatmentBox.getText());
            DBService.endVisit(diagnoseField.getText(), treatmentBox.getText(), GlobalInfo.VISIT);
            try {
                SceneSwitcher.changeScene(SceneSwitcher.VISIT_INFO_VIEW, SceneSwitcher.VISIT_INFO_STYLE, actionEvent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
