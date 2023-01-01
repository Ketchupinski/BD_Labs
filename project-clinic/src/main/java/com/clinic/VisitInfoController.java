package com.clinic;

import com.clinic.entity.Visit;
import com.clinic.service.DBService;
import com.clinic.service.Status;
import com.clinic.service.UserRole;
import com.clinic.util.GlobalInfo;
import com.clinic.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class VisitInfoController {
    @FXML
    public TextArea problemBox;
    @FXML
    public TextArea treatmentBox;
    @FXML
    public Label doctorField;
    @FXML
    public Label specField;
    @FXML
    public Label patientField;
    @FXML
    public Label diagnoseField;
    @FXML
    public Label dateField;
    @FXML
    public Label statusField;
    @FXML
    public Label diagnoseLabel;
    @FXML
    public Label treatmentLabel;
    @FXML
    public Button backButton;
    @FXML
    public Button visitButton;
    @FXML
    public Button cancelButton;

    @FXML
    public void initialize() {
        Visit visit = GlobalInfo.VISIT;
        problemBox.setText(visit.getProblem());
        treatmentBox.setText(visit.getTreatment());
        doctorField.setText(visit.getDoctorFullName());
        specField.setText(visit.getSpecialization());
        patientField.setText(visit.getPatientFullName());
        specField.setText(visit.getSpecialization());
        dateField.setText(visit.getDate().toString());
        statusField.setText(visit.getStatus().getName());
        treatmentBox.setEditable(false);
        if (visit.getStatus() == Status.DONE) {
            diagnoseLabel.setVisible(true);
            treatmentLabel.setVisible(true);
            treatmentBox.setVisible(true);
            treatmentBox.setText(visit.getTreatment());
            diagnoseField.setVisible(true);
            diagnoseField.setText(visit.getDiagnosis());
        } else {
            diagnoseLabel.setVisible(false);
            treatmentLabel.setVisible(false);
            diagnoseField.setVisible(false);
            treatmentBox.setVisible(false);
        }

        if (GlobalInfo.USER_ROLE == UserRole.PATIENT && visit.getStatus() == Status.PENDING) {
            visitButton.setVisible(false);
            cancelButton.setVisible(true);
        } else if (GlobalInfo.USER_ROLE == UserRole.DOCTOR) {
            if (visit.getStatus() == Status.PENDING) {
                visitButton.setVisible(true);
                cancelButton.setVisible(true);
                visitButton.setText("Start visit");
            } else if (visit.getStatus() == Status.IN_PROGRESS) {
                visitButton.setVisible(true);
                cancelButton.setVisible(true);
                visitButton.setText("Finish visit");
            } else {
                visitButton.setVisible(false);
                cancelButton.setVisible(false);
            }
        } else {
            visitButton.setVisible(false);
            cancelButton.setVisible(false);
        }
    }

    @FXML
    public void onBackButtonClick(ActionEvent actionEvent) {
        GlobalInfo.VISIT = null;
        try {
            SceneSwitcher.changeScene(SceneSwitcher.MAIN_VIEW, SceneSwitcher.MAIN_STYLE, actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onVisitButtonClick(ActionEvent actionEvent) {
        Visit visit = GlobalInfo.VISIT;
        if (visit.getStatus() == Status.PENDING) {
            visit.setStatus(Status.IN_PROGRESS);
            DBService.updateVisit(visit);
            try {
                SceneSwitcher.changeScene(SceneSwitcher.VISIT_INFO_VIEW, SceneSwitcher.VISIT_INFO_STYLE, actionEvent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (visit.getStatus() == Status.IN_PROGRESS) {
            try {
                SceneSwitcher.changeScene(SceneSwitcher.END_VISIT_VIEW, SceneSwitcher.END_VISIT_STYLE, actionEvent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void onCancelButtonClick(ActionEvent actionEvent) {
        DBService.setVisitStatus(GlobalInfo.VISIT, Status.CANCELED);
        GlobalInfo.VISIT.setStatus(Status.CANCELED);
        try {
            SceneSwitcher.changeScene(SceneSwitcher.VISIT_INFO_VIEW, SceneSwitcher.VISIT_INFO_STYLE, actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
