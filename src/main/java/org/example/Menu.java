package org.example;

import java.util.Scanner;

/**
 * A simple menu that allows the user to select 1-5 menu options or exit to leave the program
 */

public class Menu {

    /**
     * A simple menu that allows the user to select 1-5 menu options or they can type exit to leave the menu / application
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Scanner to read the users input
        String input = ""; // Variable to store the user's input

        // Infinite loop to repeatedly display the menu until "exit" is typed
        while (true) {
            // Display the menu options
            System.out.println("Please choose an option (type 'exit' to quit):");
            System.out.println("1. Option 1");
            System.out.println("2. Option 2");
            System.out.println("3. Option 3");
            System.out.println("4. Option 4");
            System.out.println("5. Option 5");

            // Read the users input in the menu and convert it to lowercase
            input = scanner.nextLine().trim().toLowerCase();

            // Check if the user typed exit and quit the program
            if (input.equals("exit")) {
                System.out.println("Exiting the program");
                break; // Exit the loop and kill the program
            }

            // Read the users input and selects the relative menu option
            switch (input) {
                case "1":
                    System.out.println("Option 1 selected!");
                    break;
                case "2":
                    System.out.println("Option 2 selected!");
                    break;
                case "3":
                    System.out.println("Option 3 selected!");
                    break;
                case "4":
                    System.out.println("Option 4 selected!");
                    break;
                case "5":
                    System.out.println("Option 5 selected!");
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
                    break; // If the input is invalid ask the user again
            }
        }

        // Close the scanner
        scanner.close();
    }
}

