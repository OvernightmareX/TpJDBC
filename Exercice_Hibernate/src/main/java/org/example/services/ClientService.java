package org.example.services;

import org.example.entities.*;
import org.example.repositories.ClientRepository;
import org.example.utils.InputUtils;

import java.util.List;

public class ClientService {
    private ClientRepository clientRepository = new ClientRepository();

    public void createClient(){
        System.out.println("=== Client creation ===\n");

        Client client = new Client();
        setClient(client);

        clientRepository.save(client);
    }

    public void modificationArticle(){
        System.out.println("=== Client modification ===\n");
        int userInput;

        displayAllClient();

        Client clientSelected = selectClientByID();
        if(clientSelected == null)
            return;

        do {
            System.out.println("\n=== Selected client's infos ===");
            displayClientModificationMenu(clientSelected);
            System.out.print("Your choice : ");
            userInput = InputUtils.userRangeIntInput(0, 6);
            if(userInput != 0)
                modificationArticleValue(clientSelected, userInput);

        }while (userInput != 0);
    }

    public void deleteArticle(){
        System.out.println("\n=== Client modification ===\n");

        displayAllClient();

        Client clientSelected = selectClientByID();
        if(clientSelected == null)
            return;

        clientRepository.delete(clientSelected);
    }

    public void displayClientByID(){
        Client client = selectClientByID();
        if(client != null)
            System.out.println(client);
    }

    public void displayAllClient(){
        List<Client> clients = selectAllClients();
        if(clients == null)
            return;

        for(Client client : clients){
            System.out.println(client);
        }
    }

    public void modificationArticleValue(Client client, int inputToUpdate){
        switch (inputToUpdate){
            case 1 :
                System.out.print("Please enter client's name : ");
                client.setName(InputUtils.userStringInput());
                break;
            case 2 :
                System.out.print("Please enter client's email : ");
                client.setEmail(InputUtils.userStringInput());
                break;
        }
        clientRepository.save(client);
    }

    public void setClient(Client client){
        System.out.print("Please enter client's name : ");
        client.setName(InputUtils.userStringInput());
        System.out.print("Please enter client's email : ");
        client.setEmail(InputUtils.userStringInput());
    }

    public Client selectClientByID(){
        System.out.println("Please enter client's ID : ");
        int userInput = InputUtils.userIntInput();
        Client client = clientRepository.findById(Client.class, userInput);

        if(client == null){
            System.out.println("Client's ID not found");
            return null;
        }

        return client;
    }

    public List<Client> selectAllClients(){
        List<Client> clients = clientRepository.findAll(Client.class);

        if(clients.isEmpty()){
            System.out.println("No clients");
            return null;
        }

        return clients;
    }

    public static void displayClientModificationMenu(Client client){
        System.out.println("\n1. Name : " + client.getName());
        System.out.println("2. Email : " + client.getEmail());
        System.out.println("0. Quit modifications\n");
    }
}
