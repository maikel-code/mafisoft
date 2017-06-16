package logic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import logic.logicInterface.Navigable;

import java.net.URL;
import java.util.ResourceBundle;

public class HomePage implements Initializable, Navigable {
    private String path;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void buttonPressed(ActionEvent actionEvent) {
        String button = ((Button) actionEvent.getSource()).getId();

        switch (button) {
            case "newCourse":
                path = "gui/AddCourse.fxml";
                break;
            case "allCourse":
                path = "gui/CourseList.fxml";
                break;
            case "newCustomer":
                path = "gui/AddCustomer.fxml";
                break;
            case "allCustomer":
                path = "gui/ChangeCustomerData.fxml";
                break;
        }

        goToScene(actionEvent, path);
    }

}
