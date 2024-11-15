package group8.project_files;

import java.sql.*;

/**
 * This class is used to access and manage the SQLite database
 */
public class Database {
    // Create dynamic link for sqlite
    private static String workingDir = System.getProperty("user.dir");
    private static String dbPath = workingDir + "/src/main/java/group8/project_files/db.sqlite";
    private static String url = "jdbc:sqlite:" + dbPath; // Database file

    public void testConnect() {
        // DEBUG DATABASE CONNECTION
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) { // if connects
                System.out.println("DB CONNECTED!");
            } else { // if does not connect
                System.out.println("DB NOT CONNECTED!");
            }
            // display error if any are cought
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This filers the amount of top populated cities by amount selected by user in area selected by user
     *
     * @param type 0 = world, 1 = continent, 2 = region, 3 = country, 4 = district
     */
    public void getTopPopWithinType(int topAmount, int type) {
        // Create query based on user input
        String sqlQuery = switch (type) {
            case 0 -> "SELECT SUM(Population) AS total_population FROM country";
            case 1 -> "Continent";
            case 2 -> "Region";
            case 3 -> "Country";
            case 4 -> "District";
            default -> null;
        };

        // Run Query and report back to user via console
        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlQuery)) {

            if(rs.next()) {
                long totalPopulation = rs.getLong("total_population");
                System.out.println("The total population in World is: " + totalPopulation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
