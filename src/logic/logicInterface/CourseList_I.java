package logic.logicInterface;

import dto.courses.PhysicalCourse;
import dto.courses.VideoCourse;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public interface CourseList_I extends Changed{

    PhysicalCourse fillPhysicalCourse();
    ObservableList<PhysicalCourse> getPhysicalCourseList();
    void changePhysicalCourse();

    // Video
    VideoCourse fillVideoCurse(ResultSet rs);
    ObservableList<VideoCourse> getVideoCourseList();
    void changeVideoCourse();
}
