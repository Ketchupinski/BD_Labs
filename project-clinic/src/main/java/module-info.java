module com.clinic.clinic {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.clinic to javafx.fxml;
    exports com.clinic;
    exports com.clinic.util;
    exports com.clinic.connection;
    exports com.clinic.db;
    exports com.clinic.entity;
    exports com.clinic.service;
}