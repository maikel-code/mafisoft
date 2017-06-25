package logic;

import config.R;
import dto.courses.Course;
import dto.courses.PhysicalCourse;
import dto.courses.VideoCourse;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import service.CourseService;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CourseList implements Initializable, CourseList_I {
    @FXML
    private TableView courseTable;
    @FXML
    private TableView<VideoCourse> vCourseTable;
    @FXML
    private TableColumn<PhysicalCourse, Integer> columnId;
    @FXML
    private TableColumn<PhysicalCourse, String> columnCourseName;
    @FXML
    private TableColumn<PhysicalCourse, String> columnTrainerName;
    @FXML
    private TableColumn<PhysicalCourse, Time> columnTimeBegin;
    @FXML
    private TableColumn<PhysicalCourse, Time> columnTimeEnd;
    @FXML
    private TextField search,
            coursename,
            trainer,
            courseID,
            startTime,
            endTime;

    @FXML
    private TableColumn<VideoCourse, Integer> vID;
    @FXML
    private TableColumn<VideoCourse, String> vCourseName;
    @FXML
    private TableColumn<VideoCourse, String> vTrainerName;
    @FXML
    private TableColumn<VideoCourse, String> vURL;
    @FXML
    private TableColumn<VideoCourse, String> vRemark;
    @FXML
    private TextField vSearch,
            vCourseIDChanged,
            vCourseNameChanged,
            vTrainerChanged,
            vURLChanged;
    @FXML
    private TextArea vRemarkChanged;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
    private PhysicalCourse physicalCourse = new PhysicalCourse();
    private VideoCourse videoCourse = new VideoCourse();
    private static CourseService courseService = new CourseService();
    private static final Logger LOGGER = R.LogConfig.getLogger(CourseList.class);

    public void initialize(URL location, ResourceBundle resources) {
        fillPhysicalTable();
    }

    // Physical

    public void fillPhysicalTable() {
        try {
            columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
            columnCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
            columnTrainerName.setCellValueFactory(new PropertyValueFactory<>("trainerName"));
            columnTimeBegin.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            columnTimeEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            courseTable.setItems(courseService.getAllCourse());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }

    public void changePhysicalCourse() {
        physicalCourse.setCourseName(coursename.getText());
        physicalCourse.setTrainerName(trainer.getText());
        int timeHH = Integer.parseInt(startTime.getText().split("\\p{Punct}")[0]);
        int timeMM = Integer.parseInt(startTime.getText().split("\\p{Punct}")[1]);
        physicalCourse.setStartTime(new Time(timeHH, timeMM, 0));
        timeHH = Integer.parseInt(endTime.getText().split("\\p{Punct}")[0]);
        timeMM = Integer.parseInt(endTime.getText().split("\\p{Punct}")[1]);
        physicalCourse.setEndTime(new Time(timeHH, timeMM, 0));
    }

    // Used both

    public void searchButton(KeyEvent actionEvent) {
        String buttonsID = ((TextField) actionEvent.getSource()).getId();
        String search = "";

        if (buttonsID.equals("physical")) {
            search = this.search.getText();
        } else if (buttonsID.equals("video")) {
            search = vSearch.getText();
        }

        try {
            String searchConfig = "name";

            if (search.matches("\\b\\d+\\b")) {
                searchConfig = "ID";
            }

            if (buttonsID.equals("physical")) {
                ObservableList<PhysicalCourse> filteredCourse = courseService.searchPhysicalCourse(searchConfig, search);
                courseTable.setItems(filteredCourse);
            } else if (buttonsID.equals("video")) {
                ObservableList<VideoCourse> filteredCourse = courseService.searchVideoCourse(searchConfig, search);
                vCourseTable.setItems(filteredCourse);
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kurs " + search + " nicht gefunden");
            alert.setHeaderText(null);
            alert.show();
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }

    }


    public void deleteSearchButton(ActionEvent actionEvent) {
        try {
            String buttonsID = ((Button) actionEvent.getSource()).getId();
            if (buttonsID.equals("physical")) {
                courseTable.setItems(courseService.getAllCourse());
                search.setText("");
            } else if (buttonsID.equals("video")) {
                vCourseTable.setItems(courseService.getAllVideoCourse());
                vSearch.setText("");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }


    public void changeButtonPressed(ActionEvent actionEvent) {
        String buttonsID = ((Button) actionEvent.getSource()).getId();
        try {
            if (buttonsID.equals("physical")) {
                changePhysicalCourse();
                courseService.updatePhysicalCourse(physicalCourse);
            } else if (buttonsID.equals("video")) {
                changeVideoCourse();
                courseService.updateVideoCourse(videoCourse);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }

    public void mouseOnClick(MouseEvent mouseEvent) {
        String buttonID = ((TableView) mouseEvent.getSource()).getId();
        if (mouseEvent.getClickCount() == 1) {
            if (buttonID.equals("physical")) {
                physicalCourse = (PhysicalCourse) courseTable.getSelectionModel().getSelectedItem();
                fillChangedTable(physicalCourse);
            } else if (buttonID.equals("video")) {
                videoCourse = vCourseTable.getSelectionModel().getSelectedItem();
                fillChangedTable(videoCourse);
            }
        }
    }

    public void cleanAll(int tab) {
        switch (tab) {
            case 1:
                coursename.clear();
                trainer.clear();
                startTime.clear();
                endTime.clear();
                break;
            case 2:
                vSearch.clear();
                vCourseIDChanged.clear();
                vCourseNameChanged.clear();
                vTrainerChanged.clear();
                vURLChanged.clear();
                break;
        }
    }

    public void fillChangedTable(Course course) {
        if (course instanceof PhysicalCourse) {
            cleanAll(1);
            courseID.setText(course.getId() + "");
            coursename.setText(course.getCourseName());
            trainer.setText(course.getTrainerName());
            startTime.setText(simpleDateFormat.format(((PhysicalCourse) course).getStartTime()));
            endTime.setText(simpleDateFormat.format(((PhysicalCourse) course).getEndTime()));
        } else if (course instanceof VideoCourse) {
            cleanAll(2);
            vCourseIDChanged.setText(videoCourse.getId() + "");
            vCourseNameChanged.setText(videoCourse.getCourseName());
            vTrainerChanged.setText(videoCourse.getTrainerName());
            vURLChanged.setText(videoCourse.getLink());
            vRemarkChanged.setText(videoCourse.getRemark());
        }
    }

    // Video

    public void fillVideoTable() {
        try {
            vID.setCellValueFactory(new PropertyValueFactory<>("id"));
            vCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
            vTrainerName.setCellValueFactory(new PropertyValueFactory<>("trainerName"));
            vURL.setCellValueFactory(new PropertyValueFactory<>("link"));
            vRemark.setCellValueFactory(new PropertyValueFactory<>("remark"));
            vCourseTable.setItems(courseService.getAllVideoCourse());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }


    public void changeVideoCourse() {
        videoCourse.setCourseName(vCourseNameChanged.getText());
        videoCourse.setTrainerName(vTrainerChanged.getText());
        videoCourse.setLink(vURLChanged.getText());
        videoCourse.setRemark(vRemarkChanged.getText());
    }

    @FXML
    private void goToMainWindow(ActionEvent actionEvent) {
        goToScene(actionEvent, R.Pages.PATH_TO_MAIN_WINDOW);
    }

}
