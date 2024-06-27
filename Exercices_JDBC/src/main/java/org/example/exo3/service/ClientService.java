package org.example.exo3.service;

import jakarta.persistence.EntityManager;
import org.example.exo3.constants.TypeCRUD;
import org.example.exo3.entity.Address;
import org.example.exo3.entity.Client;
import org.example.exo3.repositories.ClientRepository;

import java.util.List;

import static org.example.exo3.constants.Constants.MAX_CLIENT_AGE;
import static org.example.exo3.constants.Constants.MIN_CLIENT_AGE;
import static org.example.exo3.service.AddressService.createAdress;
import static org.example.exo3.utils.TicketingIHM.*;

public class ClientService {
    public static Client selectClient(EntityManager em){
        int userChoice;

        System.out.println("=== Select the client ===\n");
        List<Client> clients = displayAllClient(em);
        if(clients == null)
            return null;

        System.out.print("Select the client id : ");
        userChoice = userIntInput(1, clients.size());

        return ClientRepository.findClientByID(em, clients.get(userChoice-1).getId_client());
    }

    public static Client createClient(){
        System.out.print("Please enter client's name : ");
        String name = userStringInput();
        System.out.print("Please enter client's lastname : ");
        String lastName = userStringInput();
        System.out.print("Please enter client's age : ");
        int age = userIntInput(MIN_CLIENT_AGE,MAX_CLIENT_AGE);
        System.out.print("Please enter client's phone : ");
        String phoneNumber = userStringInput();
        Address address = createAdress();

        return Client.builder()
                .name(name)
                .lastName(lastName)
                .age(age)
                .phoneNumber(phoneNumber)
                .address(address)
                .build();
    }

    public static List<Client> displayAllClient(EntityManager em){
        List<Client> clients = ClientRepository.findAllClient(em);
        int counter=0;

        if(clients.isEmpty()){
            System.out.println("No client");
            return null;
        }

        for(Client clt : clients){
            System.out.println(++counter + ". " +
                    clt.getLastName() + " " + clt.getName());
        }
        return clients;
    }

    public static void displayManageClient(EntityManager em){
        int userInput = displayCRUDMenu(TypeCRUD.CLIENT);

        switch (userInput){
            case 1 -> ClientRepository.saveClient(em, createClient());
            case 2 -> displayAllClient(em);
            case 3 -> ClientRepository.saveClient(em, displayClientModification(em));
            case 4 -> ClientRepository.deleteClient(em, selectClient(em));
        }
    }

    public static Client displayClientModification(EntityManager em){
        Client client = selectClient(em);
        if(client == null)
            return null;

        int userChoice;
        do {
            displayClientModificationMenu(client);
            System.out.print("Select the information you want to change : ");
            userChoice= userIntInput(0,5);

            if(userChoice!= 0)
                System.out.println("Enter your modification : ");

            switch (userChoice){
                case 1 -> client.setName(userStringInput());
                case 2 -> client.setLastName(userStringInput());
                case 3 -> client.setAge(userIntInput(MIN_CLIENT_AGE,MAX_CLIENT_AGE));
                case 4 -> client.setPhoneNumber(userStringInput());
                case 5 -> {
                    System.out.print("Please enter address's road : ");
                    client.getAddress().setRoad(userStringInput());
                    System.out.print("Please enter address's town : ");
                    client.getAddress().setTown(userStringInput());
                }
            }
        }while (userChoice != 0);

        return client;
    }
}
