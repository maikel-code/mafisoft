package logic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import logic.logicInterface.MainWindow;

import java.net.URL;
import java.util.ResourceBundle;

public class HomePage implements Initializable, MainWindow {
    private String path;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void buttonPressed(ActionEvent actionEvent) {
        String button = ((Button) actionEvent.getSource()).getText();

        switch (button) {
            case "Kurse anlegen":
                path = "gui/AddCourse.fxml";
                break;
            case "Neue Kunde anlegen":
                path = "gui/AddCustomer.fxml";
                break;
            case "Alle Kurse anzeigen":
                path = "gui/CourseList.fxml";
                break;
            default:
                path = "gui/ChangeCustomerData.fxml";
                break;
        }

        buttonPressed(actionEvent, path);
    }

}
