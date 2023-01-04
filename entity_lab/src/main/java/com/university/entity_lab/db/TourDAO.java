package com.university.entity_lab.db;

import com.university.entity_lab.entity.Tour;
import com.university.entity_lab.entity.factory.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Set;

public class TourDAO {
    private static TourDAO tourDAO;

    private TourDAO() {
    }

    public static TourDAO getInstance() {
        if (tourDAO == null) {
            tourDAO = new TourDAO();
        }
        return tourDAO;
    }

    public void save(Tour tour) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(tour);
        tx1.commit();
        session.close();
    }

    public void delete(Tour tour) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(tour);
        tx1.commit();
        session.close();
    }

    public Tour getTourById(int id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Tour tour = session.get(Tour.class, id);
        session.close();
        return tour;
    }

    public Set<Tour> getAllTours() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        List<Tour> tours = session.createQuery("From Tour").list();
        tx1.commit();
        session.close();
        return Set.copyOf(tours);
    }


    /**
     * @param priceFrom - price from
     * @param priceTo   - price to
     * @return set of tours
     */
    public Set<Tour> getFilteredTours(int priceFrom, int priceTo) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        List<Tour> tours = session.createQuery("From Tour where price >= :priceFrom and price <= :priceTo")
                .setParameter("priceFrom", priceFrom)
                .setParameter("priceTo", priceTo)
                .list();
        tx1.commit();
        session.close();
        return Set.copyOf(tours);
    }

    public void update(Tour tour) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.merge(tour);
        tx1.commit();
        session.close();
    }

    public void create(Tour tour) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(tour);
        tx1.commit();
        session.close();
    }
}
