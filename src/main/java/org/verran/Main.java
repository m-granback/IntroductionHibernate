package org.verran;

import org.verran.controller.CustomerController;
import org.verran.view.Menu;

public class Main {
    public static void main(String[] args) {
        CustomerController controller = new CustomerController();
        Menu menu = new Menu(controller);
        menu.showMenu();
    }
}