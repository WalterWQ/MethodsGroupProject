package group8.project_files;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class DatabaseQueries {
    // Login details for MySQL DB (adjusted for Docker Compose)
    private final String jdbcUrl = "jdbc:mysql://mysql:3306/"; // Use 'mysql' as the hostname
    private final String username = "root";                      // Default username
    private final String password = "rootpassword";

    /**
     * This method gets the population lrg->small from world,continent,region
     */
    public void getTopCountry() {
        //Open user input scanner
        Scanner userInput = new Scanner(System.in);
        //User choice int
        int userChoiceMenu;
        //Query
        String query = "";
        //Show menu
        System.out.println("--------- Get Top Country Population Menu ---------");
        System.out.println("1 - World");
        System.out.println("2 - Continent");
        System.out.println("3 - Region");
        userChoiceMenu = getValidIntegerInput(userInput,1,3);

        //Decide which query to use

        switch(userChoiceMenu) {
            // World
            case 1:
                query = "SELECT country.Name,country.Continent,country.Region,country.Population,city.Name AS CapitalName " +
                        "FROM world.country " +
                        "JOIN city ON city.ID=country.capital " +
                        "ORDER BY Population DESC; ";
                break;
                // Continent
            case 2:
                // Get continent menu and let user choose continent
                ArrayList<String> continentsList = getAllContinents();
                sqlMenu(continentsList);
                // user input
                int userChoice = getValidIntegerInput(userInput,1,continentsList.size()-1);
                // Convert to string
                String userChoiceString = continentsList.get(userChoice);

                query = "SELECT country.Name,country.Continent,country.Region,country.Population,city.Name AS CapitalName " +
                        "FROM world.country " +
                        "JOIN city ON city.ID=country.capital " +
                        "WHERE country.Continent = \""+ userChoiceString +"\" " +
                        "ORDER BY Population DESC; ";

                break;
                //Region
            case 3:
                //Get Continent
                ArrayList<String> regionsList = getAllRegions();
                sqlMenu(regionsList);
                //Get user input
                int userChoiceCase3 = getValidIntegerInput(userInput,1,regionsList.size()-1);
                //Convert to string for query
                String userChoiceCase3String = regionsList.get(userChoiceCase3);

                //Query
                query = "SELECT country.Name,country.Continent,country.Region,country.Population,city.Name AS CapitalName " +
                        "FROM world.country " +
                        "JOIN city ON city.ID=country.capital " +
                        "WHERE country.Region = \""+ userChoiceCase3String +"\" " +
                        "ORDER BY Population DESC; ";
                break;


        }


        // Run Query
        try(Connection conn = DriverManager.getConnection(jdbcUrl,username,password);
            Statement useStatement = conn.createStatement();
            Statement stmt = conn.createStatement()) {

            // use world
            useStatement.execute("USE world;");

            //Get result set
            ResultSet rs = stmt.executeQuery(query);

            //process result set
            while(rs.next()) {
                System.out.println();
                System.out.print("Name :" +rs.getString("Name") + " |");
                System.out.print("Continent :" +rs.getString("Continent") + " |");
                System.out.print("Region :" +rs.getString("Region") + " |");
                System.out.print("Population :" +rs.getString("Population") + " |");
                System.out.print("Capital :" +rs.getString("CapitalName") + " |");
            }
            System.out.println();
    }catch (SQLException e) {
        e.printStackTrace();}
    }

    /**
     * This method will display the top populated cities within (world,continent,region,country,district)
     */
    public void getTopCitiesN() {
        topCitiesMenu citiesMenu = getTopCitiesMenu();

        topCitiesQueryN(citiesMenu);
    }

    /**
     * This method will display top populated cities (world,continent,region,country,district) lrg to small no filters
     */
    public void getTopCitiesAll() {
        //initialise sql query
        String query = "";

        //Create Menu
        System.out.println("----GET TOP CITIES ALL-----");
        System.out.println("1 - World");
        System.out.println("2 - Continent");
        System.out.println("3 - Region");
        System.out.println("4 - Country");
        System.out.println("5 - District");
        System.out.println("-----MENU END-----");
        // Get user input
        Scanner userInput = new Scanner(System.in);
        int userSelect = getValidIntegerInput(userInput,1,5);

        //Act on user selection and create sql statement
        switch (userSelect) {
            // world
            case 1:
                query = "SELECT city.Name, country.Name AS CountryName,city.District,city.Population " +
                        "FROM city " +
                        "JOIN country ON country.Code=city.CountryCode " +
                        "ORDER BY city.Population DESC;";
                break;
                // continent
            case 2:
                //let user choose continent
                ArrayList<String> continentsList = getAllContinents();
                //Show options
                sqlMenu(continentsList);
                //Get input
                int userSelection = getValidIntegerInput(userInput,1,continentsList.size()-1);
                // Turn into string for sql
                String userSelectionString = continentsList.get(userSelection);
                // Create query
                query = "SELECT city.Name, country.Name AS CountryName,city.District,city.Population " +
                        "FROM city " +
                        "JOIN country ON country.Code=city.CountryCode " +
                        "WHERE country.Continent = \""+ userSelectionString + "\" " +
                        "ORDER BY city.Population DESC;";
                break;
                // region
            case 3:
                //Get regions list and display to user
                ArrayList<String> regionsList = getAllRegions();
                sqlMenu(regionsList);
                //Get user input
                int userSelectionCase3 = getValidIntegerInput(userInput,1,regionsList.size()-1);
                //Turn input into string for sql
                String userSelectionStringCase3 = regionsList.get(userSelectionCase3);

                query = "SELECT city.Name, country.Name AS CountryName,city.District,city.Population " +
                        "FROM city " +
                        "JOIN country ON country.Code=city.CountryCode " +
                        "WHERE country.Region = \""+ userSelectionStringCase3 + "\" " +
                        "ORDER BY city.Population DESC;";


                break;
                // country
            case 4:
                //Get country list and display to user
                ArrayList<String> countryList = getAllCountries();
                sqlMenu(countryList);
                //Get user input
                int userSelectionCase4 = getValidIntegerInput(userInput,1, countryList.size()-1);
                //Turn user input to string for sql
                String userSelectionStringCase4 = countryList.get(userSelectionCase4);

                query = "SELECT city.Name, country.Name AS CountryName,city.District,city.Population " +
                        "FROM city " +
                        "JOIN country ON country.Code=city.CountryCode " +
                        "WHERE country.Name = \""+ userSelectionStringCase4 + "\" " +
                        "ORDER BY city.Population DESC;";
                break;
                // district
            case 5:
                //Get country list and display to user
                ArrayList<String> countryListCase5 = getAllCountries();
                sqlMenu(countryListCase5);
                //Get user input
                int userSelectionCase4_1 = getValidIntegerInput(userInput,1,countryListCase5.size()-1);
                //Turn user input into string for further searches
                String userSelectionCase4_1String = countryListCase5.get(userSelectionCase4_1);
                //Get list of all districts in users country
                ArrayList<String> districtsList = getAllDistricts(userSelectionCase4_1String);
                //Display menu
                sqlMenu(districtsList);
                //Get user input for district
                int userSelectionCase4_2 = getValidIntegerInput(userInput,1,districtsList.size()-1);
                //Turn into string for sql
                String userSelectionCase4_2String = districtsList.get(userSelectionCase4_2);

                query = "SELECT city.Name, country.Name AS CountryName,city.District,city.Population " +
                        "FROM city " +
                        "JOIN country ON country.Code=city.CountryCode " +
                        "WHERE city.District = \""+ userSelectionCase4_2String + "\" " +
                        "ORDER BY city.Population DESC;";

                break;
        }

        // Run query
        topCitiesQuery(query);

    }
    private void topCitiesQuery(String query) {
        //Run Query
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             // Create statement from query
             Statement useStatement = connection.createStatement();
             PreparedStatement queryStatement = connection.prepareStatement(query)) {

            //use world db
            useStatement.execute("USE world;");


            // Execute the query
            ResultSet resultSet = queryStatement.executeQuery();

            // Display the results
            System.out.println("----------------- RESULTS -----------------");
            while (resultSet.next()) {
                System.out.println();
                System.out.print("Name:" + resultSet.getString("Name") + " | ");
                System.out.print("Country Name:" + resultSet.getString("CountryName") + " | ");
                System.out.print("District:" + resultSet.getString("District") + " | ");
                System.out.print("Population:" + resultSet.getString("Population") + " | ");
                System.out.println();
            }
            System.out.println("----------------- RESULTS END -----------------");

        } catch (SQLException e) {
            // Handle SQL exceptions
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void topCitiesQueryN(topCitiesMenu citiesMenu) {
        //Run Query
        try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
             // Create statement from query
             Statement useStatement = connection.createStatement();
             PreparedStatement queryStatement = connection.prepareStatement(citiesMenu.query())) {

            //use world db
            useStatement.execute("USE world;");

            //Set int
            queryStatement.setInt(1, citiesMenu.topAmount());

            // Execute the query
            ResultSet resultSet = queryStatement.executeQuery();

            // Display the results
            System.out.println("----------------- RESULTS -----------------");
            while (resultSet.next()) {
                System.out.println();
                System.out.print("Name:" + resultSet.getString("Name") + " | ");
                System.out.print("Country Name:" + resultSet.getString("CountryName") + " | ");
                System.out.print("District:" + resultSet.getString("District") + " | ");
                System.out.print("Population:" + resultSet.getString("Population") + " | ");
                System.out.println();
            }
            System.out.println("----------------- RESULTS END -----------------");

        } catch (SQLException e) {
            // Handle SQL exceptions
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private topCitiesMenu getTopCitiesMenu() {
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
                query = sqlTopWorldN();
                break;
            // Top cities Continent
            case 2:
                query = sqlTopCitiesN(userScanner);
                break;
            // top cities Region
            case 3:
                query = sqlTopRegionN(userScanner);
                break;
            // top cities Country
            case 4:
                query = sqlTopCountryN(userScanner);
                break;
            // top cities district
            case 5:
                query = sqlTopDistrictN(userScanner);
                break;
        }
        topCitiesMenu citiesMenu = new topCitiesMenu(query, topAmount);
        return citiesMenu;
    }

    private record topCitiesMenu(String query, int topAmount) {
    }

    /**
     * This class returns query which searches for top cities in world
     *
     * @return Query
     */
    private static String sqlTopWorldN() {
        return "SELECT city.Name, country.Name AS CountryName,city.District,city.Population " +
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
    private String sqlTopCitiesN(Scanner userScanner) {
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
        query = "SELECT city.Name, country.Name AS CountryName,city.District,city.Population " +
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
    private String sqlTopRegionN(Scanner userScanner) {
        String regionChosen;
        String query;
        // Display Options
        ArrayList<String> regionsList = getAllRegions();

        sqlMenu(regionsList);

        // Get input
        int userRegion = getValidIntegerInput(userScanner,1,regionsList.size()-1);
        regionChosen = regionsList.get(userRegion);

        //Query
        query = "SELECT city.Name, country.Name AS CountryName,city.District,city.Population " +
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
    private String sqlTopCountryN(Scanner userScanner) {
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
        query = "SELECT city.Name, country.Name AS CountryName,city.District,city.Population " +
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
    private String sqlTopDistrictN(Scanner userScanner) {
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
        query = "SELECT city.Name, country.Name AS CountryName,city.District,city.Population " +
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
