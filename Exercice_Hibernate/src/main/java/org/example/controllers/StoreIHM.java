package org.example.controllers;

import org.example.utils.InputUtils;

import static org.example.constants.Constants.MAX_MAIN_MENU_CHOICE;

public class StoreIHM {
    public void StartStore(){
        int userChoice;
        ArticlesIHM articlesIHM = new ArticlesIHM();
        CustomerIHM customerIHM = new CustomerIHM();
        SaleIHM saleIHM = new SaleIHM();

        do {
            displayMainMenu();
            System.out.print("Your choice : ");
            userChoice = InputUtils.userRangeIntInput(0, MAX_MAIN_MENU_CHOICE);

            switch (userChoice) {
                case 1 -> articlesIHM.StartArticleIHM();
                case 2 -> customerIHM.StartCustomerIHM();
                case 3 -> saleIHM.StartSaleIHM();
            }
        } while (userChoice != 0);

        System.out.println("Thanks for using our program ! ");
    }

    public static void displayMainMenu(){
        System.out.println("\n=== Store manager ===\n");
        System.out.println("1. Manage articles");
        System.out.println("2. Manage clients");
        System.out.println("3. Manage sales");
        System.out.println("0. Quit");
    }
}
