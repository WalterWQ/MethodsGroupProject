package group8.project_files;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Create instances of classes
        Database db = new Database();
        Menu userMenu = new Menu();

        // Create scanner for text
        Scanner userScanner =new Scanner(System.in);

        // Connect to DB and test
        db.initDB();

        // Start Menu
        userMenu.start(userScanner);

    }
}