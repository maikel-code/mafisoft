package logic;

import config.R;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Logic for first view, where all views are linked to
 */
public class HomePage implements Initializable, Navigable {
    public String english, russian, german;
    private String path;
    @FXML
    private ComboBox<String> switchLanguage;
    @FXML
    private Button newCourse, allCourse, newCustomer, allCustomer;
    // Template variable
    private int selected = R.Language.LANGUAGE_DE;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setResources(ResourceBundle.getBundle(R.Language.RESOURCE_BUNDLE, new Locale(R.Language.currentLanguage, R.Language.currentCountry)));
    }


    /*
        All fx:id's be associated with R.Pages.FX_ID_*
        and all path to other windows with R.Pages.PATH_TO_*
    */

    /**
     * Loads the view which was selected in gui
     *
     * @param actionEvent Button of a view which should be loaded
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

    /**
     * set resource bundle with selected language from select box
     */
    @FXML
    private void switchLanguage() {
        int lang = switchLanguage.getSelectionModel().getSelectedIndex();
        if (lang != selected) {

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

            selected = lang;
            setResources(resourceBundle);
        }
    }

    /**
     * Set's all  labels with internationalalbe resourceBundles
     *
     * @param resourceBundle for properties
     */
    public void setResources(ResourceBundle resourceBundle) {
        try {
            newCourse.setText(new String(resourceBundle.getString("lbCreateCourse").getBytes("ISO-8859-1"), "UTF-8"));
            allCourse.setText(new String(resourceBundle.getString("lbAllCourse").getBytes("ISO-8859-1"), "UTF-8"));
            newCustomer.setText(new String(resourceBundle.getString("lbCreateCustomer").getBytes("ISO-8859-1"), "UTF-8"));
            allCustomer.setText(new String(resourceBundle.getString("lbChangeCustomerData").getBytes("ISO-8859-1"), "UTF-8"));
            switchLanguage.setPromptText(new String(resourceBundle.getString("lbSelectLanguage").getBytes("ISO-8859-1"), "UTF-8"));


            // Set Language text
            russian = new String(resourceBundle.getString("languageRU").getBytes("ISO-8859-1"), "UTF-8");
            english = new String(resourceBundle.getString("languageEN").getBytes("ISO-8859-1"), "UTF-8");
            german = new String(resourceBundle.getString("languageDE").getBytes("ISO-8859-1"), "UTF-8");

            ObservableList<String> languageList = switchLanguage.getItems();
            languageList.setAll(russian, english, german);
            switchLanguage.setItems(languageList);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
