package logic.logicInterface;

import dto.courses.Course;
import dto.courses.PhysicalCourse;
import dto.courses.VideoCourse;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface CourseList_I extends Navigable {

    // Physical

    void fillPhysicalTable();

    PhysicalCourse fillPhysicalCourse(ResultSet rs) throws SQLException;

    void changePhysicalCourse();


    // Used both

    void searchButton(ActionEvent actionEvent);

    void changeButtonPressed(ActionEvent actionEvent);

    void mouseOnClick(MouseEvent mouseEvent);

    void cleanAll(int tab);

    void fillChangedTable(Course course);


    // Video

    void fillVideoTable();

    VideoCourse fillVideoCurse(ResultSet rs);

    void changeVideoCourse();
}
