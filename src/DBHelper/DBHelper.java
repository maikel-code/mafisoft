package DBHelper;

import dao.DAOCourse;
import dao.DAOCustomer;
import dto.courses.PhysicalCourse;
import dto.courses.VideoCourse;
import dto.customer.Customer;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper implements DBHelper_I {
    private static DBHelper         instance;
    private Connection              connection;
    private DAOCourse               daoCourse       =       new DAOCourse();
    private DAOCustomer             daoCustomer     =       new DAOCustomer();

    private DBHelper() {
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
       return daoCustomer.addCustomer(dtoCustomer);
    }
    public void updateCustomer(Customer dtoCustomer) throws SQLException, ClassNotFoundException  {
        daoCustomer.updateCustomer(dtoCustomer);
    }
    public ObservableList<Customer> searchCustomer(String config, String searchText) throws SQLException, ClassNotFoundException  {
        return daoCustomer.searchCustomer(config, searchText);
    }
    public ObservableList<Customer> getAllCustomer() throws SQLException, ClassNotFoundException  {
        return daoCustomer.getAllCustomer();
    }


    // Course

    public int addPhysicalCourse(PhysicalCourse physicalCourse) throws SQLException, ClassNotFoundException {
        return daoCourse.addCourse(physicalCourse);
    }

    public int addVideoCourse(VideoCourse videoCourse) throws SQLException, ClassNotFoundException {
        return daoCourse.addVideoCourse(videoCourse);
    }

    public ObservableList<PhysicalCourse> getAllCourse() throws SQLException, ClassNotFoundException {
        return daoCourse.getAllCourse();
    }

    public ObservableList<PhysicalCourse> searchPhysicalCourse(String name, String search) throws SQLException, ClassNotFoundException {
        return daoCourse.searchCourse(name,search);
    }

    public ObservableList<PhysicalCourse> getAllCourseByCustomer(Customer dtoCustomer) throws SQLException, ClassNotFoundException {
        return daoCourse.getAllCourseByCustomer(dtoCustomer);
    }

    public ObservableList<PhysicalCourse> getAllAvailabileCourse(Customer dtoCustomer) throws SQLException, ClassNotFoundException {
        return daoCourse.getAllAvailabileCourse(dtoCustomer);
    }

    public ObservableList<VideoCourse> searchVideoCourse(String id, String search) throws SQLException, ClassNotFoundException {
        return daoCourse.searchVideoCourse(id, search);
    }

    public int addCourseToCustomer(Customer dtoCustomer, PhysicalCourse physicalCourse) throws SQLException, ClassNotFoundException {
        return daoCourse.addCourseToCustomer(dtoCustomer, physicalCourse);
    }

    public void removeCourse(Customer dtoCustomer, PhysicalCourse physicalCourse) throws SQLException, ClassNotFoundException {
        daoCourse.removeCourse(dtoCustomer, physicalCourse);
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
