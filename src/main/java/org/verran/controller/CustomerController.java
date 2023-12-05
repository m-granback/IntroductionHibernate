package org.verran.controller;

import org.verran.entity.Car;
import org.verran.entity.Customer;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 *  This class contains a lot of redundant code.
 *  The idea was not to write compact code, but to show the individual steps of CRUD operations
 *  CRUD - CREATE READ UPDATE DELETE
 */
public class CustomerController {
    // The value "hibernate" at the end of the row is a pointer of which settings in persistence.xml to use.

    public static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("hibernate");

    // CREATE
    public boolean save(Object customer) {
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(customer);
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
    public List<Customer> getAll(boolean printOut){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        List<Customer> customerListToReturn = new ArrayList<>();
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            TypedQuery<Customer> resultList = entityManager.createQuery("FROM Customer", Customer.class);
            customerListToReturn.addAll(resultList.getResultList());
            transaction.commit();
            if(printOut){
                for (Customer customer :
                        customerListToReturn) {
                    System.out.println(customer.getId() + ". " + customer.getName());
                    for (Car car :
                            customer.getOwnedCars()) {
                        System.out.println("\t - " + car.getPlateNumber());
                    }
                }
            }
            return customerListToReturn;
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
    // READ 1
    public Customer getCustomerById(int id){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Customer customerToReturn = entityManager.find(Customer.class, id);
            transaction.commit();
            return customerToReturn;
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
    // UPDATE
    public boolean updateCustomer(Customer customer){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(customer);
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
    // DELETE
    public boolean deleteCustomer(Customer customer){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            // If the entity is attached then remove customer, else merge(attach/update) entity and then remove
            entityManager.remove(entityManager.contains(customer) ? customer:entityManager.merge(customer));
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
    public boolean deleteCustomerById(int id){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            // If the entity is attached then remove customer, else merge(attach/update) entity and then remove
            entityManager.remove(entityManager.contains(id) ? id : entityManager.merge(id));
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
    // Assign car to customer
    public boolean addCarToCustomer(int carId, int customerId){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
        EntityTransaction transaction = null;
        Customer customer;
        try {
            transaction = entityManager.getTransaction();
            transaction.begin();
            Optional<Car> possiblyACar = Optional.ofNullable(entityManager.find(Car.class,carId));
            Optional<Customer> possiblyACustomer = Optional.ofNullable(entityManager.find(Customer.class, customerId));
            if(possiblyACustomer.isPresent() && possiblyACar.isPresent()){
                System.out.println("Båda finns");
                Car car = possiblyACar.get();
                customer = possiblyACustomer.get();
                customer.addCar(car);
            }
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
}
