package group8.project_files;


import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * This class is responsible for manipulation of the database
 */
public class Database {
    // Create db execute instance
    DatabaseExecutor dbExec = new DatabaseExecutor();
    //Create queries instance
    DatabaseQueries dbQuery = new DatabaseQueries();


    /**
     * This method proves the DB connected and displays tables
     */
    public void initDB(String url, String username, String password) {
        //Delay init to give docker SQL container time to start
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Connect to MySQL container
        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            // Print success message if connection is successful
            System.out.println("Connected to MYSQL database!");


            // Show tables and check if empty
            Boolean isEmpty = showTables(connection);

            //if empty populate
            dbExec.populateDb(isEmpty, url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database", e);
        }
    }

    /**
     * This method runs a query to show all tables inside of the db
     *
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
        if (counter > 0) {
            return false;
        }
        // if no tables exist
        return true;
    }

    public DatabaseQueries getDbQuery() {
        if (dbQuery == null) {
            System.err.println("Error: dbQuery is not initialized.");
            return null;
        }
        return dbQuery;
    }



}




