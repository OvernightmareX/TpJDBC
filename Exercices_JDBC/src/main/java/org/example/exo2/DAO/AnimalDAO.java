package org.example.exo2.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.example.exo2.Constants.TypeAnimalLoading;
import org.example.exo2.Entity.Animal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.example.exo2.ZooIHM.*;

public class AnimalDAO {

    public static void loadAnimal(TypeAnimalLoading type, EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        Animal animal = null;
        List<Animal> animalList = new ArrayList<>();

        switch (type) {
            case BY_ID:
                System.out.print("Please enter the id you're looking for : ");
                animal = em.find(Animal.class, userChoice(0, 200));
                System.out.println(animal);
                break;
            case BY_NAME:
                System.out.print("Please enter the name you're looking for : ");
                animalList = em.createQuery("SELECT a FROM Animal a WHERE a.name LIKE :animalName")
                        .setParameter("animalName", userNameInput())
                        .getResultList();
                System.out.println(animalList);
                break;
            case BY_DIET:
                System.out.print("Please enter the diet you're looking for : ");
                animalList = em.createQuery("SELECT a FROM Animal a WHERE a.regimeType = :animalDiet")
                        .setParameter("animalDiet", userDietInput())
                        .getResultList();
                System.out.println(animalList);
                break;
        }
        em.close();
    }

    public static void saveAnimal(Animal animal, EntityManagerFactory emf) throws SQLException {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(animal);
        em.getTransaction().commit();
        System.out.println(animal);
        em.close();
    }
}
