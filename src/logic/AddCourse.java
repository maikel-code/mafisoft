package logic;

import dao.DAOCourse;
import dto.courses.PhysicalCourse;
import dto.courses.VideoCourse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import logic.logicInterface.Appendable;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ResourceBundle;

public class AddCourse implements Initializable, Appendable {

    @FXML
    private TextField               vCourseName,
                                    vTrainerName,
                                    vLink;
    @FXML
    private TextArea                vRemark;
    @FXML
    private TextField               courseName,
                                    trainer,
                                    startTime,
                                    endTime;
    private String                  pathToMainWindow        =       "gui/Homepage.fxml";
    private DAOCourse               daoCourse                =       new DAOCourse();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    // Course and Videocourse udes same methoden
    @Override
    public boolean check(int tab) {
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

    @Override
    public void addButtonPressed(ActionEvent actionEvent) {
        if(check(1)) {
            PhysicalCourse physicalCourse = new PhysicalCourse();
            physicalCourse.setCourse_name(courseName.getText());
            physicalCourse.setTrainer_name(trainer.getText());
            int timeHH = Integer.parseInt(startTime.getText().split("\\p{Punct}")[0]);
            int timeMM = Integer.parseInt(startTime.getText().split("\\p{Punct}")[1]);
            physicalCourse.setStartTime(new Time(timeHH, timeMM, 0));
            timeHH = Integer.parseInt(endTime.getText().split("\\p{Punct}")[0]);
            timeMM = Integer.parseInt(endTime.getText().split("\\p{Punct}")[1]);
            physicalCourse.setEndTime(new Time(timeHH, timeMM, 0));
            try {
                daoCourse.addCourse(physicalCourse);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else if(check(2)){
            VideoCourse videoCourse = new VideoCourse();
            videoCourse.setCourse_name(vCourseName.getText());
            videoCourse.setTrainer_name(vTrainerName.getText());
            videoCourse.setvLink(vLink.getText());
            videoCourse.setvRemark(vRemark.getText());
            try {
                daoCourse.addVideoCourse(videoCourse);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void goToMainWindow(ActionEvent actionEvent) {
        goToScene(actionEvent, pathToMainWindow);
    }
}
