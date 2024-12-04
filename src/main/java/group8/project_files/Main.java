package group8.project_files;

import java.util.Scanner;

public class Main {

    private static final String jdbcUrl = "jdbc:mysql://mysql:3306/"; // Use 'mysql' as the hostname
    private static final String username = "root";                      // Default username
    private static final String password = "rootpassword";

    public static void main(String[] args) {

        // Create instances of classes
        Database db = new Database();
        Menu userMenu = new Menu();


        // Connect to DB and test
        db.initDB(jdbcUrl, username, password);

        // Create scanner for text
        Scanner scanner = new Scanner(System.in);
        // Start Menu
        userMenu.start(scanner,db);

    }
}