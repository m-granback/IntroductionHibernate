package org.verran.view;

import org.verran.controller.CarController;
import org.verran.controller.CustomerController;
import org.verran.entity.Car;
import org.verran.entity.Customer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 *      Simple menu, gets the job done
 */
public class Menu {
    private CustomerController customerController;
    private CarController carController;

    public Menu(CustomerController customerController) {
        this.customerController = customerController;
        carController = new CarController();
    }

    public void showMenu(){
        System.out.println("\n-------------------Gör ett val-----------------\n");
        System.out.println("0.\tLägg till 5 random kunder &\n\t5 bilar utan koppling till varandra");
        System.out.println("--------------------------");
        System.out.println("1.\tLägg till kund");
        System.out.println("2.\tUppdatera kund");
        System.out.println("3.\tTa bort kund");
        System.out.println("4.\tLista kunder och deras ägda bilar");
        System.out.println("5.\tHämta kund utifrån id");
        System.out.println("--------------------------");
        System.out.println("6.\tLägg till bil");
        System.out.println("9.\tLista alla bilar");
        System.out.println("--------------------------");
        System.out.println("11.\tHantera bilköp");
        System.out.println("--------------------------");
        handleInput();
    }
    private void space(){
        for (int i = 0; i < 10; i++) {
            System.out.println();
        }
    }

    private void handleInput() {
        System.out.print("Ange ditt val: ");
        String userChoice = new Scanner(System.in).nextLine();
        space();
        switch (userChoice){
            case "0":
                addCustomersAndCars();
                break;
            case "1":
                // Add customer
                System.out.print("Ange kundnamn: ");
                String customerName = new Scanner(System.in).nextLine();
                if(customerController.save(new Customer(customerName))){
                    System.out.println(customerName + " added");
                } else {
                    System.out.println("Failed to add customer");
                }

                break;
            case "2":
                // Update customer (change of name)
                    customerController.getAll(true);
                    System.out.print("Välj id:");
                    Customer customerToUpdate = customerController.getCustomerById(new Scanner(System.in).nextInt());
                    System.out.print("Ändra namn från " + customerToUpdate.getName() + " till?: ");
                    customerToUpdate.setName(new Scanner(System.in).nextLine());
                    if(customerController.updateCustomer(customerToUpdate)){
                        System.out.println("Customer updated");
                    } else {
                        System.out.println("Customer update failed");
                    }

                break;
            case "3":
                // Delete customer
                customerController.getAll(true);
                System.out.print("Välj id: ");
                if (customerController.deleteCustomerById(new Scanner(System.in).nextInt())){
                    System.out.println("Customer deleted");
                } else {
                    System.out.println("Failed to delete customer");
                }
                break;
            case "4":
                // Print all customers
                System.out.println("Lista av samtliga kunder: ");
                customerController.getAll(true);
                break;
            case "5":
                // Test to fetch a specific post from database
                customerController.getAll(true);
                System.out.print("Välj id för användare att hämta: ");
                Optional<Customer> fetchedCustomer = Optional.ofNullable(customerController.getCustomerById(new Scanner(System.in).nextInt()));
                if(fetchedCustomer.isPresent()) {
                    System.out.println("Customer " + fetchedCustomer.get().getName() + " fetched successfully");
                } else {
                    System.out.println("Could not fetch customer");
                }
                break;
            case "6":
                // Add a car
                System.out.print("Ange regnummer: ");
                if(carController.save(new Car(new Scanner(System.in).nextLine()))){
                    System.out.println("Car added");
                } else {
                    System.out.println("Could not save car");
                }
                break;
            case "9":
                // List all cars
                carController.getAll(true);
                break;
            case "11":
                customerController.getAll(true);
                System.out.print("Köpare med id: ");
                int buyer = new Scanner(System.in).nextInt();
                carController.getAll(true);
                System.out.println("Välj id att tilldela " + customerController.getAll(false).get(buyer-1).getName());
                int carId = new Scanner(System.in).nextInt();
                if(customerController.addCarToCustomer(carId,buyer)){
                    System.out.println("Car has new owner");
                } else {
                    System.out.println("No deal, something went wrong");
                }
                break;
            default:
                break;
        }
        showMenu();
    }

    private void addCustomersAndCars() {
        List<Customer> customersToAdd = new ArrayList<>();
        List<Car> carsToAdd = new ArrayList<>();
        customersToAdd.addAll(List.of(
                new Customer("Hans"),
                new Customer("Johannes"),
                new Customer("Ronaldo"),
                new Customer("Fia")
                )
        );
        carsToAdd.addAll(List.of(
                new Car("GAB438"),
                new Car("PZG141"),
                new Car("WLY454"),
                new Car("NPJ972")
                )
        );
        for (Customer customer :
                customersToAdd) {
            if(customerController.save(customer)){
                System.out.println(customer.getName() + " added");
            }
        }
        for (Car car :
                carsToAdd) {
            if(carController.save(car)){
                System.out.println(car.getPlateNumber() + " added");
            }
        }
    }
}
