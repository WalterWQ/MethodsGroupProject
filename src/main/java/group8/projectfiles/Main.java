package group8.projectfiles;

public class Main {
    public static void main(String[] args) {

        // Create instances of classes
        Database db = new Database();
        DatabaseExecutor executor = new DatabaseExecutor();
        //Run files to populate DB

        executor.runSqlFile("./db/country.sql");
        executor.runSqlFile("./db/city.sql");
        executor.runSqlFile("./db/countrylanguage.sql");

        // Prove that DB connected and show tables
        db.initDB();

    }
}