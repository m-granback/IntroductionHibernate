package org.verran.entity;

import javax.persistence.*;
// @Entity, we want this class to have persistence in the database
@Entity
// @Table, we can rename this to suit our needs, or else Hibernate takes charge.
@Table(name = "cars")
public class Car {
    // We declare where the primary key is
    @Id
    // The id will be generetad by the database
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private int id;
    // @Column(length = 8) is equivalent to VARCHAR(8) in database terms
    @Column(length = 8)
    private String plateNumber;
    // @ManyToOne - many cars like this one can be owned by a single customer
    @ManyToOne
    @JoinColumn(name = "customer_id")  // This is the owning side of the relation
    private Customer customer;

    public Car() {
    }

    public Car(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Car(String plateNumber, Customer customer) {
        this.plateNumber = plateNumber;
        this.customer = customer;
    }

    public Car(int id, String plateNumber, Customer customer) {
        this.id = id;
        this.plateNumber = plateNumber;
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
