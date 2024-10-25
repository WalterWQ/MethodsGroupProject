package group8.project_files;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class is used to access and manage the SQLite database
 */
public class Database {
    // Connects db
    static String url = "jdbc:sqlite:db.sqlite"; // Database file

    public void testConnect() {

        // DEBUG DATABASE CONNECTION
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) { // if connects
                System.out.println("DB CONNECTED!");
            }
            else { // if does not connect
                System.out.println("DB NOT CONNECTED!");
            }
            // display error if any are cought
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
