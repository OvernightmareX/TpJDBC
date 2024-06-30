package org.example.utils;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import static org.example.constants.Constants.*;

public class InputUtils {
    private static final Scanner sc = new Scanner(System.in);

    public static String userStringInput() {
        System.out.println();
        return sc.nextLine().trim();
    }

    public static LocalDate userDateInput() {
        System.out.print("Please enter the day: ");
        int day = userRangeIntInput(MIN_DAY_DATE, MAX_DAY_DATE);
        System.out.print("Please enter the month: ");
        int month = userRangeIntInput(MIN_MONTH_DATE, MAX_MONTH_DATE);
        System.out.print("Please enter the year: ");
        int year = userRangeIntInput(MIN_YEAR_DATE, MAX_YEAR_DATE);

        return LocalDate.of(year, month, day);
    }

    public static int userRangeIntInput(int minChoice, int maxChoice) {
        do {
            int inputChoice = getIntInput();
            if (inputChoice >= minChoice && inputChoice <= maxChoice) {
                System.out.println();
                return inputChoice;
            } else {
                System.out.println("Not a correct answer.");
                System.out.println("(The input must be between " + minChoice + " and " + maxChoice + ")");
            }
        } while (true);
    }

    public static int userIntInput() {
        System.out.println();
        return getIntInput();
    }

    public static double userDoubleInput() {
        do{
            try {
                return sc.nextDouble();
            } catch (InputMismatchException e) {
                System.out.println("Not a double. Please enter a valid number.");
                sc.next();
            }
        } while (true);
    }

    private static int getIntInput() {
         do{
            try {
                return sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Not an integer. Please enter a valid number.");
                sc.next();
            }
        } while (true);
    }
}
