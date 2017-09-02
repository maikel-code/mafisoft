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
import logger.Log;
import service.CourseService;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Logic for lisiting physical and video courses, and editing form of selected physical or video courses
 */
public class CourseList implements Initializable, CourseList_I {
    @FXML
    private Button deleteSearchBtnPC, deleteSearchBtnVC, goToMainWindowBtnPC, goToMainWindowBtnVC, changeBtnPC, changeBtnVC;
    @FXML
    private Tab tabPhysical, tabVideo;
    @FXML
    private TableView<PhysicalCourse> courseTable;
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
    private static final Logger LOGGER = Log.getLogger(CourseList.class);

    public void initialize(URL location, ResourceBundle resources) {
        setResources(ResourceBundle.getBundle(R.Language.RESOURCE_BUNDLE, new Locale(R.Language.currentLanguage, R.Language.currentCountry)));
        fillPhysicalTable();
    }

    // Physical

    /**
     * Physical Course table ist filled with all Physical courses data from DB
     */
    public void fillPhysicalTable() {
        try {
            columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
            columnCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
            columnTrainerName.setCellValueFactory(new PropertyValueFactory<>("trainerName"));
            columnTimeBegin.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            columnTimeEnd.setCellValueFactory(new PropertyValueFactory<>("endTime"));
            courseTable.setItems(courseService.getAllCourse());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }

    /**
     * Creates new Physical Course object from filled fields and assigns this object to own object attribute
     */
    public void changePhysicalCourse() {
        try {
            int timeHH = Integer.parseInt(startTime.getText().split("\\p{Punct}")[0]);
            int timeMM = Integer.parseInt(startTime.getText().split("\\p{Punct}")[1]);
            int millis = 0xea60 * (timeHH * 60 + timeMM);
            int endTimeHH = Integer.parseInt(endTime.getText().split("\\p{Punct}")[0]);
            int endTimeMM = Integer.parseInt(endTime.getText().split("\\p{Punct}")[1]);
            int endMillis = 0xea60 * (endTimeHH * 60 + endTimeMM);

            physicalCourse.setCourseName(coursename.getText())
                    .setTrainerName(trainer.getText())
                    .setEndTime(new Time(endMillis))
                    .setStartTime(new Time(millis));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }

    // Used both

    /**
     * Executes search with provided text in search field, and filters list of either physical or video courses
     */
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


    /**
     * clear search input field
     */
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

    /**
     * check required field for notempty
     * then creates new course object from field data
     * then calls courseService service updatePhysicalCourse or updateVideoCourse function with Course object as param
     *
     * @param actionEvent click event of change customer data button
     */
    public void changeButtonPressed(ActionEvent actionEvent) {
        String buttonsID = ((Button) actionEvent.getSource()).getId();
        if (buttonsID.equals("physical")) {
            changePhysicalCourse();
            courseService.updatePhysicalCourse(physicalCourse);
        } else if (buttonsID.equals("video")) {
            changeVideoCourse();
            courseService.updateVideoCourse(videoCourse);
        }
    }

    /**
     * Listens for click in course table list and loads course data into editing form
     *
     * @param mouseEvent
     */
    public void mouseOnClick(MouseEvent mouseEvent) {
        String buttonID = ((TableView) mouseEvent.getSource()).getId();
        if (mouseEvent.getClickCount() == 1) {
            if (buttonID.equals("physical")) {
                physicalCourse = courseTable.getSelectionModel().getSelectedItem();
                fillChangedTable(physicalCourse);
            } else if (buttonID.equals("video")) {
                videoCourse = vCourseTable.getSelectionModel().getSelectedItem();
                fillChangedTable(videoCourse);
            }
        }
    }

    /**
     * clears input field data of editing form in both tabs
     */
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

    /**
     * Fills the right handed Formular with selected row with course data
     *
     * @param course Row data of a course equivalent with a course object
     */
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

    /**
     * Video Course table ist filled with all Video courses data from DB
     */
    public void fillVideoTable() {
        try {
            vID.setCellValueFactory(new PropertyValueFactory<>("id"));
            vCourseName.setCellValueFactory(new PropertyValueFactory<>("courseName"));
            vTrainerName.setCellValueFactory(new PropertyValueFactory<>("trainerName"));
            vURL.setCellValueFactory(new PropertyValueFactory<>("link"));
            vRemark.setCellValueFactory(new PropertyValueFactory<>("remark"));
            vCourseTable.setItems(courseService.getAllVideoCourse());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }

    /**
     * Creates new Video Course object from filled fields and assigns this object to own object attribute
     */
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

    /**
     * Set's all  labels with internationalalbe resourceBundles
     *
     * @param resourceBundle for properties
     */
    public void setResources(ResourceBundle resourceBundle) {
        try {
            // Columns
            // Physical
            columnId.setText(new String(resourceBundle.getString("lbId").getBytes("ISO-8859-1"), "UTF-8"));
            columnCourseName.setText(new String(resourceBundle.getString("lbCourseName").getBytes("ISO-8859-1"), "UTF-8"));
            columnTrainerName.setText(new String(resourceBundle.getString("lbTrainer").getBytes("ISO-8859-1"), "UTF-8"));
            columnTimeBegin.setText(new String(resourceBundle.getString("lbTimeBeginn").getBytes("ISO-8859-1"), "UTF-8"));
            columnTimeEnd.setText(new String(resourceBundle.getString("lbEndTime").getBytes("ISO-8859-1"), "UTF-8"));
            // Video
            vID.setText(new String(resourceBundle.getString("lbId").getBytes("ISO-8859-1"), "UTF-8"));
            vCourseName.setText(new String(resourceBundle.getString("lbCourseName").getBytes("ISO-8859-1"), "UTF-8"));
            vTrainerName.setText(new String(resourceBundle.getString("lbTrainer").getBytes("ISO-8859-1"), "UTF-8"));
            vURL.setText(new String(resourceBundle.getString("lbLink").getBytes("ISO-8859-1"), "UTF-8"));
            vRemark.setText(new String(resourceBundle.getString("lbRemark").getBytes("ISO-8859-1"), "UTF-8"));


            // Tabs
            tabPhysical.setText(new String(resourceBundle.getString("lbPhysucalCourse").getBytes("ISO-8859-1"), "UTF-8"));
            tabVideo.setText(new String(resourceBundle.getString("lbVideoCourse").getBytes("ISO-8859-1"), "UTF-8"));


            // Buttons
            // Physical
            changeBtnPC.setText(new String(resourceBundle.getString("lbChange").getBytes("ISO-8859-1"), "UTF-8"));
            deleteSearchBtnPC.setText(new String(resourceBundle.getString("lbChange").getBytes("ISO-8859-1"), "UTF-8"));
            goToMainWindowBtnPC.setText(new String(resourceBundle.getString("lbGoHomePage").getBytes("ISO-8859-1"), "UTF-8"));

            // Video
            changeBtnVC.setText(new String(resourceBundle.getString("lbChange").getBytes("ISO-8859-1"), "UTF-8"));
            deleteSearchBtnVC.setText(new String(resourceBundle.getString("lbChange").getBytes("ISO-8859-1"), "UTF-8"));
            goToMainWindowBtnVC.setText(new String(resourceBundle.getString("lbGoHomePage").getBytes("ISO-8859-1"), "UTF-8"));


            // Promp Text
            // Physical
            search.setPromptText(new String(resourceBundle.getString("lbPrompSearch").getBytes("ISO-8859-1"), "UTF-8"));
            courseID.setPromptText(new String(resourceBundle.getString("lbId").getBytes("ISO-8859-1"), "UTF-8"));
            coursename.setPromptText(new String(resourceBundle.getString("lbPromptCours").getBytes("ISO-8859-1"), "UTF-8"));
            trainer.setPromptText(new String(resourceBundle.getString("lbPromptLastName").getBytes("ISO-8859-1"), "UTF-8"));

            // Video
            vSearch.setPromptText(new String(resourceBundle.getString("lbPrompSearch").getBytes("ISO-8859-1"), "UTF-8"));
            vCourseIDChanged.setPromptText(new String(resourceBundle.getString("lbId").getBytes("ISO-8859-1"), "UTF-8"));
            vCourseNameChanged.setPromptText(new String(resourceBundle.getString("lbPromptCours").getBytes("ISO-8859-1"), "UTF-8"));
            vTrainerChanged.setPromptText(new String(resourceBundle.getString("lbPromptLastName").getBytes("ISO-8859-1"), "UTF-8"));
            vURLChanged.setPromptText(new String(resourceBundle.getString("lbPromptSite").getBytes("ISO-8859-1"), "UTF-8"));
            vRemarkChanged.setPromptText(new String(resourceBundle.getString("lbRemark").getBytes("ISO-8859-1"), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
