package com.clinic.entity;

import java.io.Serial;
import java.io.Serializable;

public class Doctor extends Patient implements Serializable {
    private String specialization;

    @Serial
    private static final long serialVersionUID = 1212425L;

    public Doctor() {
        super();
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
