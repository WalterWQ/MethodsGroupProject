package group8.project_files;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Class file for all database related details and functions
 */
public class Database {

    /**
     * Connection Function
     *<p>Checks if can connect to the database successfully if not it throws a Exception</p>
     */
    public static void connect() {
        // Database URL to load from
        var url = "jdbc:sqlite:fullDatabase.sqlite";

        try (var conn = DriverManager.getConnection(url)) {
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Database Sorting Function
     *<p>Sorts Database Based on user passed vars</p>
     */
    public static void SortDatabase(boolean sortType, Integer AmountToShow) {
        connect();


    }


    public static void main(String[] args) {
        connect();
    }
}