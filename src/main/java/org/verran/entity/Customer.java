package org.verran.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// @Entity, we want this class to have persistence in the database
@Entity
// @Table, we can rename this to suit our needs, or else Hibernate takes charge.
@Table(name = "customers")
public class Customer {
    // We declare where the primary key is
    @Id
    // The id will be generetad by the database
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private int id;
    // @Column(name = "customer_name", length = 30) is equivalent to VARCHAR(30) in database terms
    @Column(name = "customer_name", length = 30)
    private String name;
    // @OneToMany Defines the relation to the other entity (One customer can own several cars)
    // FetchType.EAGER Always query for entities and all of their children FetchType.LAZY (must ask for children)
    // CascadeType.ALL saves entity and its children in one go.
    // mappedBy actually prevents this side from creating an extra table for relational mapping
    // mappedBy tells hibernate that the opposite side of this relation has control, no need to create extra tables
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Car> ownedCars = new ArrayList<>();

    public Customer() {
    }
    public Customer(String name) {
        this.name = name;
    }
    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public void addCar(Car car){
        car.setCustomer(this);
        ownedCars.add(car);
    }

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

    public List<Car> getOwnedCars() {
        return ownedCars;
    }

    public void setOwnedCars(List<Car> ownedCars) {
        this.ownedCars = ownedCars;
    }
}
