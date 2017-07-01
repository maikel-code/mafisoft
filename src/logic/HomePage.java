package logic;

import config.R;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class HomePage implements Initializable, Navigable {
    private String path;
    @FXML
    private ComboBox switchLanguage;
    @FXML
    private Button newCourse, allCourse, newCustomer, allCustomer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setResources(ResourceBundle.getBundle(R.Language.RESOURCE_BUNDLE, new Locale(R.Language.currentLanguage, R.Language.currentCountry)));
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

    @FXML
    private void switchLanguage() {
        String lang = (String) switchLanguage.getSelectionModel().getSelectedItem();

        switch (lang) {
            case R.Language.LANGUAGE_RU:
                R.Language.currentLanguage = "ru";
                R.Language.currentCountry = "RU";
                break;
            case R.Language.LANGUAGE_EN:
                R.Language.currentLanguage = "en";
                R.Language.currentCountry = "US";
                break;
            case R.Language.LANGUAGE_DE:
                R.Language.currentLanguage = "de";
                R.Language.currentCountry = "DE";
                break;
        }

        Locale locale = new Locale(R.Language.currentLanguage, R.Language.currentCountry);
        ResourceBundle resourceBundle = ResourceBundle.getBundle(R.Language.RESOURCE_BUNDLE, locale);

        setResources(resourceBundle);
    }

    public void setResources(ResourceBundle resourceBundle) {
        try {
            newCourse.setText(new String(resourceBundle.getString("lbCreateCourse").getBytes("ISO-8859-1"), "UTF-8"));
            allCourse.setText(new String(resourceBundle.getString("lbAllCourse").getBytes("ISO-8859-1"), "UTF-8"));
            newCustomer.setText(new String(resourceBundle.getString("lbCreateCustomer").getBytes("ISO-8859-1"), "UTF-8"));
            allCustomer.setText(new String(resourceBundle.getString("lbChangeCustomerData").getBytes("ISO-8859-1"), "UTF-8"));
            switchLanguage.setPromptText(new String(resourceBundle.getString("lbSelectLanguage").getBytes("ISO-8859-1"), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
