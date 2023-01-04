package com.university.entity_lab;

import com.university.entity_lab.entity.Tour;
import com.university.entity_lab.service.DBService;
import com.university.entity_lab.utilities.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Date;

public class AddController {
    @FXML
    public TextField countryField;
    @FXML
    public TextField cityField;
    @FXML
    public TextField priceField;
    @FXML
    public DatePicker startDateField;
    @FXML
    public DatePicker endDateField;
    @FXML
    public Button backButton;
    @FXML
    public Button addButton;

    @FXML
    public void onBackButtonClick(ActionEvent actionEvent) {
        try {
            SceneSwitcher.getInstance().changeScene(SceneSwitcher.MAIN_VIEW, actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onAddButtonClick(ActionEvent actionEvent) {
        Tour tour = new Tour();
        tour.setCountry(countryField.getText());
        tour.setCity(cityField.getText());
        tour.setPrice(Integer.parseInt(priceField.getText()));
        tour.setStartDate(Date.valueOf(startDateField.getValue()));
        tour.setEndDate(Date.valueOf(endDateField.getValue()));
        DBService.getInstance().addTour(tour);
        try {
            SceneSwitcher.getInstance().changeScene(SceneSwitcher.MAIN_VIEW, actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
