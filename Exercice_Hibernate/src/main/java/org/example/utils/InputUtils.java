package org.example.utils;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import static org.example.constants.Constants.*;

public class InputUtils {
    public static String userStringInput() {
        Scanner sc = new Scanner(System.in);
        String name = sc.nextLine();
        System.out.println();
        return name.trim();
    }

    public static LocalDate userDateInput() {
        int day;
        int month;
        int year;

        System.out.print("Please enter the day : ");
        day = userRangeIntInput(MIN_DAY_DATE, MAX_DAY_DATE);
        System.out.print("Please enter the month : ");
        month = userRangeIntInput(MIN_MONTH_DATE, MAX_MONTH_DATE);
        System.out.print("Please enter the year : ");
        year = userRangeIntInput(MIN_YEAR_DATE, MAX_YEAR_DATE);

        return LocalDate.of(year, month, day);
    }

    public static int userRangeIntInput(int minChoice, int maxChoice) {
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

        System.out.println();
        return inputChoice;
    }

    public static int userIntInput() {
        int inputChoice;
        Scanner sc = new Scanner(System.in);

        do {
            try {
                inputChoice = sc.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Not an integer.");
            }
        } while (true);

        System.out.println();
        return inputChoice;
    }

    public static double userDoubleInput() {
        double inputChoice;
        Scanner sc = new Scanner(System.in);

        do {
            try {
                inputChoice = sc.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Not a double.");
            }
        } while (true);

        return inputChoice;
    }
}
