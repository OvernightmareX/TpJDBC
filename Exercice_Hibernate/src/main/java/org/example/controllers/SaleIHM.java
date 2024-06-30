package org.example.controllers;

import org.example.services.SaleService;

import static org.example.constants.Constants.MAX_SELL_CRUD_CHOICE;
import static org.example.utils.InputUtils.userRangeIntInput;

public class SaleIHM {
    private SaleService saleService = new SaleService();

    public void StartSaleIHM(){
        int userChoice;

        do {
            userChoice = displaySalesCRUDMenu();
            switch (userChoice) {
                case 1 -> saleService.createSale();
                case 2 -> saleService.displaySalesByArticle();
                case 3 -> saleService.displaySalesByPeriod();
                case 4 -> saleService.displaySalesByCustomer();
            }
        } while (userChoice != 0);
    }

    public static int displaySalesCRUDMenu(){
        System.out.println("\n=== Sales manager ===\n");
        System.out.println("1. Register a sale");
        System.out.println("2. Display sales by articles");
        System.out.println("3. Display sales by period");
        System.out.println("4. Display sales by clients");
        System.out.println("0. Quit sales manager\n");

        System.out.print("Your choice :");
        return userRangeIntInput(0,MAX_SELL_CRUD_CHOICE);
    }
}
