package DBHelper;

import config.R;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBHelper {
    private static DBHelper instance;
    private Connection connection;
    private static final Logger LOGGER = R.LogConfig.getLogger(DBHelper.class);

    private DBHelper() {
        // Exists only to defeat instantiation.
    }

    public synchronized static DBHelper getInstance() {
        if (instance == null) {
            instance = new DBHelper();
        }

        return instance;
    }

    public synchronized Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            if (connection != null) {
                return connection;
            } else {
                String dbPassword = "mafisoft";
                String dbUser = "root";
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mafisoftBD?autoReconnect=true&useSSL=false", dbUser, dbPassword);
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
