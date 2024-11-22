package group8.projectfiles;

import java.sql.*;

public class Database {
    String currentDirectory = System.getProperty("user.dir");
    String dbLocation = "\\src\\main\\resources\\data.sqlite";
    String url = "jdbc:sqlite:" + currentDirectory + dbLocation;
    public void testConnection() {
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                System.out.println("Connected to the SQLite database.");

                // Create a statement
                Statement stmt = conn.createStatement();
                String sql = "SELECT * FROM city"; // Replace with your query

                // Execute the query and process the results
                ResultSet rs = stmt.executeQuery(sql);
                while (rs.next()) {
                    System.out.println("Column1: " + rs.getString("Name"));
                    System.out.println("Column2: " + rs.getInt("ID"));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } }
    }


