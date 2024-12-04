package group8;

import group8.project_files.Database;
import group8.project_files.DatabaseExecutor;
import org.testcontainers.containers.MySQLContainer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class integrationtests {

    private static MySQLContainer<?> mysqlContainer;
    private static DatabaseExecutor dbExecutor;
    private static Connection connection; // Add the connection variable
    private static final String jdbcUrl = "jdbc:mysql://localhost:33060/"; // Use 'mysql' as the hostname
    private static final String username = "root";                      // Default username
    private static final String password = "rootpassword";              // Password (as set in docker-compose)

    /**
     * Setup connection to mysql database
     * @throws SQLException
     */
    @BeforeAll
    static void setupDatabase() throws SQLException {
        Database db = new Database();
        try {
            connection = DriverManager.getConnection(jdbcUrl,username,password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Setup database strcuture
     * @throws SQLException
     */
    @Test
    void setupDatabaseStructure() throws SQLException {
        Database db = new Database();
        db.initDB(jdbcUrl, username, password);
    }

    /**
     * Checks if it can connect to the database correctly
     * @throws Exception
     */
    @Test
    void testDatabaseConnection() throws Exception {
        // Test your database connection here (e.g., assert that the connection is not null)
        assertNotNull(connection, "Database connection should not be null");
        System.out.println("Database connection successful!");
    }

    /**
     * Checks if the country table exists
     * @throws SQLException
     */
    @Test
    void testTableExists() throws SQLException {
        // Check if the 'country' table exists
        Statement useStatement = connection.createStatement();
        useStatement.execute("USE world;");
        String checkTableQuery = "SHOW TABLES LIKE 'country'";
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(checkTableQuery)) {
            if (rs.next()) {
                System.out.println("Table 'country' exists.");
            } else {
                System.err.println("Table 'country' does not exist.");
                fail("Table 'country' should exist");
            }
        }
    }

    /**
     * Checks if the create.sql file added all the data correctly
     * @throws SQLException
     */
    @Test
    public void testInsertcountryData() throws SQLException {
        Statement useStatement = connection.createStatement();
        useStatement.execute("USE world;");
        String query = "SELECT * FROM country WHERE Code = 'ABW'";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            assertTrue(rs.next(), "country with code 'ABW' should exist");

            // Assert that the values match the expected data for 'ABW'
            assertEquals("ABW", rs.getString("Code"));
            assertEquals("Aruba", rs.getString("Name"));
            assertEquals("North America", rs.getString("Continent"));
            assertEquals("Caribbean", rs.getString("Region"));
            assertEquals(193.00, rs.getDouble("SurfaceArea"));
            assertEquals(103000, rs.getInt("Population"));
            assertEquals(78.4, rs.getDouble("LifeExpectancy"));
            assertEquals(828.00, rs.getDouble("GNP"));
        }
    }

    /**
     * checks to ensure only the data wanted was added
     * @throws SQLException
     */
    @Test
    public void testRowCountAfterInsertion() throws SQLException {
        Statement useStatement = connection.createStatement();
        useStatement.execute("USE world;");
        // Test the number of rows in the 'country' table after inserting the data
        String query = "SELECT COUNT(*) AS rowCount FROM country";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            assertTrue(rs.next(), "Row count query failed");
            int rowCount = rs.getInt("rowCount");

            // Checks total amount of countrys matches expected amount of 239
            assertEquals(239, rowCount, "Row count should match the expected number of countries.");
        }
    }

    /**
     * Tests to ensure primary key constraints are being followed
     * @throws SQLException
     */
    @Test
    public void testPrimaryKeyConstraint() throws SQLException {
        Statement useStatement = connection.createStatement();
        useStatement.execute("USE world;");
        // Tests the primary key by attempting to insert a duplicate country
        String duplicateInsertQuery = "INSERT INTO country (Code, Name, Continent, Region, SurfaceArea, Population, LifeExpectancy, GNP) VALUES ('ABW', 'Aruba', 'North America', 'Caribbean', 193.00, 103000, 78.4, 828.00)";
        try (PreparedStatement stmt = connection.prepareStatement(duplicateInsertQuery)) {
            // should throw a exception due to the primary key constraint violation
            stmt.executeUpdate();
            fail("Expected SQLException for duplicate primary key.");
        } catch (SQLException e) {
            // should do nothing as the test passes
            assertTrue(e.getMessage().contains("Duplicate entry"));
        }
    }

    /**
     * tests to ensure no null fields are present in the database
     * @throws SQLException
     */
    @Test
    public void testNotNullFields() throws SQLException {
        Statement useStatement = connection.createStatement();
        useStatement.execute("USE world;");
        // Test to ensure that required fields like Code, Name and Population are not null
        String query = "SELECT Code, Name, Population FROM country WHERE Code IS NULL OR Name IS NULL OR Population IS NULL";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            assertFalse(rs.next(), "No country should have NULL values in 'Code', 'Name' or 'Population'.");
        }
    }

    /**
     * Tests data types in the table to ensure they match expected
     * @throws SQLException
     */
    @Test
    public void testValidDataTypes() throws SQLException {
        Statement useStatement = connection.createStatement();
        useStatement.execute("USE world;");
        String query = "SELECT SurfaceArea, Population, LifeExpectancy FROM country WHERE Code = 'ABW'";
        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            assertTrue(rs.next(), "country 'ABW' should exist");

            // Validate that the data types match
            assertTrue(rs.getDouble("SurfaceArea") > 0, "SurfaceArea should be a valid number.");
            assertTrue(rs.getInt("Population") > 0, "Population should be a valid number.");
            assertTrue(rs.getDouble("LifeExpectancy") > 0, "LifeExpectancy should be a valid number.");
        }
    }

}
