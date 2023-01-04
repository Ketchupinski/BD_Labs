module com.university.entity_lab {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires org.mapstruct.processor;
    requires jakarta.persistence;
    requires java.naming;

    opens com.university.entity_lab to javafx.fxml;
    opens com.university.entity_lab.entity to org.hibernate.orm.core, org.mapstruct.processor;
    exports com.university.entity_lab;
    exports com.university.entity_lab.entity;
    exports com.university.entity_lab.entity.factory;
    exports com.university.entity_lab.db;
    exports com.university.entity_lab.service;
}