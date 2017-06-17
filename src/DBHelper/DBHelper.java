package DBHelper;

import dao.DAOCourse;
import dao.DAOCustomer;
import dto.courses.PhysicalCourse;
import dto.courses.VideoCourse;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    private static DBHelper         instance        =       null;
    private Connection              connection;
    private DAOCourse               daoCourse       =       new DAOCourse();
    private DAOCustomer             daoCustomer     =       new DAOCustomer();

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

    public ObservableList<PhysicalCourse> getAllCourse() throws SQLException, ClassNotFoundException {
        return daoCourse.getAllCourse();
    }

    public ObservableList<PhysicalCourse> searchPhysicalCourse(String name, String search) throws SQLException, ClassNotFoundException {
        return daoCourse.searchCourse(name,search);
    }

    public ObservableList<VideoCourse> searchVideoCourse(String id, String search) throws SQLException, ClassNotFoundException {
        return daoCourse.searchVideoCourse(id, search);
    }

    public void updatePhysicalCourse(PhysicalCourse physicalCourse) throws SQLException, ClassNotFoundException {
        daoCourse.updateCourse(physicalCourse);
    }

    public void updateVideoCourse(VideoCourse videoCourse) throws SQLException, ClassNotFoundException {
        daoCourse.updateVideoCourse(videoCourse);
    }

    public ObservableList<VideoCourse> getAllVideoCourse() throws SQLException, ClassNotFoundException {
        return daoCourse.getAllVideoCourse();
    }

}
