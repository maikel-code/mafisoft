package DBHelper;

import dao.DAOCourse;
import dao.DAOCustomer;
import dto.courses.PhysicalCourse;
import dto.courses.VideoCourse;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBHelper {
    private static DBHelper         instance        =       null;
    private Connection              connection;
    private DAOCourse               daoCourse       =       new DAOCourse();
    private DAOCustomer             daoCustomer     =       new DAOCustomer();
    private ResultSet allCourse;
    private ResultSet allVideoCourse;

    private DBHelper() {
    }

    public static synchronized DBHelper getInstance() {
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
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mafisoftBD?autoReconnect=true&useSSL=false", "root", "mafisoft");
            }
        } catch (SQLException sql) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Datenank existiert nicht oder wird nicht gefunden");
            alert.setHeaderText(null);
            alert.showAndWait();
        } catch (ClassNotFoundException cnfe) {
            Alert alert = new Alert(Alert.AlertType.ERROR, cnfe.getLocalizedMessage());
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        return connection;
    }

    // Course

    public void addPhysicalCourse(PhysicalCourse physicalCourse) throws SQLException, ClassNotFoundException {
        daoCourse.addCourse(physicalCourse);
    }

    public void addVideoCourse(VideoCourse videoCourse) throws SQLException, ClassNotFoundException {
        daoCourse.addVideoCourse(videoCourse);
    }


    public ResultSet getAllCourse() {
        return allCourse;
    }

    public ResultSet searchPhysicalCourse(String name, String search) {
        return null;
    }

    public ResultSet searchVideoCourse(String id, String search) {
        return null;
    }

    public void updatePhysicalCourse(PhysicalCourse physicalCourse) {
    }

    public void updateVideoCourse(VideoCourse videoCourse) {
    }

    public ResultSet getAllVideoCourse() {
        return allVideoCourse;
    }
}
