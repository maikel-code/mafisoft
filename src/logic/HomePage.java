package logic;

import config.R;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

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
                path = R.Pages.PATH_TO_ADD_COURSE_WINDOW;
                break;
            case "allCourse":
                path = R.Pages.PATH_TO_CHANGE_COURSE_WINDOW;
                break;
            case "newCustomer":
                path = R.Pages.PATH_TO_ADD_CUSTOMER_WINDOW;
                break;
            case "allCustomer":
                path = R.Pages.PATH_TO_CHANGE_CUSTOMER_WINDOW;
                break;
        }

        goToScene(actionEvent, path);
    }

}
