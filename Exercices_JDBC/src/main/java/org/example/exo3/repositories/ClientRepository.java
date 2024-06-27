package org.example.exo3.repositories;

import jakarta.persistence.EntityManager;
import org.example.exo3.entity.Client;

import java.util.List;

public class ClientRepository {

    public static List<Client> findAllClient(EntityManager em){
        return em.createQuery("SELECT c FROM Client c")
                .getResultList();
    }

    public static Client findClientByID(EntityManager em, int id){
        return em.find(Client.class, id);
    }

    public static void saveClient(EntityManager em, Client clt){
        if(clt == null)
            throw new IllegalArgumentException("Impossible to delete, client null");

        em.getTransaction().begin();
        em.persist(clt.getAddress());
        em.persist(clt);
        em.getTransaction().commit();
    }

    public static void deleteClient(EntityManager em, Client clt){
        if(clt == null)
            throw new IllegalArgumentException("Impossible to delete, client null");

        em.getTransaction().begin();
        em.remove(clt);
        em.getTransaction().commit();
    }
}
