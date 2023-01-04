package com.university.entity_lab;

import com.university.entity_lab.entity.Tour;
import com.university.entity_lab.entity.User;
import com.university.entity_lab.service.DBService;
import com.university.entity_lab.utilities.SceneSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.util.Set;

public class MainController {
    @FXML
    public TableView<Tour> toursTable;
    @FXML
    public TableView<User> clientsTable;
    @FXML
    public TableColumn<Tour, Date> startDateCol;
    @FXML
    public TableColumn<Tour, Date> endDateCol;
    @FXML
    public TableColumn<Tour, String> countryCol;
    @FXML
    public TableColumn<Tour, String> cityCol;
    @FXML
    public TableColumn<Tour, Integer> priceCol;
    @FXML
    public TableColumn<Tour, String> nameCol;
    @FXML
    public TableColumn<Tour, String> surnameCol;
    @FXML
    public Button addTourButton;
    @FXML
    public Button deleteTourButton;
    @FXML
    public Button addClientButton;
    @FXML
    public Button removeClientButton;
    @FXML
    public Button filterButton;
    @FXML
    public TextField priceFromField;
    @FXML
    public TextField priceToField;
    @FXML
    public ComboBox<User> clientComboBox;
    @FXML
    public Button showAllButton;

    @FXML
    public void initialize() {
        startDateCol.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        countryCol.setCellValueFactory(new PropertyValueFactory<>("country"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameCol.setCellValueFactory(new PropertyValueFactory<>("surname"));
        DBService.getInstance().getAllTours().forEach(toursTable.getItems()::add);
        toursTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            clientsTable.getItems().clear();
            Set<User> users = newValue.getUsers();
            users.forEach(clientsTable.getItems()::add);
        });
        DBService.getInstance().getAllUsers().forEach(clientComboBox.getItems()::add);
    }

    @FXML
    public void onAddTourClick(ActionEvent actionEvent) {
        try {
            SceneSwitcher.getInstance().changeScene(SceneSwitcher.ADD_TOUR_VIEW, actionEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onDeleteTourClick(ActionEvent actionEvent) {
        Tour selectedItem = toursTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No tour selected");
            alert.setContentText("Please select a tour to delete");
            alert.showAndWait();
            return;
        }
        DBService.getInstance().deleteTour(selectedItem);
        toursTable.getItems().remove(selectedItem);
    }

    @FXML
    public void onAddClientClick(ActionEvent actionEvent) {
        Tour selectedItem = toursTable.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No tour selected");
            alert.setContentText("Please select a tour to add a client to");
            alert.showAndWait();
            return;
        }
        User user = clientComboBox.getSelectionModel().getSelectedItem();
        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No client selected");
            alert.setContentText("Please select a client to add to the tour");
            alert.showAndWait();
            return;
        }
        DBService.getInstance().addUserToTour(user, selectedItem);
        clientsTable.getItems().add(user);
    }

    @FXML
    public void onRemoveClientClick(ActionEvent actionEvent) {
        User user = clientsTable.getSelectionModel().getSelectedItem();
        Tour tour = toursTable.getSelectionModel().getSelectedItem();
        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No client selected");
            alert.setContentText("Please select a client to remove");
            alert.showAndWait();
            return;
        }
        DBService.getInstance().deleteUserFromTour(tour, user);
        clientsTable.getItems().remove(user);
    }

    @FXML
    public void onFilterClick(ActionEvent actionEvent) {
        int priceFrom = 0;
        int priceTo = Integer.MAX_VALUE;
        try {
            priceFrom = Integer.parseInt(priceFromField.getText());
            priceTo = Integer.parseInt(priceToField.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please enter valid numbers");
            alert.showAndWait();
            return;
        }
        if (priceFrom > priceTo) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Price from cannot be greater than price to");
            alert.showAndWait();
            return;
        }
        toursTable.getItems().clear();
        DBService.getInstance().getFilteredTours(priceFrom, priceTo).forEach(toursTable.getItems()::add);

    }

    @FXML
    public void onShowAllButtonClick(ActionEvent actionEvent) {
        toursTable.getItems().clear();
        DBService.getInstance().getAllTours().forEach(toursTable.getItems()::add);
    }
}