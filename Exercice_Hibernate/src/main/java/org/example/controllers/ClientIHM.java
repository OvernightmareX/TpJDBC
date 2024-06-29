package org.example.controllers;

import org.example.services.ArticleService;
import org.example.services.ClientService;

import static org.example.constants.Constants.MAX_CLIENT_CRUD_CHOICE;
import static org.example.utils.InputUtils.userRangeIntInput;

public class ClientIHM {
    ClientService clientService = new ClientService();

    public void StartClientIHM(){
        int userChoice;

        do {
            userChoice = displayClientCRUDMenu();
            switch (userChoice) {
                case 1 -> clientService.createClient();
                case 2 -> clientService.modificationArticle();
                case 3 -> clientService.deleteArticle();
                case 4 -> clientService.displayClientByID();
                case 5 -> clientService.displayAllClient();
            }
        } while (userChoice != 0);
    }

    public static int displayClientCRUDMenu(){
        System.out.println("\n=== Client manager ===\n");
        System.out.println("1. Create");
        System.out.println("2. Modification");
        System.out.println("3. Delete");
        System.out.println("4. Display client by ID");
        System.out.println("5. Display all clients");
        System.out.println("0. Quit client manager\n");

        System.out.print("Your choice :");
        return userRangeIntInput(0,MAX_CLIENT_CRUD_CHOICE);
    }
}
