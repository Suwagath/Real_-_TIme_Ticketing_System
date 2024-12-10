package main.util;

import java.util.Scanner;


public class UserInputGetCollection {

    // To get inputs
    private static Scanner scanner = new Scanner(System.in);

    // Get integer user inputs
    public int getUserInputInt(String prompt) {
        try {
            System.out.print(prompt);
            return scanner.nextInt();

        } catch (Exception e) {
            System.out.println("Invalid input type input expect Integer");
            scanner = new Scanner(System.in);
            return -1;
        }
    }

    // Get float user inputs
    public double getUserInputDouble(String prompt) {
        try {
            System.out.print(prompt);
            return scanner.nextDouble();

        } catch (Exception e) {
            System.out.println("Invalid input type input expect Float");
            scanner = new Scanner(System.in);
            return -1;
        }
    }

    // Get string user inputs
    public String getUserInputString(String prompt) {
        try {
            System.out.print(prompt);
            return scanner.next();

        } catch (Exception e) {
            System.out.println("Invalid input type input expect String");
            scanner = new Scanner(System.in);
            return null;
        }
    }
}
