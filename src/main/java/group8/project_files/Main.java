package group8.project_files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {
    static String url = "jdbc:sqlite:db.sqlite"; // Database file

    public static void main(String[] args) {
        System.out.println("Hello world!");

        // DEBUG DATABASE CONNECTION
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("DB CONNECTED!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}