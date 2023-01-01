package com.clinic.db;

import com.clinic.entity.Doctor;
import com.clinic.entity.Patient;
import com.clinic.entity.Visit;
import com.clinic.service.Status;
import com.clinic.service.UserRole;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DBManager {
    private static DBManager instance;

    private DBManager() {
    }

    public static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    public static List<String> getAllDoctors(Connection connection) {
        List<String> doctors = new ArrayList<>();
        String query = "SELECT DISTINCT first_name, last_name, name as spec FROM users " +
                "INNER JOIN doctors_specializations ds on users.id = ds.doctor_id " +
                "INNER JOIN specializations s on ds.specialization_id = s.id " +
                "WHERE role_id = 2";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                doctors.add(resultSet.getString("first_name") + " " + resultSet.getString("last_name") + " - " + resultSet.getString("spec"));
            }
            return doctors;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static List<String> getAllSpecializations(Connection connection) {
        List<String> specializations = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM specializations")) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                specializations.add(resultSet.getString("name"));
            }
            return specializations;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static List<String> getDoctorPatients(Connection connection, int id) {
        List<String> patients = new ArrayList<>();
        String query = "SELECT DISTINCT u.first_name as first_name, u.last_name as last_name FROM visits " +
                "INNER JOIN users u on visits.user_id = u.id " +
                "WHERE doctor_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                patients.add(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
            }
            return patients;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static void createApplication(Connection connection, LocalDate date, String problem, int patientId, int doctorId) {

        String query = "INSERT INTO visits (date, problem, user_id, doctor_id, status_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setDate(1, Date.valueOf(date));
            preparedStatement.setString(2, problem);
            preparedStatement.setInt(3, patientId);
            preparedStatement.setInt(4, doctorId);
            preparedStatement.setInt(5, Status.PENDING.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getDoctorIdByName(Connection connection, String doctor) {
        String query = "SELECT id FROM users WHERE first_name = ? AND last_name = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, doctor.split(" ")[0]);
            preparedStatement.setString(2, doctor.split(" ")[1]);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void setVisitStatus(Connection connection, Visit visit, Status canceled) {
        String query = "UPDATE visits SET status_id = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, canceled.getId());
            preparedStatement.setInt(2, visit.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateVisitStatus(Connection connection, Visit visit) {
        String query = "UPDATE visits SET status_id = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, visit.getStatus().getId());
            preparedStatement.setInt(2, visit.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setVisitDiagnose(Connection connection, String diagnose, String treatment, Visit visit) {
        String query = "INSERT INTO diagnoses (visit_id, name, treatment) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, visit.getId());
            preparedStatement.setString(2, diagnose);
            preparedStatement.setString(3, treatment);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkLoginUser(String email, String pass, Connection connection) {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, pass);
            return preparedStatement.executeQuery().next();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getUserRole(String email, Connection connection) {
        String query = "SELECT role_id FROM users WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("role_id");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Doctor getDoctorByEmail(String email, Connection connection) {
        String query = "SELECT users.id as id, name as first_name, last_name as last_name, " +
                "email as email, password as pass, specializations.name as specialization " +
                "FROM users " +
                "INNER JOIN doctors_specializations ON users.id = doctors_specializations.doctor_id " +
                "INNER JOIN specializations ON doctors_specializations.specialization_id = specializations.id " +
                "WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    Doctor doctor = new Doctor();
                    doctor.setId(rs.getInt("id"));
                    doctor.setName(rs.getString("first_name"));
                    doctor.setLastName(rs.getString("last_name"));
                    doctor.setEmail(rs.getString("email"));
                    doctor.setPassword(rs.getString("pass"));
                    doctor.setSpecialization(rs.getString("specialization"));
                    return doctor;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Patient getPatientByEmail(String email, Connection connection) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    Patient patient = new Patient();
                    patient.setId(rs.getInt("id"));
                    patient.setName(rs.getString("first_name"));
                    patient.setLastName(rs.getString("last_name"));
                    patient.setEmail(rs.getString("email"));
                    patient.setPassword(rs.getString("password"));
                    patient.setPhotoPath(rs.getString("photo_path"));
                    return patient;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean registerNewUser(String email, String name, String surname, String pass, String
            photoPath, Connection connection) {
        String query = "INSERT INTO users (first_name, last_name, email, password, role_id, photo_path) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, pass);
            preparedStatement.setInt(5, UserRole.PATIENT.getValue());
            preparedStatement.setString(6, photoPath);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<Visit> getPatientVisits(int id, Connection connection) {
        String query = "SELECT visits.id as visit_id, doctors.first_name as doctor_name, doctors.last_name as last_name, user_id as user_id, ds.doctor_id as doctor_id, " +
                "       patients.first_name as patient_name, patients.last_name as patient_last_name, " +
                "       date as date, problem as problem, status_id as status_id, name as status_name " +
                "FROM visits " +
                "INNER JOIN users patients ON visits.user_id = patients.id " +
                "INNER JOIN users doctors ON visits.doctor_id = doctors.id " +
                "INNER JOIN doctors_specializations ds on doctors.id = ds.doctor_id " +
                "INNER JOIN specializations s on ds.specialization_id = s.id " +
                "WHERE user_id = ?";
        return getVisitsByQuery(id, connection, query);
    }

    public List<Visit> getDoctorActiveVisits(int id, Connection connection) {
        String query = "SELECT visits.id as visit_id, doctors.first_name as doctor_name, doctors.last_name as last_name, user_id as user_id, ds.doctor_id as doctor_id, " +
                "       patients.first_name as patient_name, patients.last_name as patient_last_name, " +
                "       date as date, problem as problem, status_id as status_id, name as status_name " +
                "FROM visits " +
                "INNER JOIN users patients ON visits.user_id = patients.id " +
                "INNER JOIN users doctors ON visits.doctor_id = doctors.id " +
                "INNER JOIN doctors_specializations ds on doctors.id = ds.doctor_id " +
                "INNER JOIN specializations s on ds.specialization_id = s.id " +
                "WHERE visits.doctor_id = ? AND (status_id = 1 OR status_id = 4)";
        return getVisitsByQuery(id, connection, query);
    }

    private List<Visit> getVisitsByQuery(int id, Connection connection, String query) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                List<Visit> visits = new ArrayList<>();
                while (rs.next()) {
                    Visit visit = new Visit();
                    visit.setId(rs.getInt("visit_id"));
                    visit.setPatientId(rs.getInt("user_id"));
                    visit.setDoctorId(rs.getInt("doctor_id"));
                    visit.setPatientFullName(rs.getString("patient_name") + " " + rs.getString("patient_last_name"));
                    visit.setDoctorFullName(rs.getString("doctor_name") + " " + rs.getString("last_name"));
                    visit.setDate(rs.getDate("date"));
                    visit.setProblem(rs.getString("problem"));
                    visit.setStatus(Status.getById(rs.getInt("status_id")));
                    visit.setSpecialization(rs.getString("status_name"));
                    visits.add(visit);
                }
                return visits;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
