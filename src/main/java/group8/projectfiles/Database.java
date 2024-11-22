package group8.projectfiles;

import java.sql.*;

/**
 * This class is responsible for creation and manipulation of the database
 */
public class Database {

    // Login details for mysql DB
    private final String jdbcUrl = "jdbc:h2:mem:testdb"; // In-memory database
    private final String username = "sa";              // Default username
    private final String password = "";                // Default password

    /**
     * Constructor for db, tells H2 to load in order for DB to work in-memory
     */
    Database() {
        // On creation load H2
        try {
            // Explicitly load the H2 driver
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load H2 driver", e);
        }
    }

    /**
     * Class that tests for H2 in-memory db connection
     */
    public void testConnect() {
       try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
           System.out.println("Connected to H2 in-memory database!");
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }
   }
}
