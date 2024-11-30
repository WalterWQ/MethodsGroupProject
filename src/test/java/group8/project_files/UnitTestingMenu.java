package group8.project_files;

import org.junit.jupiter.api.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Class that contains all the unit testing for the full program
 */
public class UnitTestingMenu {

    /**
     * Initialise Variables
     */
    private Menu menu;
    private ByteArrayOutputStream outputStream;

    /**
     * Setup Testing and initialise menu for testing
     */
    @BeforeEach
    public void setup() {
        menu = new Menu();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    /**
     * Checks and sees if the menu works with the exit function and closes correctly
     */
    @Test
    public void testExit() {
        // Simulating the input "exit"
        simulateInput("exit\n");

        // Running the menu start
        menu.start(new Scanner(System.in));

        // Fetching the output produced by the menu
        String output = outputStream.toString();

        // Asserting if the program prints "Exiting the program"
        assertTrue(output.contains("Exiting the program"), "The program should print 'Exiting the program' when 'exit' is entered.");
    }

    /**
     * Checks and verify if the invalid input detections works and ensures that users can only select correct menu options
     */
    @Test
    public void testInvalidOption() {
        // Simulating an invalid option followed by "exit"
        simulateInput("invalid\nexit\n");

        // Running the menu start
        menu.start(new Scanner(System.in));

        // Fetching the output produced by the menu
        String output = outputStream.toString();

        // Asserting if the program responds to invalid input
        assertTrue(output.contains("Invalid option"), "The program should print 'Invalid option' when invalid input is entered.");
    }

    /**
     * Ensures the menu options are able to be selected and ran
     */
    @Test
    public void testOptionSelection() {
        // Simulating valid input "1" followed by "exit"
        simulateInput("1\nexit\n");

        // Running the menu start
        menu.start(new Scanner(System.in));

        // Fetching the output produced by the menu
        String output = outputStream.toString();

        // Asserting if the program responds to valid option
        assertTrue(output.contains("Option 1 selected!"), "The program should print 'Option 1 selected!' when '1' is entered!.");
    }


    /**
     * Simple function which simulates user input, Pass a string/data to this and it will be pushed to the menu like a user would do
     * @param data
     */
    private void simulateInput(String data) {
        InputStream inputStream = new ByteArrayInputStream(data.getBytes());
        System.setIn(inputStream);
    }
}
