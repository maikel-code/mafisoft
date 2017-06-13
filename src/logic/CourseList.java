package logic;

import DBHelper.DBController;
import DTO.courses.Course;
import DTO.courses.PhysicalCourse;
import DTO.courses.VideoCourse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import logicInterface.Controller;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;


public class CourseList implements Initializable, Controller {
    @SuppressWarnings("rawtypes")
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
    private TextField courseTXT, coursename, trainer, courseID, startTime, endTime;

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
    private TextField vSearch, vCourseIDChanged, vCourseNameChanged, vTrainerChanged, vURLChanged;
    @FXML
    private TextArea vRemarkChanged;
    private String pathToMainWindow = "gui/Homepage.fxml";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
    private PhysicalCourse course;
    private VideoCourse videoCourse;
    private int tab = 1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillTable();
    }

    // Course list prepeid

    @SuppressWarnings("unchecked")
    public void fillTable() {
        try {
            id.setCellValueFactory(new PropertyValueFactory<>("course_id"));
            courseName.setCellValueFactory(new PropertyValueFactory<>("course_name"));
            trainerName.setCellValueFactory(new PropertyValueFactory<>("trainer_name"));
            timeBegin.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            timeEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            courseTable.setItems(getCourseList());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ObservableList<Course> getCourseList() throws SQLException, ClassNotFoundException {
        ResultSet rs = DBController.getAllCourse();
        ObservableList<Course> row = FXCollections.observableArrayList();

        while (rs.next()) {
            row.add(fillCourse(rs));
        }

        return row;
    }

    private void fillChangeTabble(PhysicalCourse course) {
        tab = 1;
        cleanAll();
        courseID.setText(course.getCourse_id() + "");
        coursename.setText(course.getCourse_name());
        trainer.setText(course.getTrainer_name());
        startTime.setText(simpleDateFormat.format(course.getStartTime()));
        endTime.setText(simpleDateFormat.format(course.getEndTime()));

    }

    public PhysicalCourse fillCourse(ResultSet rs) throws SQLException {
        PhysicalCourse course = new PhysicalCourse();

        course.setCourse_id(rs.getInt("course_id"));
        course.setCourse_name(rs.getString("course_name"));
        course.setTrainer_name(rs.getString("trainer_name"));
        course.setStartTime(rs.getTime("start"));
        course.setEndTime(rs.getTime("end"));

        return course;
    }

    public void searchButton() {
        ResultSet rs;

        String search = courseTXT.getText();
        try {
            if (search.matches("\\b\\d+\\b")) {
                rs = DBController.searchCourse("ID", search);
            } else {
                rs = DBController.searchCourse("name", search);
            }

            if (rs != null && rs.next()) {
                fillChangeTabble(fillCourse(rs));
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kurs " + search + " nicht gefunden");
            alert.setHeaderText(null);
            alert.show();
        }
    }

    @SuppressWarnings("deprecation")
    private void changeCourse() {
        course.setCourse_name(coursename.getText());
        course.setTrainer_name(trainer.getText());
        int timeHH = Integer.parseInt(startTime.getText().split("\\p{Punct}")[0]);
        int timeMM = Integer.parseInt(startTime.getText().split("\\p{Punct}")[1]);
        course.setStartTime(new Time(timeHH, timeMM, 0));
        timeHH = Integer.parseInt(endTime.getText().split("\\p{Punct}")[0]);
        timeMM = Integer.parseInt(endTime.getText().split("\\p{Punct}")[1]);
        course.setEndTime(new Time(timeHH, timeMM, 0));
    }

    public void changeButtonPressed() {
        try {
            changeCourse();
            DBController.updateCourse(course.getCourse_id(), course.getCourse_name(), course.getTrainer_name(), course.getStartTime(), course.getEndTime());
            fillTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mouseOnClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            course = (PhysicalCourse) courseTable.getSelectionModel().getSelectedItem();
            fillChangeTabble(course);
        }
    }

    // Course and Videocourse udes same methoden

    @FXML
    private void goToMainWindow(ActionEvent actionEvent) {
        goToMainWindow(actionEvent, pathToMainWindow);
    }

    @Override
    public void cleanAll() {
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

    // Video course list prepaid

    public void fillVideoTable() {
        try {
            vID.setCellValueFactory(new PropertyValueFactory<>("course_id"));
            vCourseName.setCellValueFactory(new PropertyValueFactory<>("course_name"));
            vTrainerName.setCellValueFactory(new PropertyValueFactory<>("trainer_name"));
            vURL.setCellValueFactory(new PropertyValueFactory<>("vLink"));
            vRemark.setCellValueFactory(new PropertyValueFactory<>("vRemark"));
            vCourseTable.setItems(getVideoCourseList());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ObservableList<VideoCourse> getVideoCourseList() throws SQLException, ClassNotFoundException {
        ResultSet rs = DBController.getAllVideoCourse();
        ObservableList<VideoCourse> row = FXCollections.observableArrayList();

        while (rs.next()) {
            row.add(fillVideoCurse(rs));
        }

        return row;
    }

    private void fillVideoCourseChange(VideoCourse videoCourse) {
        tab = 2;
        cleanAll();

        vCourseIDChanged.setText(videoCourse.getCourse_id() + "");
        vCourseNameChanged.setText(videoCourse.getCourse_name());
        vTrainerChanged.setText(videoCourse.getTrainer_name());
        vURLChanged.setText(videoCourse.getvLink());
        vRemarkChanged.setText(videoCourse.getvRemark());
    }

    private VideoCourse fillVideoCurse(ResultSet rs) {
        VideoCourse videoCourse = new VideoCourse();

        try {
            videoCourse.setCourse_id(rs.getInt("videoID"));
            videoCourse.setCourse_name(rs.getString("courseName"));
            videoCourse.setTrainer_name(rs.getString("trainerName"));
            videoCourse.setvLink(rs.getString("link"));
            videoCourse.setvRemark(rs.getString("remark"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return videoCourse;
    }

    public void searchButtonVideo() {
        ResultSet rs;

        String search = vSearch.getText();
        try {
            if (search.matches("\\b\\d+\\b")) {
                rs = DBController.searchVideoCourse("ID", search);
            } else {
                rs = DBController.searchVideoCourse("other", search);
            }

            if (rs.next()) {
                fillVideoCourseChange(fillVideoCurse(rs));
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kurs " + search + " nicht gefunden");
            alert.setHeaderText(null);
            alert.show();
        }
    }

    private void changeVideoCourse() {
        videoCourse.setCourse_name(vCourseNameChanged.getText());
        videoCourse.setTrainer_name(vTrainerChanged.getText());
        videoCourse.setvLink(vURLChanged.getText());
        videoCourse.setvRemark(vRemarkChanged.getText());
    }

    public void videoChangeButtonPressed() {
        changeVideoCourse();
        try {
            DBController.updateVideoCourse(videoCourse.getCourse_id(), videoCourse.getCourse_name(), videoCourse.getTrainer_name(), videoCourse.getvLink(), videoCourse.getvRemark());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mouseOnClickVideoCourse(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            videoCourse = vCourseTable.getSelectionModel().getSelectedItem();
            fillVideoCourseChange(videoCourse);
        }
    }


    @Override
    public boolean check(int tab) {
        // TODO Auto-generated method stub
        return false;
    }

}
