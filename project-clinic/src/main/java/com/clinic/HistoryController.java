package com.clinic;

import com.clinic.entity.Visit;
import com.clinic.service.DBService;
import com.clinic.service.Status;
import com.clinic.util.GlobalInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Date;
import java.util.List;

public class HistoryController {
    @FXML
    public TableView<Visit> visitsTable;
    @FXML
    public TableColumn<Visit, String> patientCol;
    @FXML
    public TableColumn<Visit, String> doctorCol;
    @FXML
    public TableColumn<Visit, String> specCol;
    @FXML
    public TableColumn<Visit, String> problemCol;
    @FXML
    public TableColumn<Visit, Date> dateCol;
    @FXML
    public TableColumn<Visit, Status> statusCol;
    @FXML
    public Button upButton;
    @FXML
    public Button downButton;
    @FXML
    public Button addButton;
    @FXML
    public Button deleteButton;
    @FXML
    public TextField patientField;
    @FXML
    public Button findButton;


    @FXML
    public void initialize() {
        patientCol.setCellValueFactory(new PropertyValueFactory<>("patientFullName"));
        doctorCol.setCellValueFactory(new PropertyValueFactory<>("doctorFullName"));
        specCol.setCellValueFactory(new PropertyValueFactory<>("specialization"));
        problemCol.setCellValueFactory(new PropertyValueFactory<>("problem"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        List<Visit> visits = DBService.getAllDoctorVisits();
        visitsTable.getItems().addAll(visits);
        visitsTable.selectionModelProperty().get().setSelectionMode(SelectionMode.SINGLE);
    }


    @FXML
    public void onUpButtonClick(ActionEvent actionEvent) {
        int index = visitsTable.getSelectionModel().getSelectedIndex();
        if (index > 0) {
            visitsTable.getSelectionModel().select(index - 1);
        }
    }

    @FXML
    public void onDownButtonClick(ActionEvent actionEvent) {
        int index = visitsTable.getSelectionModel().getSelectedIndex();
        if (index < visitsTable.getItems().size() - 1) {
            visitsTable.getSelectionModel().select(index + 1);
        }
    }

    @FXML
    public void onAddButtonClick(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("You can't add visit. Please, contact with administrator");
        alert.showAndWait();
    }

    @FXML
    public void onDeleteButtonClick(ActionEvent actionEvent) {
        DBService.deleteVisit(visitsTable.getSelectionModel().getSelectedItem());
        visitsTable.getItems().remove(visitsTable.getSelectionModel().getSelectedItem());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Visit was deleted");
        alert.showAndWait();
    }

    @FXML
    public void onFindButtonClick(ActionEvent actionEvent) {
        String patient = patientField.getText();
        List<Visit> visits = DBService.getVisitsByDoctorPatient(patient, GlobalInfo.DOCTOR.getId());
        visitsTable.getItems().clear();
        visitsTable.getItems().addAll(visits);
    }

    @FXML
    public void onGetAllVisitsButtonClick(ActionEvent actionEvent) {
        List<Visit> visits = DBService.getAllDoctorVisits();
        visitsTable.getItems().clear();
        visitsTable.getItems().addAll(visits);
    }
}
