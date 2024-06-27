package org.example.exo3.repositories;

import jakarta.persistence.EntityManager;
import org.example.exo3.entity.Event;
import org.example.exo3.entity.Ticket;

import java.util.List;

public class EventRepository {

    public static List<Event> findAllEvent(EntityManager em){
        return em.createQuery("SELECT e FROM Event e")
                .getResultList();
    }

    public static Event findEventByID(EntityManager em, int id){
        return em.find(Event.class, id);
    }

    public static void saveEvent(EntityManager em, Event event){
        if(event == null)
            throw new IllegalArgumentException("Impossible to delete, event null");

        List<Ticket> tickets = TicketRepository.findAllTicketByEventID(em, event.getId_event());
        event.setRemainingPlace(event.getNumberOfPlace() - tickets.size());

        em.getTransaction().begin();
        em.persist(event.getAddress());
        em.persist(event);
        em.getTransaction().commit();
    }

    public static void deleteEvent(EntityManager em, Event event){
        if(event == null)
            throw new IllegalArgumentException("Impossible to delete, event null");

        em.getTransaction().begin();
        em.remove(event);
        em.getTransaction().commit();
    }
}
