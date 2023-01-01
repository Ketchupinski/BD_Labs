package com.clinic.entity;

import com.clinic.service.Status;

import java.sql.Date;
import java.util.Objects;

public class Visit {

    private int id;

    private int patientId;

    private String patientFullName;
    private int doctorId;

    private String doctorFullName;
    private String specialization;
    private String problem;
    private Date date;
    private Status status;
    private String diagnosis;
    private String treatment;

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPatientFullName() {
        return patientFullName;
    }

    public void setPatientFullName(String patientFullName) {
        this.patientFullName = patientFullName;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorFullName() {
        return doctorFullName;
    }

    public void setDoctorFullName(String doctorFullName) {
        this.doctorFullName = doctorFullName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem = problem;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Visit visit = (Visit) o;
        return id == visit.id && patientId == visit.patientId && doctorId == visit.doctorId && Objects.equals(patientFullName, visit.patientFullName) && Objects.equals(doctorFullName, visit.doctorFullName) && Objects.equals(specialization, visit.specialization) && Objects.equals(problem, visit.problem) && Objects.equals(date, visit.date) && status == visit.status && Objects.equals(diagnosis, visit.diagnosis) && Objects.equals(treatment, visit.treatment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, patientId, patientFullName, doctorId, doctorFullName, specialization, problem, date, status, diagnosis, treatment);
    }

    @Override
    public String toString() {
        return "Visit{" +
                "id=" + id +
                ", patientId=" + patientId +
                ", patientFullName='" + patientFullName + '\'' +
                ", doctorId=" + doctorId +
                ", doctorFullName='" + doctorFullName + '\'' +
                ", specialization='" + specialization + '\'' +
                ", problem='" + problem + '\'' +
                ", date=" + date +
                ", status=" + status +
                ", diagnosis='" + diagnosis + '\'' +
                ", treatment='" + treatment + '\'' +
                '}';
    }
}
