package dao;


import dto.courses.PhysicalCourse;
import dto.courses.VideoCourse;
import dto.customer.Customer;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface DAOCourse_I {

    ObservableList<PhysicalCourse> getAllCourseByCustomer(Customer dtoCustomer) throws SQLException, ClassNotFoundException;

    void addCourse(PhysicalCourse dtoPhysicalCourse) throws SQLException, ClassNotFoundException;

    void updateCourse(PhysicalCourse dtoPhysicalCourse) throws SQLException, ClassNotFoundException;

    ObservableList<PhysicalCourse> searchCourse(String searchConfig, String search) throws SQLException, ClassNotFoundException;

    ObservableList<PhysicalCourse> getAllCourse() throws SQLException, ClassNotFoundException ;

    ObservableList<PhysicalCourse> getAllAvailabileCourse(Customer dtoCustomer) throws SQLException, ClassNotFoundException;

    void addCourseToCustomer(Customer dtoCustomer, PhysicalCourse dtoCourse) throws SQLException, ClassNotFoundException;

    void removeCourse(Customer dtoCustomer, PhysicalCourse dtoCourse) throws SQLException, ClassNotFoundException;

    void addVideoCourse(VideoCourse dtoVideoCourse) throws SQLException, ClassNotFoundException;

    void updateVideoCourse(VideoCourse dtoVideoCourse) throws SQLException, ClassNotFoundException;

    ObservableList<VideoCourse> searchVideoCourse(String searchConfig, String search) throws SQLException, ClassNotFoundException;

    ObservableList<VideoCourse> getAllVideoCourse() throws SQLException, ClassNotFoundException;

}