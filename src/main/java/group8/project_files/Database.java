package group8.project_files;


import java.sql.*;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * This class is responsible for manipulation of the database
 */
public class Database {
    // Create db execute instance
    DatabaseExecutor dbExec = new DatabaseExecutor();
    // Login details for MySQL DB (adjusted for Docker Compose)
    private final String jdbcUrl = "jdbc:mysql://mysql:3306/"; // Use 'mysql' as the hostname
    private final String username = "root";                      // Default username
    private final String password = "rootpassword";              // Password (as set in docker-compose)


    /**
     * This method proves the DB connected and displays tables
     */
    public void initDB() {
        //Delay init to give docker SQL container time to start
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Connect to MySQL container
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            // Print success message if connection is successful
            System.out.println("Connected to MYSQL database!");


            // Show tables and check if empty
            Boolean isEmpty = showTables(connection);

            //if empty populate
            dbExec.populateDb(isEmpty);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    /**
     * This method runs a query to show all tables inside of the db
     * @param connection db connection
     */
    private static boolean showTables(Connection connection) {
        String query = "SHOW TABLES;";  // SQL command to show all tables
        int counter = 0;

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("Tables in the database:");
            while (resultSet.next()) {
                // Display each table name
                System.out.println(resultSet.getString(1));  // Table name is in the first column
                counter++;
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving tables: " + e.getMessage());
        }
        // If tables exist
        if(counter > 0) {
            return false;
        }
        // if no tables exist
        return true;
    }

    /**
     * This method will display the top populated cities within (world,continent,region,country,district)
     */
    public static void getTopCities(){
        //Scanner for user input
        Scanner userScanner = new Scanner(System.in);

        //Get top amount from user
        int topAmount = -1;
        System.out.println("Input top amount of cities you would like to see");
        topAmount = Integer.parseInt(userScanner.nextLine());

        //Get scope from user (world,continent,region,country,district)
        int userScopeChoice = -1;
        System.out.println("Select the scope of your search:");
        System.out.println("1 - World");
        System.out.println("2 - Continent");
        System.out.println("3 - Region");
        System.out.println("4 - Country");
        System.out.println("5 - District");
        userScopeChoice = Integer.parseInt(userScanner.nextLine());

        // Create Query
        String query = "SELECT *"

        //Run Query

        //Display Results
    }
}
