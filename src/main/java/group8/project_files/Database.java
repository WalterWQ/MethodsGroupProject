package group8.project_files;


import java.sql.*;
import java.util.concurrent.TimeUnit;

/**
 * This class is responsible for manipulation of the database
 */
public class Database {

    // Login details for MySQL DB (adjusted for Docker Compose)
    private final String jdbcUrl = "jdbc:mysql://mysql:3306/world"; // Use 'mysql' as the hostname
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
            showTables(connection);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    /**
     * This method runs a query to show all tables inside of the db
     * @param connection db connection
     */
    private static void showTables(Connection connection) {
        String query = "SHOW TABLES";  // SQL command to show all tables

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("Tables in the database:");
            while (resultSet.next()) {
                // Display each table name
                System.out.println(resultSet.getString(1));  // Table name is in the first column
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving tables: " + e.getMessage());
        }
    }
}
