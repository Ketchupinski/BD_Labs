package com.clinic;

import com.clinic.entity.Visit;
import com.clinic.service.DBService;
import com.clinic.service.Status;
import com.clinic.service.UserRole;
import com.clinic.util.GlobalInfo;
import com.clinic.util.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.util.List;

public class MainController {
    @FXML
    public Label welcomeLabel;
    @FXML
    public Button logOutButton;
    @FXML
    public Button allAplliesButton;
    @FXML
    public DatePicker dateFromField;
    @FXML
    public DatePicker dateToField;
    @FXML
    public ComboBox<String> doctorPatientBox;
    @FXML
    public ComboBox<String> specializationBox;
    @FXML
    public Button filterButton;
    @FXML
    public Button createApplicationButton;
    @FXML
    public Button visitInfoButton;
    @FXML
    public TableView<Visit> visitsTable;
    @FXML
    public TableColumn<Visit, String> patientColumn;
    @FXML
    public TableColumn<Visit, String> doctorColumn;
    @FXML
    public TableColumn<Visit, String> specializationColumn;
    @FXML
    public TableColumn<Visit, Status> statusColumn;
    @FXML
    public TableColumn<Visit, Date> dateColumn;
    @FXML
    public Label doctorPatientLabel;
    @FXML
    public Label specializationLabel;
    @FXML
    public Button profileButton;


    @FXML
    public void initialize() {
        String welcomeText = GlobalInfo.USER_ROLE == UserRole.PATIENT
                ? "Welcome, " + GlobalInfo.PATIENT.getName() + " " + GlobalInfo.PATIENT.getLastName() + "! Here are your visits."
                : "Welcome, Dr. " + GlobalInfo.DOCTOR.getName() + " " + GlobalInfo.DOCTOR.getLastName() + "! Here are your active visits.";
        welcomeLabel.setText(welcomeText);
        patientColumn.setCellValueFactory(new PropertyValueFactory<>("patientFullName"));
        doctorColumn.setCellValueFactory(new PropertyValueFactory<>("doctorFullName"));
        specializationColumn.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        visitsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        onAllAppliesButtonClick(null);
        if (GlobalInfo.USER_ROLE == UserRole.PATIENT) {
            doctorPatientLabel.setText("Doctor:");
            specializationLabel.setText("Specialization:");
            doctorPatientBox.getItems().addAll(DBService.getAllDoctors());
            specializationBox.getItems().addAll(DBService.getAllSpecializations());
            createApplicationButton.setVisible(true);
            profileButton.setVisible(true);
        } else {
            doctorPatientLabel.setText("Patient:");
            specializationLabel.setText("Status:");
            doctorPatientBox.getItems().addAll(DBService.getDoctorPatients());
            specializationBox.getItems().addAll(Status.PENDING.toString(), Status.CANCELED.toString(), Status.DONE.toString(), Status.IN_PROGRESS.toString());
            createApplicationButton.setVisible(false);
            profileButton.setVisible(false);
        }
    }

    @FXML
    public void onLogOutButtonClick(ActionEvent actionEvent) {
        GlobalInfo.USER_ROLE = null;
        GlobalInfo.PATIENT = null;
        GlobalInfo.DOCTOR = null;
        try {
            SceneSwitcher.changeScene(SceneSwitcher.LOGIN_VIEW, SceneSwitcher.LOGIN_STYLE, actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onAllAppliesButtonClick(ActionEvent actionEvent) {
        visitsTable.getItems().clear();
        System.out.println("All applies to table");
        List<Visit> visits;
        if (GlobalInfo.USER_ROLE == UserRole.PATIENT) {
            visits = DBService.getInstance().getPatientVisits(GlobalInfo.PATIENT.getId());
        } else {
            visits = DBService.getInstance().getDoctorActiveVisits(GlobalInfo.DOCTOR.getId());
        }
        visitsTable.getItems().addAll(visits);
    }

    @FXML
    public void onFilterButtonClick(ActionEvent actionEvent) {
        visitsTable.getItems().clear();
        System.out.println("Filter applies to table");
        List<Visit> visits;
        if (GlobalInfo.USER_ROLE == UserRole.PATIENT) {
            visits = DBService.getInstance().getPatientVisits(GlobalInfo.PATIENT.getId());
        } else {
            visits = DBService.getInstance().getDoctorActiveVisits(GlobalInfo.DOCTOR.getId());
        }
        if (dateFromField.getValue() != null) {
            visits.removeIf(visit -> visit.getDate().before(Date.valueOf(dateFromField.getValue())));
        }
        if (dateToField.getValue() != null) {
            visits.removeIf(visit -> visit.getDate().after(Date.valueOf(dateToField.getValue())));
        }
        if (doctorPatientBox.getValue() != null) {
            if (GlobalInfo.USER_ROLE == UserRole.PATIENT) {
                visits.removeIf(visit -> !visit.getDoctorFullName().equals(doctorPatientBox.getValue()));
            } else {
                visits.removeIf(visit -> !visit.getPatientFullName().equals(doctorPatientBox.getValue()));
            }
        }
        if (specializationBox.getValue() != null) {
            if (GlobalInfo.USER_ROLE == UserRole.PATIENT) {
                visits.removeIf(visit -> !visit.getSpecialization().equals(specializationBox.getValue()));
            } else {
                visits.removeIf(visit -> !visit.getStatus().toString().equals(specializationBox.getValue()));
            }
        }
        visitsTable.getItems().addAll(visits);
    }

    @FXML
    public void onCreateApplicationButtonClick(ActionEvent actionEvent) {
        try {
            SceneSwitcher.changeScene(SceneSwitcher.CREATE_APPLICATION_VIEW, SceneSwitcher.CREATE_APPLICATION_STYLE, actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onVisitInfoButtonClick(ActionEvent actionEvent) {
        Visit visit = visitsTable.getSelectionModel().getSelectedItem();
        if (visit == null) {
            return;
        }
        GlobalInfo.VISIT = visit;
        try {
            SceneSwitcher.changeScene(SceneSwitcher.VISIT_INFO_VIEW, SceneSwitcher.VISIT_INFO_STYLE, actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onProfileButtonClick(ActionEvent actionEvent) {
        try {
            SceneSwitcher.changeScene(SceneSwitcher.PROFILE_VIEW, SceneSwitcher.PROFILE_STYLE, actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
