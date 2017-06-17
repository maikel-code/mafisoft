package DBHelper;

import dto.courses.PhysicalCourse;
import dto.courses.VideoCourse;
import dto.customer.Customer;
import javafx.collections.ObservableList;

import java.sql.SQLException;

/**
 * Created by maikel on 17.06.17.
 */
public interface DBHelper_I {

    ObservableList<PhysicalCourse> getAllCourseByCustomer(Customer dtoCustomer) throws SQLException, ClassNotFoundException;

    void addPhysicalCourse(PhysicalCourse dtoPhysicalCourse) throws SQLException, ClassNotFoundException;

    void updatePhysicalCourse(PhysicalCourse dtoPhysicalCourse) throws SQLException, ClassNotFoundException;

    ObservableList<PhysicalCourse> searchPhysicalCourse(String searchConfig, String search) throws SQLException, ClassNotFoundException;

    ObservableList<PhysicalCourse> getAllCourse() throws SQLException, ClassNotFoundException ;

    ObservableList<PhysicalCourse> getAllAvailabileCourse(Customer dtoCustomer) throws SQLException, ClassNotFoundException;

    void addCourseToCustomer(Customer dtoCustomer, PhysicalCourse dtoCourse) throws SQLException, ClassNotFoundException;

    void removeCourse(Customer dtoCustomer, PhysicalCourse dtoCourse) throws SQLException, ClassNotFoundException;

    void addVideoCourse(VideoCourse dtoVideoCourse) throws SQLException, ClassNotFoundException;

    void updateVideoCourse(VideoCourse dtoVideoCourse) throws SQLException, ClassNotFoundException;

    ObservableList<VideoCourse> searchVideoCourse(String searchConfig, String search) throws SQLException, ClassNotFoundException;

    ObservableList<VideoCourse> getAllVideoCourse() throws SQLException, ClassNotFoundException;

    // Customer

    void addCustomer(Customer customer) throws SQLException, ClassNotFoundException;
    void updateCustomer(Customer updateCustomer) throws SQLException, ClassNotFoundException;

    ObservableList<Customer> searchCustomer(String searchConfig, String search) throws SQLException, ClassNotFoundException;
    ObservableList<Customer> getAllCustomer() throws SQLException, ClassNotFoundException;

}
