package group8.project_files;

public class Main {
    public static void main(String[] args) {

        // Create instances of classes
        Database db = new Database();
        DatabaseExecutor executor = new DatabaseExecutor();
        Menu userMenu = new Menu();

        //Run files to populate DB
        //executor.populateDb();

        // Prove that DB connected and show tables
        db.initDB();

        //MENU IS SCUFFED FIX IT CARTER, ITS NOT SUPPOSED TO HAVE A MAIN111!!!111!!!
        //userMenu.start();

    }
}