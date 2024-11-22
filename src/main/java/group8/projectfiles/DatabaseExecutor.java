package group8.projectfiles;

import java.io.*;
import java.sql.*;

/**
 * This class is responsible for allowing us to run .sql files, it manages comments and errors
 */
public class DatabaseExecutor {
    // DB login info
    private final String jdbcUrl = "jdbc:mysql://mysql:3306/world"; // Update with your database URL
    private final String username = "root"; // Database username
    private final String password = "rootpassword"; // Database password

    public void runSqlFile(String filePath) {
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
            System.out.println("Connected to the database successfully!");
            executeSqlFile(connection, filePath);
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void executeSqlFile(Connection connection, String filePath) {
        // Display the current working directory
        System.out.println("Current working directory: " + new File(".").getAbsolutePath());
        try (InputStream inputStream = new FileInputStream(filePath);
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             Statement statement = connection.createStatement()) {

            StringBuilder sqlBuilder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Skip empty lines and comments
                if (line.isEmpty() || line.startsWith("--") || line.startsWith("/*") || line.startsWith("*/")) {
                    continue;
                }

                sqlBuilder.append(line);

                // Execute the SQL when reaching a semicolon
                if (line.endsWith(";")) {
                    String sql = sqlBuilder.toString();
                    statement.execute(sql);
                    System.out.println("Executed: " + sql);
                    sqlBuilder.setLength(0); // Clear the builder
                }
            }

            System.out.println("SQL file executed successfully!");
        } catch (IOException e) {
            System.err.println("Error reading the SQL file: " + e.getMessage());
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error executing SQL statement: " + e.getMessage());}}}
