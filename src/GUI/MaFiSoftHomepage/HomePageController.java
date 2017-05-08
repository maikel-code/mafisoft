package GUI.MaFiSoftHomepage;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void addNewCustomer(ActionEvent actionEvent) {
        try {
            Parent home_page_parent = FXMLLoader.load(getClass().getResource("/GUI/addCustomer/AddCustomer.fxml"));
            Scene home_page_scene = new Scene(home_page_parent);
            Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            app_stage.setResizable(false);
            app_stage.setScene(home_page_scene);
            app_stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeCustomerData(ActionEvent actionEvent) {
        try {
            Parent home_page_parent = FXMLLoader.load(getClass().getResource("/GUI/changeCustomerData/ChangeData.fxml"));
            Scene home_page_scene = new Scene(home_page_parent);
            Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            app_stage.setScene(home_page_scene);
            app_stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeCourseList(ActionEvent actionEvent) {
        try {
            Parent home_page_parent = FXMLLoader.load(getClass().getResource("/GUI/changeCourseList/CourseList.fxml"));
            Scene home_page_scene = new Scene(home_page_parent);
            Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            app_stage.setScene(home_page_scene);
            app_stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNewCourse(ActionEvent actionEvent) {
        try {
            Parent home_page_parent = FXMLLoader.load(getClass().getResource("/GUI/addCourse/AddCourse.fxml"));
            Scene home_page_scene = new Scene(home_page_parent);
            Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            app_stage.setResizable(false);
            app_stage.setScene(home_page_scene);
            app_stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

