package dao;


import dto.courses.PhysicalCourse;
import dto.courses.VideoCourse;
import dto.customer.Customer;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface CourseDAO {

    ObservableList<PhysicalCourse> getAllCourseByCustomer(Customer dtoCustomer) throws SQLException;

    int addPhysicalCourse(PhysicalCourse dtoPhysicalCourse) throws SQLException;

    void updatePhysicalCourse(PhysicalCourse dtoPhysicalCourse) throws SQLException;

    ObservableList<PhysicalCourse> searchPhysicalCourse(String searchConfig, String search) throws SQLException;

    ObservableList<PhysicalCourse> getAllCourse() throws SQLException ;

    ObservableList<PhysicalCourse> getAllAvailabileCourse(Customer dtoCustomer) throws SQLException;

    int addCourseToCustomer(Customer dtoCustomer, PhysicalCourse dtoCourse) throws SQLException;

    void removeCourse(Customer dtoCustomer, PhysicalCourse dtoCourse) throws SQLException;

    int addVideoCourse(VideoCourse dtoVideoCourse) throws SQLException;

    void updateVideoCourse(VideoCourse dtoVideoCourse) throws SQLException;

    ObservableList<VideoCourse> searchVideoCourse(String searchConfig, String search) throws SQLException;

    ObservableList<VideoCourse> getAllVideoCourse() throws SQLException;

}