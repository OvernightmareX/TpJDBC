package org.example.controllers;

import org.example.services.CustomerService;

import static org.example.constants.Constants.MAX_CLIENT_CRUD_CHOICE;
import static org.example.utils.InputUtils.userRangeIntInput;

public class CustomerIHM {
    CustomerService customerService = new CustomerService();

    public void StartCustomerIHM(){
        int userChoice;

        do {
            userChoice = displayCustomerCRUDMenu();
            switch (userChoice) {
                case 1 -> customerService.createCustomer();
                case 2 -> customerService.modificationCustomer();
                case 3 -> customerService.deleteArticle();
                case 4 -> customerService.displayCustomerByID();
                case 5 -> customerService.selectCustomerHistory();
                case 6 -> customerService.displayAllCustomer();
            }
        } while (userChoice != 0);
    }

    public static int displayCustomerCRUDMenu(){
        System.out.println("\n=== Customer manager ===\n");
        System.out.println("1. Create");
        System.out.println("2. Modification");
        System.out.println("3. Delete");
        System.out.println("4. Display customer by ID");
        System.out.println("5. Display customer's history");
        System.out.println("6. Display all customers");
        System.out.println("0. Quit customer manager\n");

        System.out.print("Your choice :");
        return userRangeIntInput(0,MAX_CLIENT_CRUD_CHOICE);
    }
}
