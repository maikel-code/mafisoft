package DBHelper;

import config.R;
import javafx.scene.control.Alert;
import logger.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBHelper {
    private static DBHelper instance;
    private Connection connection;
    private static final Logger LOGGER = Log.getLogger(DBHelper.class);

    private DBHelper() {
        // Exists only to defeat instantiation.
    }

    public synchronized static DBHelper getInstance() {
        if (instance == null) {
            instance = new DBHelper();
            LOGGER.info("Connecting to Database");
        }

        return instance;
    }

    public synchronized Connection getConnection() {
        try {
            Class.forName(R.DB.DB_DRIVER);
            if (connection != null) {
                return connection;
            } else {
                String dbPassword = "mafisoft";
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mafisoftBD?autoReconnect=true&useSSL=false", R.DB.DB_USER, dbPassword);
            }
        } catch (SQLException sql) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Datenank existiert nicht oder wird nicht gefunden");
            alert.setHeaderText(null);
            alert.showAndWait();
            LOGGER.log(Level.SEVERE, sql.getLocalizedMessage(), sql);
        } catch (ClassNotFoundException cnfe) {
            Alert alert = new Alert(Alert.AlertType.ERROR, cnfe.getLocalizedMessage());
            alert.setHeaderText(null);
            alert.showAndWait();
            LOGGER.log(Level.SEVERE, cnfe.getLocalizedMessage(), cnfe);
        }
        return connection;
    }

}
