package org.example.services;

import org.example.entities.*;
import org.example.repositories.CustomerRepository;
import org.example.utils.InputUtils;

import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private CustomerRepository customerRepository = new CustomerRepository();

    public Customer createCustomer(){
        System.out.println("=== Customer creation ===\n");

        Customer customer = new Customer();
        setCustomer(customer);

        customerRepository.save(customer);
        return customer;
    }

    public void modificationCustomer(){
        System.out.println("=== Customer modification ===\n");
        int userInput;

        displayAllCustomer();

        Customer customerSelected = selectCustomerByID();
        if(customerSelected == null)
            return;

        do {
            System.out.println("\n=== Selected customer's infos ===");
            displayCustomerModificationMenu(customerSelected);
            System.out.print("Your choice : ");
            userInput = InputUtils.userRangeIntInput(0, 6);
            if(userInput != 0)
                modificationCustomerValue(customerSelected, userInput);

        }while (userInput != 0);
    }

    public void deleteArticle(){
        System.out.println("\n=== Customer modification ===\n");

        displayAllCustomer();

        Customer customerSelected = selectCustomerByID();
        if(customerSelected == null)
            return;

        customerRepository.delete(customerSelected);
    }

    public void displayCustomerByID(){
        Customer customer = selectCustomerByID();
        if(customer != null)
            System.out.println(customer);
    }

    public void displayAllCustomer(){
        List<Customer> customers = selectAllCustomers();
        if(customers == null)
            return;

        for(Customer customer : customers){
            System.out.println(customer);
        }
    }

    public void selectCustomerHistory(){
        displayAllCustomer();
        Customer customer = selectCustomerByID();
        if(customer == null)
            return;

        displayCustomerHistory(customer.getId_customer());
    }

    public void displayCustomerHistory(int id_customer){
        List<Sale> sales= customerRepository.findAllSalesByCustomerID(id_customer);
        System.out.println(sales);
    }

    public void modificationCustomerValue(Customer customer, int inputToUpdate){
        switch (inputToUpdate){
            case 1 :
                System.out.print("Please enter customer's name : ");
                customer.setName(InputUtils.userStringInput());
                break;
            case 2 :
                System.out.print("Please enter customer's email : ");
                customer.setEmail(InputUtils.userStringInput());
                break;
        }
        saveCustomer(customer);
    }

    public void saveCustomer(Customer customer){
        customerRepository.save(customer);
    }

    public void setCustomer(Customer customer){
        System.out.print("Please enter customer's name : ");
        customer.setName(InputUtils.userStringInput());
        System.out.print("Please enter customer's email : ");
        customer.setEmail(InputUtils.userStringInput());
        customer.setPurchasingHistory(new ArrayList<>());
    }

    public Customer selectCustomerByID(){
        System.out.print("Please enter customer's ID : ");
        int userInput = InputUtils.userIntInput();
        Customer customer = customerRepository.findById(Customer.class, userInput);

        if(customer == null){
            System.out.println("Customer's ID not found");
            return null;
        }

        return customer;
    }

    public List<Customer> selectAllCustomers(){
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

    public static void displayCustomerModificationMenu(Customer customer){
        System.out.println("\n1. Name : " + customer.getName());
        System.out.println("2. Email : " + customer.getEmail());
        System.out.println("0. Quit modifications\n");
    }
}
