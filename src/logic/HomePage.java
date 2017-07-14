package logic;

import config.R;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import logger.Log;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class HomePage implements Initializable, Navigable {
    private static final Logger LOGGER = Log.getLogger(HomePage.class);
    public String english, russian, german;
    private String path;
    @FXML
    private ComboBox<String> switchLanguage;
    @FXML
    private Button newCourse, allCourse, newCustomer, allCustomer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setResources(ResourceBundle.getBundle(R.Language.RESOURCE_BUNDLE, new Locale(R.Language.currentLanguage, R.Language.currentCountry)));
    }


    /*
        All fx:id's be associated with R.Pages.FX_ID_*
        and all path to other windows with R.Pages.PATH_TO_*
    */

    @FXML
    private void buttonPressed(ActionEvent actionEvent) {
        String button = ((Button) actionEvent.getSource()).getId();

        switch (button) {
            case R.Pages.FX_ID_ADD_COURSE:
                path = R.Pages.PATH_TO_ADD_COURSE_WINDOW;
                break;
            case R.Pages.FX_ID_ALL_COURSES:
                path = R.Pages.PATH_TO_CHANGE_COURSE_WINDOW;
                break;
            case R.Pages.FX_ID_ADD_CUSTOMER:
                path = R.Pages.PATH_TO_ADD_CUSTOMER_WINDOW;
                break;
            case R.Pages.FX_ID_ALL_CUSTOMERS:
                path = R.Pages.PATH_TO_CHANGE_CUSTOMER_WINDOW;
                break;
        }

        goToScene(actionEvent, path);
    }

    @FXML
    private void switchLanguage() {
        String lang = switchLanguage.getSelectionModel().getSelectedItem();

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

        LOGGER.info(String.format("Set %S language", R.Language.currentLanguage));

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
