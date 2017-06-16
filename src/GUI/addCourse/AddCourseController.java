package GUI.addCourse;

import DBHelper.DBController;
import dto.courses.PhysicalCourse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ResourceBundle;

public class AddCourseController implements Initializable {

    @FXML
    private TextField vCourseName, vTrainerName, vLink;
    @FXML
    private TextArea vRemark;
    @FXML
    private TextField courseName, trainer, startTime, endTime;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    // Course
    public void addCourseButton(ActionEvent actionEvent) {
        PhysicalCourse course = new PhysicalCourse();
        course.setCourse_name(courseName.getText());
        course.setTrainer_name(trainer.getText());
        int timeHH = Integer.parseInt(startTime.getText().split("\\p{Punct}")[0]);
        int timeMM = Integer.parseInt(startTime.getText().split("\\p{Punct}")[1]);
        course.setStartTime(new Time(timeHH, timeMM, 0));
        timeHH = Integer.parseInt(endTime.getText().split("\\p{Punct}")[0]);
        timeMM = Integer.parseInt(endTime.getText().split("\\p{Punct}")[1]);
        course.setEndTime(new Time(timeHH, timeMM, 0));
        fillCourseInDB(course, actionEvent);
    }

    private void fillCourseInDB(PhysicalCourse course, ActionEvent actionEvent) {
        if (check(1)) {
            try {
                DBController.addCourse(course.getCourse_name(), course.getTrainer_name(), course.getStartTime(), course.getEndTime());
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Kunde wurde hingefügt", ButtonType.OK);
                alert.setHeaderText(null);
                alert.show();
                goToMainWindow(actionEvent);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    // Course and Videocourse udes same methoden
    private boolean check(int tab) {

        switch (tab) {
            case 1:
                return !(courseName.getText().isEmpty() &&
                        trainer.getText().isEmpty() &&
                        startTime.getText().isEmpty() &&
                        endTime.getText().isEmpty());
            case 2:
                return !(vCourseName.getText().isEmpty() &&
                        vTrainerName.getText().isEmpty() &&
                        vLink.getText().isEmpty());
        }

        return false;
    }

    public void goToMainWindow(ActionEvent actionEvent) {
        try {
            Parent home_page_parent = FXMLLoader.load(getClass().getResource("/GUI/MaFiSoftHomepage/Homepage.fxml"));
            Scene home_page_scene = new Scene(home_page_parent);
            Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            app_stage.hide();
            app_stage.setScene(home_page_scene);
            app_stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // VideoCourse
    public void addVideoButton(ActionEvent actionEvent) {
        if (check(2)) {
            try {
                DBController.addVideoCourse(vCourseName.getText(), vTrainerName.getText(), vLink.getText(), vRemark.getText());
                goToMainWindow(actionEvent);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

}
