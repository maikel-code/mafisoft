package DBHelper;

import dao.DAOCourse;
import dao.DAOCourse_I;
import dao.DAOCustomer;
import dao.DAOCustomer_I;
import dto.courses.PhysicalCourse;
import dto.courses.VideoCourse;
import dto.customer.Customer;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper implements DAOCustomer_I, DAOCourse_I {
    private static DBHelper         instance;
    private Connection              connection;
    private DAOCourse_I               daoCourse_i       =       new DAOCourse();
    private DAOCustomer_I             daoCustomer_i     =       new DAOCustomer();

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
        } catch (ClassNotFoundException cnfe) {
            Alert alert = new Alert(Alert.AlertType.ERROR, cnfe.getLocalizedMessage());
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        return connection;
    }


    // Customer
    public int addCustomer(Customer dtoCustomer) throws SQLException, ClassNotFoundException  {
       return daoCustomer_i.addCustomer(dtoCustomer);
    }
    public void updateCustomer(Customer dtoCustomer) throws SQLException, ClassNotFoundException  {
        daoCustomer_i.updateCustomer(dtoCustomer);
    }
    public ObservableList<Customer> searchCustomer(String config, String searchText) throws SQLException, ClassNotFoundException  {
        return daoCustomer_i.searchCustomer(config, searchText);
    }
    public ObservableList<Customer> getAllCustomer() throws SQLException, ClassNotFoundException  {
        return daoCustomer_i.getAllCustomer();
    }


    // Course

    public int addPhysicalCourse(PhysicalCourse physicalCourse) throws SQLException, ClassNotFoundException {
        return daoCourse_i.addPhysicalCourse(physicalCourse);
    }

    public int addVideoCourse(VideoCourse videoCourse) throws SQLException, ClassNotFoundException {
        return daoCourse_i.addVideoCourse(videoCourse);
    }

    public ObservableList<PhysicalCourse> getAllCourse() throws SQLException, ClassNotFoundException {
        return daoCourse_i.getAllCourse();
    }

    public ObservableList<PhysicalCourse> searchPhysicalCourse(String searchConfig, String search) throws SQLException, ClassNotFoundException {
        return daoCourse_i.searchPhysicalCourse(searchConfig,search);
    }

    public ObservableList<PhysicalCourse> getAllCourseByCustomer(Customer dtoCustomer) throws SQLException, ClassNotFoundException {
        return daoCourse_i.getAllCourseByCustomer(dtoCustomer);
    }

    public ObservableList<PhysicalCourse> getAllAvailabileCourse(Customer dtoCustomer) throws SQLException, ClassNotFoundException {
        return daoCourse_i.getAllAvailabileCourse(dtoCustomer);
    }

    public ObservableList<VideoCourse> searchVideoCourse(String searchConfig, String search) throws SQLException, ClassNotFoundException {
        return daoCourse_i.searchVideoCourse(searchConfig, search);
    }

    public int addCourseToCustomer(Customer dtoCustomer, PhysicalCourse physicalCourse) throws SQLException, ClassNotFoundException {
        return daoCourse_i.addCourseToCustomer(dtoCustomer, physicalCourse);
    }

    public void removeCourse(Customer dtoCustomer, PhysicalCourse physicalCourse) throws SQLException, ClassNotFoundException {
        daoCourse_i.removeCourse(dtoCustomer, physicalCourse);
    }

    public void updatePhysicalCourse(PhysicalCourse physicalCourse) throws SQLException, ClassNotFoundException {
        daoCourse_i.updatePhysicalCourse(physicalCourse);
    }

    public void updateVideoCourse(VideoCourse videoCourse) throws SQLException, ClassNotFoundException {
        daoCourse_i.updateVideoCourse(videoCourse);
    }

    public ObservableList<VideoCourse> getAllVideoCourse() throws SQLException, ClassNotFoundException {
        return daoCourse_i.getAllVideoCourse();
    }

}
