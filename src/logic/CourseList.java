package logic;

import DBHelper.DBHelper;
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

import java.net.URL;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;


public class CourseList implements Initializable, CourseList_I {
    @FXML
    private TableView courseTable;
    @FXML
    private TableView<VideoCourse> vCourseTable;
    @FXML
    private TableColumn<PhysicalCourse, Integer> id;
    @FXML
    private TableColumn<PhysicalCourse, String> courseName;
    @FXML
    private TableColumn<PhysicalCourse, String> trainerName;
    @FXML
    private TableColumn<PhysicalCourse, Time> timeBegin;
    @FXML
    private TableColumn<PhysicalCourse, Time> timeEnd;
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
    private String pathToMainWindow                     =       "gui/Homepage.fxml";
    private SimpleDateFormat simpleDateFormat           =       new SimpleDateFormat("HH:mm");
    private PhysicalCourse physicalCourse               =       new PhysicalCourse();
    private VideoCourse videoCourse                     =       new VideoCourse();
    private static DBHelper dbHelper                    =       DBHelper.getInstance();


    public void initialize(URL location, ResourceBundle resources) {
        fillPhysicalTable();
    }

    // Physical

    public void fillPhysicalTable() {
        try {
            id.setCellValueFactory(new PropertyValueFactory<>("id"));
            courseName.setCellValueFactory(new PropertyValueFactory<>("course_name"));
            trainerName.setCellValueFactory(new PropertyValueFactory<>("trainer_name"));
            timeBegin.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            timeEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            courseTable.setItems(dbHelper.getAllCourse());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void changePhysicalCourse() {
        physicalCourse.setCourse_name(coursename.getText());
        physicalCourse.setTrainer_name(trainer.getText());
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
        String search = null;

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
                ObservableList<PhysicalCourse> filteredCourse = dbHelper.searchPhysicalCourse(searchConfig, search);
                courseTable.setItems(filteredCourse);
            } else if (buttonsID.equals("video")) {
                ObservableList<VideoCourse> filteredCourse = dbHelper.searchVideoCourse(searchConfig, search);
                vCourseTable.setItems(filteredCourse);
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kurs " + search + " nicht gefunden");
            alert.setHeaderText(null);
            alert.show();
        }

    }


    public void deleteSearchButton(ActionEvent actionEvent) {
        try {
            String buttonsID = ((Button) actionEvent.getSource()).getId();
            if (buttonsID.equals("physical")) {
                courseTable.setItems(dbHelper.getAllCourse());
                search.setText("");
            } else if (buttonsID.equals("video")) {
                vCourseTable.setItems(dbHelper.getAllVideoCourse());
                vSearch.setText("");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void changeButtonPressed(ActionEvent actionEvent) {
        String buttonsID = ((Button) actionEvent.getSource()).getId();
        try {
            if (buttonsID.equals("physical")) {
                changePhysicalCourse();
                dbHelper.updatePhysicalCourse(physicalCourse);
            } else if (buttonsID.equals("video")) {
                changeVideoCourse();
                dbHelper.updateVideoCourse(videoCourse);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
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
            coursename.setText(course.getCourse_name());
            trainer.setText(course.getTrainer_name());
            startTime.setText(simpleDateFormat.format(((PhysicalCourse) course).getStartTime()));
            endTime.setText(simpleDateFormat.format(((PhysicalCourse) course).getEndTime()));
        } else if (course instanceof VideoCourse) {
            cleanAll(2);
            vCourseIDChanged.setText(videoCourse.getId() + "");
            vCourseNameChanged.setText(videoCourse.getCourse_name());
            vTrainerChanged.setText(videoCourse.getTrainer_name());
            vURLChanged.setText(videoCourse.getvLink());
            vRemarkChanged.setText(videoCourse.getvRemark());
        }
    }

    // Video

    public void fillVideoTable() {
        try {
            vID.setCellValueFactory(new PropertyValueFactory<>("id"));
            vCourseName.setCellValueFactory(new PropertyValueFactory<>("course_name"));
            vTrainerName.setCellValueFactory(new PropertyValueFactory<>("trainer_name"));
            vURL.setCellValueFactory(new PropertyValueFactory<>("vLink"));
            vRemark.setCellValueFactory(new PropertyValueFactory<>("vRemark"));
            vCourseTable.setItems(dbHelper.getAllVideoCourse());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void changeVideoCourse() {
        videoCourse.setCourse_name(vCourseNameChanged.getText());
        videoCourse.setTrainer_name(vTrainerChanged.getText());
        videoCourse.setvLink(vURLChanged.getText());
        videoCourse.setvRemark(vRemarkChanged.getText());
    }

    @FXML
    private void goToMainWindow(ActionEvent actionEvent) {
        goToScene(actionEvent, pathToMainWindow);
    }

}
