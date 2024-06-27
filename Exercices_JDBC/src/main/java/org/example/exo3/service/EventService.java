package org.example.exo3.service;

import jakarta.persistence.EntityManager;
import org.example.exo3.constants.TypeCRUD;
import org.example.exo3.entity.Address;
import org.example.exo3.entity.Event;
import org.example.exo3.entity.Ticket;
import org.example.exo3.repositories.EventRepository;
import org.example.exo3.repositories.TicketRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.example.exo3.service.AddressService.createAdress;
import static org.example.exo3.utils.TicketingIHM.*;

public class EventService {
    public static Event selectEvent(EntityManager em){
        int userChoice;

        System.out.println("\n=== Select the event ===\n");
        List<Event> events = displayAllEvent(em);
        if(events == null)
            return null;

        System.out.print("Select the event id : ");
        userChoice = userIntInput(1, events.size());

        return EventRepository.findEventByID(em, events.get(userChoice-1).getId_event());
    }

    public static Event createEvent(){
        System.out.print("Please enter event's name : ");
        String name = userStringInput();
        System.out.print("Please enter event's date : ");
        LocalDate date = userDateInput();
        System.out.print("Please enter event's time : ");
        LocalTime time = LocalTime.of(userIntInput(0,23), userIntInput(0,59));
        System.out.print("Please enter event's number of place : ");
        int numberOfPlace = userIntInput(0, Integer.MAX_VALUE);

        Address adress = createAdress();

        return new Event(name, adress, date, time, numberOfPlace);
    }

    public static void displayEventTicketOrdered(EntityManager em){
        Event eventSelected = selectEvent(em);
        if(eventSelected == null)
            return;

        List<Ticket> tickets = TicketRepository.findAllTicketByEventID(em, eventSelected.getId_event());

        System.out.println("=== " + eventSelected.getName() + " tickets ===");
        for(Ticket ticket : tickets){
            System.out.println(ticket);
        }
    }

    public static List<Event> displayAllEvent(EntityManager em){
        List<Event> events = EventRepository.findAllEvent(em);
        int counter=0;

        if(events.isEmpty()){
            System.out.println("No event");
            return null;
        }

        for(Event event : events){
            System.out.println(++counter + ". " +
                    event.getName() + " " +
                    event.getDate() + " at " +
                    event.getTime() + " (" +
                    event.getRemainingPlace() +
                    " remaining places) ");
        }
        return events;
    }

    public static void displayManageEvent(EntityManager em){
        int userInput =  displayCRUDMenu(TypeCRUD.EVENT);

        switch (userInput){
            case 1 -> EventRepository.saveEvent(em, createEvent());
            case 2 -> displayAllEvent(em);
            case 3 -> EventRepository.saveEvent(em, displayEventModification(em));
            case 4 -> EventRepository.deleteEvent(em, selectEvent(em));
        }
    }

    public static Event displayEventModification(EntityManager em){
        Event event = selectEvent(em);
        if(event == null)
            return null;

        int userChoice;
        do {
            displayEventModificationMenu(event);
            System.out.print("Select the information you want to change : ");
            userChoice= userIntInput(0,5);

            if(userChoice!= 0)
                System.out.println("Enter your modification : ");

            switch (userChoice){
                case 1 -> event.setName(userStringInput());
                case 2 -> event.setDate(userDateInput());
                case 3 -> event.setTime(LocalTime.of(userIntInput(0,23), userIntInput(0,59)));
                case 4 -> event.setNumberOfPlace(userIntInput(0, Integer.MAX_VALUE));
                case 5 -> {
                    System.out.print("Please enter address's road : ");
                    event.getAddress().setRoad(userStringInput());
                    System.out.print("Please enter address's town : ");
                    event.getAddress().setTown(userStringInput());
                }
            }
        }while (userChoice != 0);

        return event;
    }
}
