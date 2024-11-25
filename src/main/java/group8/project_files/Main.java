package group8.project_files;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Create instances of classes
        Database db = new Database();
        Menu userMenu = new Menu();

        // Connect to DB and test
        db.initDB();

        // Create scanner for text
        Scanner scanner = new Scanner(System.in);
        // Start Menu
        userMenu.start(scanner);

    }
}