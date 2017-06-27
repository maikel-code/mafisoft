package logic;

import config.R;
import dto.courses.PhysicalCourse;
import dto.courses.VideoCourse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import logger.Log;
import service.CourseService;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddCourse implements Initializable, AddCourse_I {

    @FXML
    private TextField vCourseName,
            vTrainer,
            vLink;
    @FXML
    private TextArea vRemark;
    @FXML
    private TextField courseName,
            trainer,
            startTime,
            endTime;
    private static CourseService courseService = new CourseService();
    private static final Logger LOGGER = Log.getLogger(AddCourse.class);

    public void initialize(URL location, ResourceBundle resources) {

    }

    public boolean check(int tab) {
        switch (tab) {
            case 1:
                return !(courseName.getText().isEmpty() &&
                        trainer.getText().isEmpty() &&
                        startTime.getText().isEmpty() &&
                        endTime.getText().isEmpty());
            case 2:
                return !(vCourseName.getText().isEmpty() &&
                        vTrainer.getText().isEmpty() &&
                        vLink.getText().isEmpty());
        }

        return false;
    }

    public void addButtonPressed(ActionEvent actionEvent) {
        String buttonName = ((Button) actionEvent.getSource()).getId();

        switch (buttonName) {
            case "addPhysicalCourse":
                if (check(1)) {
                    addPhysicalCOurse();
                }
                break;
            case "addVideoCourse":
                if (check(2)) {
                    addVideoCourse();
                }
                break;
        }
    }

    private void addPhysicalCOurse() {
        PhysicalCourse physicalCourse = new PhysicalCourse();
        physicalCourse.setCourseName(courseName.getText());
        physicalCourse.setTrainerName(trainer.getText());
        String[] startSplitted = startTime.getText().split("\\p{Punct}");
        String[] endSplitted = endTime.getText().split("\\p{Punct}");
        if (startSplitted.length <= 1 || endSplitted.length <= 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fehlerhafte startzeit angegeben");
            alert.setHeaderText(null);
            alert.show();
        } else {
            int timeHH = Integer.parseInt(startSplitted[0]);
            int timeMM = Integer.parseInt(startSplitted[1]);
            physicalCourse.setStartTime(new Time(timeHH, timeMM, 0));

            timeHH = Integer.parseInt(endSplitted[0]);
            timeMM = Integer.parseInt(endSplitted[1]);
            physicalCourse.setEndTime(new Time(timeHH, timeMM, 0));
        }
        try {
            Integer genID = courseService.addPhysicalCourse(physicalCourse);
            if (genID > 0) {
                courseName.setText("");
                trainer.setText("");
                startTime.setText("");
                endTime.setText("");
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Kurs wurde angelegt");
                alert.setHeaderText(null);
                alert.show();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }

    private void addVideoCourse() {
        VideoCourse videoCourse = new VideoCourse();
        videoCourse.setCourseName(vCourseName.getText());
        videoCourse.setTrainerName(vTrainer.getText());
        videoCourse.setLink(vLink.getText());
        videoCourse.setRemark(vRemark.getText());
        try {
            Integer genID = courseService.addVideoCourse(videoCourse);
            if (genID > 0) {
                vCourseName.setText("");
                vTrainer.setText("");
                vLink.setText("");
                vRemark.setText("");
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Video Kurs wurde angelegt");
                alert.setHeaderText(null);
                alert.show();
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }

    @FXML
    private void goToMainWindow(ActionEvent actionEvent) {
        goToScene(actionEvent, R.Pages.PATH_TO_MAIN_WINDOW);
    }

}
