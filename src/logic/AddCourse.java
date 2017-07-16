package logic;

import config.R;
import dto.courses.PhysicalCourse;
import dto.courses.VideoCourse;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import logger.Log;
import service.CourseService;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Time;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Logic class for view of adding physical and video courses,
 */
public class AddCourse implements Initializable, AddCourse_I {

    @FXML
    private Button homeBtnPC, addBtnPC, homeBtnVC, addBtnVC;
    @FXML
    private Tab tabPhysical, tabVideo;
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
    @FXML
    private Label pcText, trainerTxt, stText, etText, videoLbl,
            trainerLbl, linkLbl, remarkLbl;
    private String alertAddCours, alerAddVideoCourse, alertTimeException;

    private static CourseService courseService = new CourseService();
    private static final Logger LOGGER = Log.getLogger(AddCourse.class);

    /**
     *
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {
        setResources(ResourceBundle.getBundle(R.Language.RESOURCE_BUNDLE, new Locale(R.Language.currentLanguage, R.Language.currentCountry)));
    }

    /**
     * Checks if required fields are not empty, for a certain tab
     *
     * @return true if required fields are not empty
     */
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

    /**
     * Checks which button of certain tab was pressed, and calls either addPhysicalCourse or addVideoCourse
     * In both function a Course Object is created with data from the filled fields and given to courseService Class
     *
     * @param actionEvent EventOject of clicked button
     */
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

    /**
     *
     */
    private void addPhysicalCOurse() {
        PhysicalCourse physicalCourse = new PhysicalCourse();
        physicalCourse.setCourseName(courseName.getText());
        physicalCourse.setTrainerName(trainer.getText());
        String[] startSplitted = startTime.getText().split("\\p{Punct}");
        String[] endSplitted = endTime.getText().split("\\p{Punct}");
        if (startSplitted.length <= 1 || endSplitted.length <= 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR, alertTimeException);
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
        Integer genID = courseService.addPhysicalCourse(physicalCourse);

        if (genID > 0) {
            courseName.setText("");
            trainer.setText("");
            startTime.setText("");
            endTime.setText("");
            Alert alert = new Alert(Alert.AlertType.INFORMATION, alertAddCours);
            alert.setHeaderText(null);
            alert.show();
            LOGGER.log(Level.INFO, alertAddCours);
        }
    }

    private void addVideoCourse() {
        VideoCourse videoCourse = new VideoCourse();
        videoCourse.setCourseName(vCourseName.getText());
        videoCourse.setTrainerName(vTrainer.getText());
        videoCourse.setLink(vLink.getText());
        videoCourse.setRemark(vRemark.getText());
        Integer genID = courseService.addVideoCourse(videoCourse);
        if (genID > 0) {
            vCourseName.setText("");
            vTrainer.setText("");
            vLink.setText("");
            vRemark.setText("");
            Alert alert = new Alert(Alert.AlertType.INFORMATION, alerAddVideoCourse);
            alert.setHeaderText(null);
            alert.show();
        }
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

            // Video
            videoLbl.setText(new String(resourceBundle.getString("lbVideoCourse").getBytes("ISO-8859-1"), "UTF-8"));
            trainerLbl.setText(new String(resourceBundle.getString("lbTrainer").getBytes("ISO-8859-1"), "UTF-8"));
            linkLbl.setText(new String(resourceBundle.getString("lbLink").getBytes("ISO-8859-1"), "UTF-8"));
            remarkLbl.setText(new String(resourceBundle.getString("lbRemark").getBytes("ISO-8859-1"), "UTF-8"));

            // Physical
            pcText.setText(new String(resourceBundle.getString("lbPhysucalCourse").getBytes("ISO-8859-1"), "UTF-8"));
            trainerTxt.setText(new String(resourceBundle.getString("lbTrainer").getBytes("ISO-8859-1"), "UTF-8"));
            stText.setText(new String(resourceBundle.getString("lbStartTime").getBytes("ISO-8859-1"), "UTF-8"));
            etText.setText(new String(resourceBundle.getString("lbEndTime").getBytes("ISO-8859-1"), "UTF-8"));

            // Buttons
            homeBtnPC.setText(new String(resourceBundle.getString("lbGoHomePage").getBytes("ISO-8859-1"), "UTF-8"));
            addBtnPC.setText(new String(resourceBundle.getString("lbAdd").getBytes("ISO-8859-1"), "UTF-8"));
            homeBtnVC.setText(new String(resourceBundle.getString("lbGoHomePage").getBytes("ISO-8859-1"), "UTF-8"));
            addBtnVC.setText(new String(resourceBundle.getString("lbAdd").getBytes("ISO-8859-1"), "UTF-8"));

            // Tabs
            tabPhysical.setText(new String(resourceBundle.getString("lbPhysucalCourse").getBytes("ISO-8859-1"), "UTF-8"));
            tabVideo.setText(new String(resourceBundle.getString("lbVideoCourse").getBytes("ISO-8859-1"), "UTF-8"));

            // Allerts
            alertAddCours = new String(resourceBundle.getString("alAddCourse").getBytes("ISO-8859-1"), "UTF-8");
            alerAddVideoCourse = new String(resourceBundle.getString("alAddVideoCourse").getBytes("ISO-8859-1"), "UTF-8");
            alertTimeException = new String(resourceBundle.getString("alTimeException").getBytes("ISO-8859-1"), "UTF-8");

            // Promp text Video
            vCourseName.setPromptText(new String(resourceBundle.getString("lbPromptCours").getBytes("ISO-8859-1"), "UTF-8"));
            vTrainer.setPromptText(new String(resourceBundle.getString("lbPromptLastName").getBytes("ISO-8859-1"), "UTF-8"));
            vLink.setPromptText(new String(resourceBundle.getString("lbPromptSite").getBytes("ISO-8859-1"), "UTF-8"));
            vRemark.setPromptText(new String(resourceBundle.getString("lbRemark").getBytes("ISO-8859-1"), "UTF-8"));

            // Promp text Physical
            courseName.setPromptText(new String(resourceBundle.getString("lbPromptCours").getBytes("ISO-8859-1"), "UTF-8"));
            trainer.setPromptText(new String(resourceBundle.getString("lbPromptLastName").getBytes("ISO-8859-1"), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
