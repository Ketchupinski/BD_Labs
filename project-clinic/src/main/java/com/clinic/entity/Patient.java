package com.clinic.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Patient implements Serializable {
    private int id;
    private String name;
    private String lastName;
    private String email;
    private String password;

    private String photoPath;

    @Serial
    private static final long serialVersionUID = 12125L;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return id == patient.id && Objects.equals(name, patient.name) && Objects.equals(lastName, patient.lastName) && Objects.equals(email, patient.email) && Objects.equals(password, patient.password) && Objects.equals(photoPath, patient.photoPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastName, email, password, photoPath);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", photoPath='" + photoPath + '\'' +
                '}';
    }
}
