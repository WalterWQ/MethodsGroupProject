package group8.project_files;

import java.util.Scanner;

/**
 * A simple menu that allows the user to select 1-5 menu options or exit to leave the program
 */

public class Menu {

    /**
     * A simple menu that allows the user to select 1-5 menu options or they can type exit to leave the menu / application
     *
     */
    public void start(Scanner scanner, Database db) {
        String input = ""; // Variable to store the user's input

        // Infinite loop to repeatedly display the menu until "exit" is typed
        while (true) {
            // Display the menu options
            System.out.println("----------------- MAIN MENU -----------------");
            System.out.println("Please choose an option (type 'exit' to quit):");
            System.out.println("1. Top Cities(N)");
            System.out.println("2. Top Cities(LRG>SML)");
            System.out.println("3. Top Population (World,Continent,Region)");
            System.out.println("4. Top Capital Cities(LRG>SML)");
            System.out.println("5. Top Capital Cities(N)");
            System.out.println("6. Top Population Living/Not Cities");
            System.out.println("7. Language Statistics");
            System.out.println("----------------- MAIN MENU END -----------------");

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
                    db.getDbQuery().getTopCitiesN();
                    break;
                case "2":
                    db.getDbQuery().getTopCitiesAll();
                    break;
                case "3":
                    db.getDbQuery().getTopCountry();
                    break;
                case "4":
                    db.getDbQuery().getTopCapitalCities();
                    break;
                case "5":
                    db.getDbQuery().getTopCapitalCitiesN();
                    break;
                case "6":
                    db.getDbQuery().getPopulationLivingCities();
                    break;
                case "7":
                    db.getDbQuery().getLanguageStatisticsAll();
                default:
                    System.out.println("Invalid option, please try again.");
                    break; // If the input is invalid ask the user again
            }
        }

        // Close the scanner
        scanner.close();
    }
}

