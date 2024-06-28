package org.example.exo3.service;

import org.example.exo3.entity.Address;

import static org.example.exo3.controllers.TicketingIHM.userStringInput;

public class AddressService {
    public static Address createAdress(){
        System.out.print("Please enter address's road : ");
        String road = userStringInput();
        System.out.print("Please enter address's town : ");
        String town = userStringInput();

        return Address.builder().road(road).town(town).build();
    }
}
