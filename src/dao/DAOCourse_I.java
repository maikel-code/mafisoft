package dao;


import dto.courses.Course;
import dto.customer.Customer;
import javafx.collections.ObservableList;

/**
 * Created by maikel on 13.06.17.
 */
public interface DAOCourse_I {

    ObservableList<Course> getAllCourseByCustomer(Customer dtoCustomer);

    void addCourse(Course course);

    void updateCourse();

    Course searchCourse();

    ObservableList<Course> getAllCourse();

    ObservableList<Course> getAllAvailabileCourse();

    void addCourseToCustomer();

    void removeCourse();
}
