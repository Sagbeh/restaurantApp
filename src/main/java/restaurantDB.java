import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Maia on 5/3/2017.
 */
public class restaurantDB {

    static ArrayList<String> saved = new ArrayList<String>();

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; //Configure the driver needed
    static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/restaurants";  //Connection string – where's the database?
    static final String USER = "maia";
    static final String PASSWORD = System.getenv("MYSQL_PW");

    public static void main(String[] args) {
        try { Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Can't instantiate driver class; check you have drivers and classpath configured correctly?");
            cnfe.printStackTrace();
            System.exit(-1);  //No driver? Need to fix before anything else will work. So quit the program
        }
    }

    //Method that is used once only on start up
    public static ArrayList<String> loadSavedRestaurants(){

        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {

            //Create a table in the database, if it does not exist already
            String createTableSQL = "CREATE TABLE IF NOT EXISTS mysavedrestaurants(Name varchar(30))";
            statement.executeUpdate(createTableSQL);
            System.out.println("Created My Saved Restaurants table");

            //TODO: Have saved restaurants saved to a file and then grab and load file here.

            statement.execute("INSERT INTO mysavedrestaurants VALUES ('Texas Roadhouse')"); //Test data - will be delete

            //Cycles through all the rows in the database and adds each row to an array list
            String fetchAllDataSQL = "SELECT * FROM mysavedrestaurants";
            ResultSet rs = statement.executeQuery(fetchAllDataSQL);
            while (rs.next()){
                String restaurantName = rs.getString("Name");
                System.out.println("Dog name = " + restaurantName);
                saved.add(restaurantName);
            }

            //close all connections
            rs.close();
            statement.close();
            conn.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }

        //return the array list
        return saved;
    }

    //Method that is run when the add button is selected
    //Note: this method only adds information to the database
    public static void addRestaurantToDB(String enterName){

        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {

            //Get input from form -- will change when API is added.
            String sName = enterName;

            //Add input to database
            String addDataSQL = "INSERT INTO mysavedrestaurants VALUE (?)";
            PreparedStatement addStatement = conn.prepareStatement(addDataSQL);
            addStatement.setString(1, sName);
            addStatement.executeUpdate();

            //confirm completion
            System.out.println("Successfully added row to database from method");

        } catch (SQLException se) {
            System.out.println(se);
        }
    }

    //Method that is run to update the JTable on any information that has been added to the database
    //Note: this method is only run after successful modification to database has been done.
    public static ArrayList<String>  fetchDB(){
        try (Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
             Statement statement = conn.createStatement()) {

            //Clears the array list or else duplicates.
            saved.clear();

            //Cycles through all the rows in the database and adds each row to an array list
            String fetchAllDataSQL = "SELECT * FROM mysavedrestaurants";
            ResultSet rs = statement.executeQuery(fetchAllDataSQL);
            while (rs.next()){
                String restaurantName = rs.getString("Name");
                System.out.println("Restaurant name = " + restaurantName);
                saved.add(restaurantName);
            }

            //close all connections
            rs.close();
            statement.close();
            conn.close();

        } catch (SQLException se) {
            se.printStackTrace();
        }

        //return the array list
        return saved;
    }
}
