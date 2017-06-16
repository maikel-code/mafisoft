package logic;

import DBHelper.DBHelper;
import dao.DAOCourse;
import dto.courses.PhysicalCourse;
import dto.courses.VideoCourse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import logic.logicInterface.Navigable;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;


public class CourseList implements Initializable, Navigable {
    @SuppressWarnings("rawtypes")
    @FXML
    private TableView                               courseTable;
    @FXML
    private TableView<VideoCourse>                  vCourseTable;
    @FXML
    private TableColumn<PhysicalCourse, Integer>    id;
    @FXML
    private TableColumn<PhysicalCourse, String>     courseName;
    @FXML
    private TableColumn<PhysicalCourse, String>     trainerName;
    @FXML
    private TableColumn<PhysicalCourse, Time>       timeBegin;
    @FXML
    private TableColumn<PhysicalCourse, Time>       timeEnd;
    @FXML
    private TextField                               courseTXT,
                                                    coursename,
                                                    trainer,
                                                    courseID,
                                                    startTime,
                                                    endTime;

    @FXML
    private TableColumn<VideoCourse, Integer>       vID;
    @FXML
    private TableColumn<VideoCourse, String>        vCourseName;
    @FXML
    private TableColumn<VideoCourse, String>        vTrainerName;
    @FXML
    private TableColumn<VideoCourse, String>        vURL;
    @FXML
    private TableColumn<VideoCourse, String>        vRemark;
    @FXML
    private TextField                               vSearch,
                                                    vCourseIDChanged,
                                                    vCourseNameChanged,
                                                    vTrainerChanged,
                                                    vURLChanged;
    @FXML
    private TextArea                                vRemarkChanged;
    private int                                     tab                     =       1;
    private String                                  pathToMainWindow        =       "gui/Homepage.fxml";
    private SimpleDateFormat                        simpleDateFormat        =       new SimpleDateFormat("HH:mm");
    private DAOCourse                               course                  =       new DAOCourse();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillTable();
    }

    // Course list prepaid

    public void fillTable() {
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        courseName.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        trainerName.setCellValueFactory(new PropertyValueFactory<>("trainer_name"));
        timeBegin.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        timeEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        courseTable.setItems(course.getAllCourse());
    }

    private void fillChangeTable(PhysicalCourse course) {
        tab = 1;
        cleanAll();
        courseID.setText(course.getId() + "");
        coursename.setText(course.getCourse_name());
        trainer.setText(course.getTrainer_name());
        startTime.setText(simpleDateFormat.format(course.getStartTime()));
        endTime.setText(simpleDateFormat.format(course.getEndTime()));

    }

    public PhysicalCourse fillCourse(ResultSet rs) throws SQLException {
        PhysicalCourse course = new PhysicalCourse();

        course.setId(rs.getInt("id"));
        course.setCourse_name(rs.getString("course_name"));
        course.setTrainer_name(rs.getString("trainer_name"));
        course.setStartTime(rs.getTime("start"));
        course.setEndTime(rs.getTime("end"));

        return course;
    }

    public void searchButton() {
        course.searchCourse();
    }

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
            course.updateCourse(course.getCourse_id(), course.getCourse_name(), course.getTrainer_name(), course.getStartTime(), course.getEndTime());
            fillTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void mouseOnClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            course = (PhysicalCourse) courseTable.getSelectionModel().getSelectedItem();
            fillChangeTable(course);
        }
    }

    // Course and VideoCourse use same method

    @FXML
    private void goToMainWindow(ActionEvent actionEvent) {
        goToScene(actionEvent, pathToMainWindow);
    }

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
            vID.setCellValueFactory(new PropertyValueFactory<>("id"));
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
        ResultSet rs = DBHelper.DBHelper.DBHelper.getAllVideoCourse();
        ObservableList<VideoCourse> row = FXCollections.observableArrayList();

        while (rs.next()) {
            row.add(fillVideoCurse(rs));
        }

        return row;
    }

    private void fillVideoCourseChange(VideoCourse videoCourse) {
        tab = 2;
        cleanAll();

        vCourseIDChanged.setText(videoCourse.getId() + "");
        vCourseNameChanged.setText(videoCourse.getCourse_name());
        vTrainerChanged.setText(videoCourse.getTrainer_name());
        vURLChanged.setText(videoCourse.getvLink());
        vRemarkChanged.setText(videoCourse.getvRemark());
    }

    private VideoCourse fillVideoCurse(ResultSet rs) {
        VideoCourse videoCourse = new VideoCourse();

        try {
            videoCourse.setId(rs.getInt("videoID"));
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
                rs = course.searchVideoCourse("ID", search);
            } else {
                rs = course.searchVideoCourse("other", search);
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
            course.updateVideoCourse(videoCourse.getCourse_id(), videoCourse.getCourse_name(), videoCourse.getTrainer_name(), videoCourse.getvLink(), videoCourse.getvRemark());
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

}


/*

    GUI

        Interface

            Fachlogic

                DB

                    dto

                        dao

 */