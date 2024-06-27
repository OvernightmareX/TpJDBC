package org.example.exo3.service;

import jakarta.persistence.EntityManager;
import org.example.exo3.entity.Client;
import org.example.exo3.entity.Event;
import org.example.exo3.repositories.TicketRepository;

import java.sql.SQLException;

public class TicketService {
    public static void ticketOrdering(EntityManager em) throws SQLException {
        Client clientSelected = ClientService.selectClient(em);
        if(clientSelected == null)
            return;

        Event eventSelected = EventService.selectEvent(em);
        if(eventSelected == null)
            return;

        TicketRepository.saveTicket(em, clientSelected, eventSelected);
    }
}
