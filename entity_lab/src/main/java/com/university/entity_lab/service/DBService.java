package com.university.entity_lab.service;

import com.university.entity_lab.db.TourDAO;
import com.university.entity_lab.db.UserDAO;
import com.university.entity_lab.entity.Tour;
import com.university.entity_lab.entity.User;

import java.util.Set;

public class DBService {
    private static DBService dbService;

    private DBService() {}

    public static DBService getInstance() {
        if (dbService == null) {
            dbService = new DBService();
        }
        return dbService;
    }

    public Set<Tour> getAllTours() {
        return TourDAO.getInstance().getAllTours();
    }

    public Set<User> getAllUsers() {
        return UserDAO.getInstance().getAllUsers();
    }

    public void saveTour(Tour tour) {
        TourDAO.getInstance().save(tour);
    }

    public void saveUser(User user) {
        UserDAO.getInstance().save(user);
    }

    public void deleteUser(User user) {
        UserDAO.getInstance().delete(user);
    }

    public void deleteTour(Tour tour) {
        TourDAO.getInstance().delete(tour);
    }

    public Set<Tour> getFilteredTours(int priceFrom, int priceTo) {
        return TourDAO.getInstance().getFilteredTours(priceFrom, priceTo);
    }

    public void addUserToTour(User user, Tour tour) {
        tour.getUsers().add(user);
        user.getTours().add(tour);
        UserDAO.getInstance().update(user);
    }

    public void deleteUserFromTour(Tour tour, User user) {
        tour.getUsers().remove(user);
        user.getTours().remove(tour);
        UserDAO.getInstance().update(user);
    }

    public void addTour(Tour tour) {
        TourDAO.getInstance().create(tour);
    }
}
