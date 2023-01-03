package com.clinic.service;

import com.clinic.connection.ConnectionPool;
import com.clinic.connection.StandardConnectionPool;
import com.clinic.db.DBManager;
import com.clinic.entity.Visit;
import com.clinic.util.GlobalInfo;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

public final class DBService {
    private static DBService instance;

    private static ConnectionPool connectionPool;

    private DBService() {
    }

    static {
        try {
            connectionPool = StandardConnectionPool.create("jdbc:postgresql://localhost:5432/clinic",
                    "javadude",
                    "pass");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static DBService getInstance() {
        if (instance == null) {
            instance = new DBService();
        }
        return instance;
    }

    public static List<String> getAllDoctors() {
        try (Connection connection = connectionPool.getConnection()) {
            List<String> doctors = DBManager.getAllDoctors(connection);
            connectionPool.releaseConnection(connection);
            return doctors;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static List<String> getAllSpecializations() {
        try (Connection connection = connectionPool.getConnection()) {
            List<String> spec = DBManager.getAllSpecializations(connection);
            connectionPool.releaseConnection(connection);
            return spec;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static List<String> getDoctorPatients() {
        try (Connection connection = connectionPool.getConnection()) {
            List<String> patients = DBManager.getDoctorPatients(connection, GlobalInfo.DOCTOR.getId());
            connectionPool.releaseConnection(connection);
            return patients;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static void createApplication(LocalDate date, String problem, String doctor) {
        try (Connection connection = connectionPool.getConnection()) {
            int doctorId = DBManager.getDoctorIdByName(connection, doctor);
            int patientId = GlobalInfo.PATIENT.getId();
            DBManager.createApplication(connection, date, problem, patientId, doctorId);
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void setVisitStatus(Visit visit, Status canceled) {
        try (Connection connection = connectionPool.getConnection()) {
            DBManager.setVisitStatus(connection, visit, canceled);
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateVisit(Visit visit) {
        try (Connection connection = connectionPool.getConnection()) {
            DBManager.updateVisitStatus(connection, visit);
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void endVisit(String diagnose, String treatment, Visit visit) {
        try (Connection connection = connectionPool.getConnection()) {
            DBManager.setVisitDiagnose(connection, diagnose, treatment, visit);
            DBManager.updateVisitStatus(connection, visit);
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<Visit> getAllDoctorVisits() {
        try (Connection connection = connectionPool.getConnection()) {
            List<Visit> visits = DBManager.getAllDoctorVisits(connection, GlobalInfo.DOCTOR.getId());
            connectionPool.releaseConnection(connection);
            return visits;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static void deleteVisit(Visit selectedItem) {
        try (Connection connection = connectionPool.getConnection()) {
            DBManager.deleteVisit(connection, selectedItem);
            connectionPool.releaseConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static List<Visit> getVisitsByDoctorPatient(String patient, int doctorId) {
        try (Connection connection = connectionPool.getConnection()) {
            List<Visit> visits = DBManager.getVisitsByDoctorPatient(connection, patient, doctorId);
            connectionPool.releaseConnection(connection);
            return visits;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


    public boolean loginUser(String email, String pass) {
        if (email.isEmpty() && pass.isEmpty() &&
                !email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
            return false;
        }
        try (Connection connection = connectionPool.getConnection()) {
            boolean isUserExists = DBManager.getInstance().checkLoginUser(email, pass, connection);
            if (!isUserExists) {
                return false;
            }
            int userRole = DBManager.getInstance().getUserRole(email, connection);
            GlobalInfo.USER_ROLE = UserRole.getRole(userRole);
            if (userRole == 1) {
                GlobalInfo.PATIENT = DBManager.getInstance().getPatientByEmail(email, connection);
            } else {
                GlobalInfo.DOCTOR = DBManager.getInstance().getDoctorByEmail(email, connection);
            }
            connectionPool.releaseConnection(connection);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean registerUser(String email, String name, String surname, String pass, String photoPath) {
        if (email.isEmpty() && pass.isEmpty() && name.isEmpty() && surname.isEmpty() &&
                !email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
            return false;
        }
        try (Connection connection = connectionPool.getConnection()) {
            boolean result = DBManager.getInstance().registerNewUser(email, name, surname, pass, photoPath, connection);
            connectionPool.releaseConnection(connection);
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Visit> getPatientVisits(int id) {
        try (Connection connection = connectionPool.getConnection()) {
            List<Visit> visits = DBManager.getInstance().getPatientVisits(id, connection);
            connectionPool.releaseConnection(connection);
            return visits;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<Visit> getDoctorActiveVisits(int id) {
        try (Connection connection = connectionPool.getConnection()) {
            List<Visit> visits = DBManager.getInstance().getDoctorActiveVisits(id, connection);
            connectionPool.releaseConnection(connection);
            return visits;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
