package org.example.services;

import org.example.entities.Customer;
import org.example.entities.Sale;
import org.example.repositories.CustomerRepository;
import org.example.utils.InputUtils;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private CustomerRepository customerRepository = new CustomerRepository();

    public Customer createCustomer(){
        System.out.println("=== Customer creation ===\n");

        Customer customer = new Customer();
        setCustomerDetails(customer);

        saveCustomer(customer);
        return customer;
    }

    public void modifyCustomer(){
        System.out.println("=== Customer modification ===\n");
        displayAllCustomer();

        Customer customerSelected = getCustomerByID();
        if(customerSelected == null)
            return;

        int userInput;
        do {
            System.out.println("\n=== Selected customer's infos ===");
            displayCustomerModificationMenu(customerSelected);
            System.out.print("Your choice : ");
            userInput = InputUtils.userRangeIntInput(0, 2);

            if(userInput != 0)
                modifyCustomerValue(customerSelected, userInput);
        }while (userInput != 0);
    }

    public void deleteCustomer(){
        System.out.println("\n=== Customer deletion ===\n");

        displayAllCustomer();

        Customer customerSelected = getCustomerByID();
        if(customerSelected == null)
            return;

        customerRepository.delete(customerSelected);
    }

    public void selectCustomerHistory(){
        displayAllCustomer();
        Customer customer = getCustomerByID();
        if(customer == null)
            return;

        displayCustomerHistory(customer.getId_customer());
    }

    public void modifyCustomerValue(Customer customer, int inputToUpdate){
        switch (inputToUpdate) {
            case 1 -> {
                System.out.print("Please enter customer's name: ");
                customer.setName(InputUtils.userStringInput());
            }
            case 2 -> {
                System.out.print("Please enter customer's email: ");
                customer.setEmail(InputUtils.userStringInput());
            }
            default -> throw new IllegalArgumentException("Invalid input for customer modification");
        }
        saveCustomer(customer);
    }

    public void saveCustomer(Customer customer){
        customerRepository.save(customer);
    }

    public void setCustomerDetails(Customer customer){
        System.out.print("Please enter customer's name : ");
        customer.setName(InputUtils.userStringInput());
        System.out.print("Please enter customer's email : ");
        customer.setEmail(InputUtils.userStringInput());
        customer.setPurchasingHistory(new ArrayList<>());
    }

    public Customer getCustomerByID(){
        System.out.print("Please enter customer's ID : ");
        int userInput = InputUtils.userIntInput();
        Customer customer = customerRepository.findById(Customer.class, userInput);

        if(customer == null)
            System.out.println("Customer's ID not found");

        return customer;
    }

    public List<Customer> getAllCustomers(){
        List<Customer> customers = customerRepository.findAll(Customer.class);

        if(customers.isEmpty()){
            System.out.println("No customers");
            return null;
        }

        return customers;
    }

    public long getCustomerCount(){
        return customerRepository.count(Customer.class);
    }

    public void displayCustomerByID(){
        Customer customer = getCustomerByID();
        if(customer != null)
            System.out.println(customer);
    }

    public void displayAllCustomer(){
        List<Customer> customers = getAllCustomers();
        if(customers == null)
            return;

        System.out.println(customers);
    }

    public static void displayCustomerModificationMenu(Customer customer){
        System.out.println("\n1. Name : " + customer.getName());
        System.out.println("2. Email : " + customer.getEmail());
        System.out.println("0. Quit modifications\n");
    }

    public void displayCustomerHistory(int id_customer){
        List<Sale> sales= customerRepository.findAllSalesByCustomerID(id_customer);
        System.out.println(sales);
    }
}
