/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Bean.SenhasBean;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Marcos Filho
 */
public class SenhasDAO {

    public static SenhasDAO instance;
    protected EntityManager entityManager;

    public static SenhasDAO getInstance() {
        if (instance == null) {
            instance = new SenhasDAO();
        }

        return instance;
    }

    public SenhasDAO() {
        entityManager = getEntityManager();
    }

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("crudHibernatePU");
        if (entityManager == null) {
            entityManager = factory.createEntityManager();
        }

        return entityManager;
    }

    public SenhasBean getById(final int id) {
        return entityManager.find(SenhasBean.class, id);
    }


    @SuppressWarnings("unchecked")
    public List<SenhasBean> findAll() {
        return entityManager.createQuery("FROM " + SenhasBean.class.getName()).getResultList();
    }

    public void persist(SenhasBean cliente) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(cliente);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void merge(SenhasBean cliente) {
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(cliente);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void remove(SenhasBean cliente) {
        try {
            entityManager.getTransaction().begin();
            cliente = entityManager.find(SenhasBean.class, cliente.getId());
            entityManager.remove(cliente);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }

    public void removeById(final int id) {
        try {
            SenhasBean cliente = getById(id);
            remove(cliente);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
