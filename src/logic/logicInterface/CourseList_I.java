package logic.logicInterface;

import dto.courses.Course;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public interface CourseList_I extends Navigable {

    // Physical

    void fillPhysicalTable();

    void changePhysicalCourse();


    // Used both

    void searchButton(KeyEvent actionEvent);

    void changeButtonPressed(ActionEvent actionEvent);

    void mouseOnClick(MouseEvent mouseEvent);

    void cleanAll(int tab);

    void fillChangedTable(Course course);


    // Video

    void fillVideoTable();

    void changeVideoCourse();
}
