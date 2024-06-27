package org.example.exo3.repositories;

import jakarta.persistence.EntityManager;
import org.example.exo3.constants.TypePlace;
import org.example.exo3.entity.Client;
import org.example.exo3.entity.Event;
import org.example.exo3.entity.Ticket;

import java.util.List;

public class TicketRepository {

    public static List<Ticket> findAllTicketByEventID(EntityManager em, int eventID){
        return em.createQuery("SELECT t FROM Ticket t WHERE t.event.id_event = :event")
                .setParameter("event", eventID)
                .getResultList();
    }

    public static void saveTicket(EntityManager em, Client clt, Event event){
        if(event.getRemainingPlace() == 0 ){
            System.out.println("No more available ticket.");
            return;
        }

        Ticket ticket = Ticket.builder()
        .client(clt)
        .event(event)
        .placeNum(event.getNumberOfPlace() - event.getRemainingPlace())
        .typePlace(TypePlace.VIP)
        .build();

        event.setRemainingPlace(event.getRemainingPlace()-1);

        em.getTransaction().begin();
        em.persist(event);
        em.persist(ticket);
        em.getTransaction().commit();
    }
}
