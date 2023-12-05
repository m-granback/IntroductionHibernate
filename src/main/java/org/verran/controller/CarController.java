package org.verran.controller;

import org.verran.entity.Car;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

/**
 *  This class contains a lot of redundant code.
 *  The idea was not to write compact code, but to show the individual steps of CRUD operations
 *  CRUD - CREATE READ UPDATE DELETE
 */
public class CarController {

    // The value "hibernate" at the end of the row is a pointer of which settings in persistence.xml to use.
    public static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hibernate");

    // CREATE
    public boolean save(Car car) {
        // "Boiler plate" of code, recurring code throughout the code
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(car);
            transaction.commit();
            return true;
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return false;
    }
    // READ
    public List<Car> getAll(boolean printOut){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            List<Car> listToReturn = new ArrayList<>(entityManager.createQuery("FROM Car", Car.class).getResultList());
            transaction.commit();
            if(printOut){
                for (Car car :
                        listToReturn) {
                    System.out.println(car.getId() + ". " + car.getPlateNumber());
                }
            }
            return listToReturn;
        } catch (Exception e){
            if(transaction != null){
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
        return null;
    }
}
