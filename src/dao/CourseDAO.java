package dao;


import dto.courses.PhysicalCourse;
import dto.courses.VideoCourse;
import dto.customer.Customer;
import javafx.collections.ObservableList;

public interface CourseDAO {

    ObservableList<PhysicalCourse> getAllCourseByCustomer(Customer dtoCustomer);

    int addPhysicalCourse(PhysicalCourse dtoPhysicalCourse);

    void updatePhysicalCourse(PhysicalCourse dtoPhysicalCourse);

    ObservableList<PhysicalCourse> searchPhysicalCourse(String searchConfig, String search);

    ObservableList<PhysicalCourse> getAllCourse();

    ObservableList<PhysicalCourse> getAllAvailabileCourse(Customer dtoCustomer);

    void addCourseToCustomer(Customer dtoCustomer, PhysicalCourse dtoCourse);

    void removeCourseByCustomer(Customer dtoCustomer, PhysicalCourse dtoCourse);

    int addVideoCourse(VideoCourse dtoVideoCourse);

    void updateVideoCourse(VideoCourse dtoVideoCourse);

    ObservableList<VideoCourse> searchVideoCourse(String searchConfig, String search);

    ObservableList<VideoCourse> getAllVideoCourse();

}