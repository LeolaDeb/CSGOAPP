package fr.android.progmob_poject.jdbc;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import fr.android.progmob_poject.MainActivity;

/**
 * A singleton that returns an instance of the connection to the MySQL database.
 *
 * Uses mysql-connector jdbc driver
 *
 * @author Tristan Aymé-Martin
 *
 */
public class DatabaseConnection {
    //private static final String url = "jdbc:mysql://localhost/appli_hopital?autoReconnect=true";
    //private static final String user = "root";
    //private static final String password = "root";
    private static final String url = "jdbc:mysql://192.168.1.11/appli_csgo_tracker?autoReconnect=true&useSSL=false";
    private static final String user = "root";
    private static final String password = "root";
    //private static final String password = "root";
    private static Connection conn = null;
    /**
     * The private constructor that is called by method getInstance
     */
    DatabaseConnection() throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }

        // créer une connexion avec la BD
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            //Toast.makeText(MainActivity.context, "You are not connected to the MySQL Database", Toast.LENGTH_LONG).show();
            throw new Exception("You are not connected to the MySQL Database", e);

        }

    }

    /**
     * Create an instance of the connection to the database if not already initialized
     * and returns the instance of the database
     * @return an instance of the connection to the database
     */
    public static Connection getInstance() throws Exception {
        if (conn==null) {
            new DatabaseConnection();
            System.out.println("INSTANCIATION DE LA CONNEXION SQL ! ");
        }
        System.out.println("CONNEXION EXISTANTE ");
        return conn;

    }
}