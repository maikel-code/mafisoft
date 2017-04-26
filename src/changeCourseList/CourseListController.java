package changeCourseList;

import DBHelper.DBController;
import customer_course.Course;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

public class CourseListController implements Initializable {
    @FXML
    private TextField courseTXT, coursename, trainer, courseID, startTime, endTime;
    @FXML
    private TableView courseTable;
    @FXML
    private TableColumn id, courseName, trainerName, timeBegin, timeEnd, customerQuantitys;
    private final String format = "HH:mm";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillTale();
    }

    private void fillTale() {
        try {
            id.setCellValueFactory(new PropertyValueFactory<Course, Integer>("course_id"));
            courseName.setCellValueFactory(new PropertyValueFactory<Course, String>("course_name"));
            trainerName.setCellValueFactory(new PropertyValueFactory<Course, String>("trainer_name"));
            timeBegin.setCellValueFactory(new PropertyValueFactory<Course, Time>("startTime"));
            timeEnd.setCellValueFactory(new PropertyValueFactory<Course, Time>("endTime"));
            customerQuantitys.setCellValueFactory(new PropertyValueFactory<Course, Integer>("customerQuantity"));
            courseTable.setItems(getCustomerList());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ObservableList<Course> getCustomerList() throws SQLException, ClassNotFoundException {
        ResultSet rs = DBController.getAllCourse();
        ObservableList<Course> row = FXCollections.observableArrayList();

        while (rs.next()) {
            row.add(fillCourse(rs));
        }

        return row;
    }

    private void fillChangeTabble(Course course) {
        clearAll();
        courseID.setText(course.getCourse_id() + "");
        coursename.setText(course.getCourse_name());
        trainer.setText(course.getTrainer_name());
        startTime.setText(simpleDateFormat.format(course.getStartTime()));
        endTime.setText(simpleDateFormat.format(course.getEndTime()));

    }

    private void clearAll() {
        coursename.clear();
        trainer.clear();
        startTime.clear();
        endTime.clear();
    }

    public void mouseOnClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            fillChangeTabble((Course) courseTable.getSelectionModel().getSelectedItem());
        }
    }

    public void changeButton() {
        try {
//            DBController.updateCourse(Integer.parseInt(courseID.getText()), courseName.getText(), trainer.getText(), getTime(startTime.getText()), getTime(endTime.getText()));
//            Have to fix it XDD
            fillTale();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private Course fillCourse(ResultSet rs) throws SQLException {
        Course course = new Course();

        course.setCourse_id(rs.getInt("course_id"));
        course.setCourse_name(rs.getString("course_name"));
        course.setTrainer_name(rs.getString("trainer_name"));
        course.setStartTime(rs.getTime("start"));
        course.setEndTime(rs.getTime("end"));
        course.setCustomerQuantity(rs.getInt("customerQuantity"));

        return course;
    }

    public void goToMainWindow(ActionEvent actionEvent) {
        try {
            Parent home_page_parent = FXMLLoader.load(getClass().getResource("/MaFiSoftHomepage/Homepage.fxml"));
            Scene home_page_scene = new Scene(home_page_parent);
            Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            app_stage.hide();
            app_stage.setScene(home_page_scene);
            app_stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
