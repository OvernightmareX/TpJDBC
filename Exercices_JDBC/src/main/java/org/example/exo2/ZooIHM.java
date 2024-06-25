package org.example.exo2;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.exo2.Constants.RegimeType;
import org.example.exo2.Constants.TypeAnimalLoading;
import org.example.exo2.DAO.AnimalDAO;
import org.example.exo2.Entity.Animal;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import static org.example.exo2.Constants.Constants.*;

public class ZooIHM {

    public static void StartZooManager() throws SQLException {
        int userChoice;
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");

        do {
            displayMainMenu();
            System.out.println("Your choice : ");
            userChoice = userChoice(0, MAX_MAIN_MENU_CHOICE);

            switch (userChoice) {
                case 1 -> displayAddAnimal(emf);
                case 2 -> AnimalDAO.loadAnimal(TypeAnimalLoading.BY_ID, emf);
                case 3 -> AnimalDAO.loadAnimal(TypeAnimalLoading.BY_NAME, emf);
                case 4 -> AnimalDAO.loadAnimal(TypeAnimalLoading.BY_DIET, emf);
            }
        } while (userChoice != 0);

        emf.close();
        System.out.println("Thanks for using our program ! ");
    }

    public static void displayMainMenu() {
        System.out.println("\n=== Zoo manager ===\n");
        System.out.println("1. Create animal");
        System.out.println("2. Search by id");
        System.out.println("3. Search by name");
        System.out.println("4. Search by diet");
        System.out.println("0. Quit");
    }

    public static void displayAddAnimal(EntityManagerFactory emf) throws SQLException {
        Animal animal = new Animal();

        System.out.print("Please enter the animal's name : ");
        animal.setName(userNameInput());
        System.out.print("Please enter the animal's age : ");
        animal.setAge(userChoice(MIN_ANIMAL_AGE, MAX_ANIMAL_AGE));
        animal.setRegimeType(userDietInput());
        animal.setArrivedDate(userDateInput());

        AnimalDAO.saveAnimal(animal, emf);
    }

    public static String userNameInput() {
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        return name.trim();
    }

    public static RegimeType userDietInput() {
        System.out.println("Please enter the animal's diet : ");
        System.out.println("1. Carnivore");
        System.out.println("2. Herbivore");
        System.out.println("3. Omnivore");
        int userChoice = userChoice(1, 3);

        return switch (userChoice) {
            case 1 -> RegimeType.CARNIVORE;
            case 2 -> RegimeType.HERBIVORE;
            case 3 -> RegimeType.OMNIVORE;
            default -> throw new IllegalStateException("Unexpected value: " + userChoice);
        };
    }

    public static LocalDate userDateInput() {
        int day;
        int month;
        int year;

        System.out.print("Please enter the day of arrival : ");
        day = userChoice(MIN_DAY_DATE, MAX_DAY_DATE);
        System.out.print("Please enter the month of arrival : ");
        month = userChoice(MIN_MONTH_DATE, MAX_MONTH_DATE);
        System.out.print("Please enter the year of arrival : ");
        year = userChoice(MIN_YEAR_DATE, MAX_YEAR_DATE);

        return LocalDate.of(year, month, day);
    }

    public static int userChoice(int minChoice, int maxChoice) {
        int inputChoice;
        Scanner sc = new Scanner(System.in);

        do {
            try {
                inputChoice = sc.nextInt();
                if (inputChoice < minChoice || inputChoice > maxChoice) throw new IllegalArgumentException();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Not an integer.");
            } catch (IllegalArgumentException e) {
                System.out.println("Not a correct answer.");
                System.out.println("(The input must be between " + minChoice + " and " + maxChoice + ")");
            }
        } while (true);

        return inputChoice;
    }
}
