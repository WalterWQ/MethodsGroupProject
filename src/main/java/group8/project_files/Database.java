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
    // Login details for MySQL DB (adjusted for Docker Compose)
    private final String jdbcUrl = "jdbc:mysql://mysql:3306/"; // Use 'mysql' as the hostname
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


            // Show tables and check if empty
            Boolean isEmpty = showTables(connection);

            //if empty populate
            dbExec.populateDb(isEmpty);
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

    /**
     * This method will display the top populated cities within (world,continent,region,country,district)
     */
    public void getTopCities() {
        String query = "";
        //Scanner for user input
        Scanner userScanner = new Scanner(System.in);

        //Get top amount from user
        System.out.println("Input top amount of cities you would like to see");
        int topAmount = getValidIntegerInput(userScanner,1,99999);

        //Get scope from user (world,continent,region,country,district)
        System.out.println("----------------- SCOPE SELECT -----------------");
        System.out.println("Select the scope of your search:");
        System.out.println("1 - World");
        System.out.println("2 - Continent");
        System.out.println("3 - Region");
        System.out.println("4 - Country");
        System.out.println("5 - District");
        System.out.println("----------------- SCOPE SELECT END -----------------");
        int userScopeChoice = getValidIntegerInput(userScanner,1,5);


        switch (userScopeChoice) {
            // Top Cities World
            case 1:
                query = sqlTopWorld();
                break;
            // Top cities Continent
            case 2:
                query = sqlTopCities(userScanner);
                break;
            // top cities Region
            case 3:
                query = sqlTopRegion(userScanner);
                break;
            // top cities Country
            case 4:
                query = sqlTopCountry(userScanner);
                break;
            // top cities district
            case 5:
                query = sqlTopDistrict(userScanner);
                break;
        }

        //Run Query
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             // Create statement from query
             Statement useStatement = connection.createStatement();
             PreparedStatement queryStatement = connection.prepareStatement(query)) {

            //use world db
            useStatement.execute("USE world;");

            //Set int
            queryStatement.setInt(1, topAmount);

            // Execute the query
            ResultSet resultSet = queryStatement.executeQuery();

            // Display the results
            System.out.println("----------------- RESULTS -----------------");
            while (resultSet.next()) {
                String cityName = resultSet.getString("Name");
                int population = resultSet.getInt("Population");
                System.out.printf("%-10s | %d%n", cityName, population);
            }
            System.out.println("----------------- RESULTS END -----------------");

        } catch (SQLException e) {
            // Handle SQL exceptions
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * This class returns query which searches for top cities in world
     *
     * @return Query
     */
    private static String sqlTopWorld() {
        return "SELECT city.Name, city.Population " +
                "FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "ORDER BY city.Population DESC " +
                "LIMIT ?";
    }

    /**
     * This class creates a query and handles menu based on user input, Gets top cities in World
     *
     * @param userScanner System.in Scanner
     * @return Query
     */
    private String sqlTopCities(Scanner userScanner) {
        String continentChosen;
        String query;
        // Display Options
        ArrayList<String> continentsList = getAllContinents();

        // loop thru array and display options
        sqlMenu(continentsList);

        // Get input
        int userContinent = getValidIntegerInput(userScanner,1,continentsList.size()-1);
        continentChosen = continentsList.get(userContinent);
        // Fill based on input

        //Query
        query = "SELECT city.Name, city.Population " +
                "FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Continent = \"" + continentChosen + "\" " +
                "ORDER BY city.Population DESC " +
                "LIMIT ?";
        return query;
    }

    /**
     * This class creates a query and handles menu based on user input, Gets top cities in region
     *
     * @param userScanner System.in Scanner
     * @return Query
     */
    private String sqlTopRegion(Scanner userScanner) {
        String regionChosen;
        String query;
        // Display Options
        ArrayList<String> regionsList = getAllRegions();

        sqlMenu(regionsList);

        // Get input
        int userRegion = getValidIntegerInput(userScanner,1,regionsList.size()-1);
        regionChosen = regionsList.get(userRegion);

        //Query
        query = "SELECT city.Name, city.Population " +
                "FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Region = \"" + regionChosen + "\" " +
                "ORDER BY city.Population DESC " +
                "LIMIT ?";
        return query;
    }

    /**
     * This class create a query and handles manu based on user input, Gets top cities in country
     * @param userScanner System.in Scanner
     * @return query
     */
    private String sqlTopCountry(Scanner userScanner) {
        String countryChosen;
        String query;
        // Display Options
        ArrayList<String> regionsList = getAllCountries();

        sqlMenu(regionsList);

        // Get input
        int userRegion = getValidIntegerInput(userScanner,1,regionsList.size()-1);
        countryChosen = regionsList.get(userRegion);
        // Fill based on input

        //Query
        query = "SELECT city.Name, city.Population " +
                "FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "WHERE country.Name = \"" + countryChosen + "\" " +
                "ORDER BY city.Population DESC " +
                "LIMIT ?";
        return query;
    }

    /**
     * This class creates a query and handles manu based on user input, Gets top cities in district
     * @param userScanner System.in Scanner
     * @return query
     */
    private String sqlTopDistrict(Scanner userScanner) {
        String query;
        //Get Countries
        ArrayList<String> countriesList = getAllCountries();
        // Display countries menu
        sqlMenu(countriesList);

        //Get Country from user
        int userCountry = getValidIntegerInput(userScanner,1, countriesList.size()-1);

        // Convert choice to country name string
        String userCountryString = countriesList.get(userCountry);

        //Get Districts from userCountry
        ArrayList<String> districtsList = getAllDistricts(userCountryString);

        //Display Menu Districts
        sqlMenu(districtsList);

        //Get User District Choice
        int districtChosen = getValidIntegerInput(userScanner,1,districtsList.size()-1);

        //Convert district chosen to string
        String districtChosenString  = districtsList.get(districtChosen);


        //Query
        query = "SELECT city.Name, city.Population " +
                "FROM city " +
                "JOIN country ON city.CountryCode = country.Code " +
                "WHERE city.District = \"" + districtChosenString + "\" " +
                "ORDER BY city.Population DESC " +
                "LIMIT ?";
        return query;
    }

    /**
     * This class takes in a list and displays a menu based upon it
     * @param list ArrayList<String>
     */
    private static void sqlMenu(ArrayList<String> list) {
        System.out.println("----------------- MENU START -----------------");

        // loop thru array and display options
        for (int i = 1; list.size() > i; i++) {
            System.out.println(i + " - " + list.get(i));
        }

        // Make it clearer to read
        System.out.println("----------------- MENU END -----------------");
    }

    /**
     * This returns all regions as arrayList
     *
     * @return ArrayList
     */
    private ArrayList<String> getAllRegions() {
        // Initialzie array
        ArrayList<String> regionsList = new ArrayList<>();
        regionsList.add("0");
        //Query
        String query = "SELECT DISTINCT country.Region FROM country;";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

             // Create statement from query
             Statement useStatement = connection.createStatement();
             PreparedStatement statement = connection.prepareStatement(query)) {

            //use world db
            useStatement.execute("USE world;");


            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Display the results
            while (resultSet.next()) {
                regionsList.add(resultSet.getString("Region"));
            }

        } catch (SQLException e) {
            // Handle SQL exceptions
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
        // return list of regions
        return regionsList;
    }

    /**
     * This class returns all continents as arrayList
     *
     * @return ArrayList
     */
    private ArrayList<String> getAllContinents() {
        // Initialzie array
        ArrayList<String> continentsList = new ArrayList<>();
        continentsList.add("0");
        //Query
        String query = "SELECT DISTINCT country.Continent FROM country;";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

             // Create statement from query
             Statement useStatement = connection.createStatement();
             PreparedStatement statement = connection.prepareStatement(query)) {

            //use world db
            useStatement.execute("USE world;");


            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Display the results
            while (resultSet.next()) {
                continentsList.add(resultSet.getString("Continent"));
            }

        } catch (SQLException e) {
            // Handle SQL exceptions
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
        // return list of regions
        return continentsList;
    }

    private ArrayList<String> getAllCountries() {
        // Initialzie array
        ArrayList<String> countriesList = new ArrayList<>();
        countriesList.add("0");
        //Query
        String query = "SELECT DISTINCT country.Name FROM country;";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

             // Create statement from query
             Statement useStatement = connection.createStatement();
             PreparedStatement statement = connection.prepareStatement(query)) {

            //use world db
            useStatement.execute("USE world;");


            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Display the results
            while (resultSet.next()) {
                countriesList.add(resultSet.getString("Name"));
            }

        } catch (SQLException e) {
            // Handle SQL exceptions
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
        // return list of regions
        return countriesList;
    }

    private ArrayList<String> getAllDistricts(String country) {
        // Initialzie array
        ArrayList<String> districtsList = new ArrayList<>();
        districtsList.add("0");
        //Query
        String query = "SELECT DISTINCT city.District " +
                "FROM city " +
                "JOIN country ON city.CountryCode=country.Code " +
                "WHERE country.Name = \""+country+"\"";
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);

             // Create statement from query
             Statement useStatement = connection.createStatement();
             PreparedStatement statement = connection.prepareStatement(query)) {

            //use world db
            useStatement.execute("USE world;");


            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Display the results
            while (resultSet.next()) {
                districtsList.add(resultSet.getString("District"));
            }

        } catch (SQLException e) {
            // Handle SQL exceptions
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
        // return list of regions
        return districtsList;
    }

    /**
     * this class validates a int input
     * @param scanner system.in scanner
     * @param min minimum valid number
     * @param max maximum valid number
     * @return if input is valid
     */
    public static int getValidIntegerInput(Scanner scanner, int min, int max) {
        int validInput = -1;
        while (true) {
            try {
                validInput = Integer.parseInt(scanner.nextLine());
                if (validInput >= min && validInput <= max) {
                    break; // Valid input exit loop
                } else {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        }
        return validInput;
    }


}




