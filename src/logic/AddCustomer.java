package logic;

import config.R;
import dto.customer.Customer;
import dto.zipCode.NumberTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import logger.Log;
import service.CustomerService;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddCustomer implements Initializable, AddCustomer_I {
    @FXML
    private Button addBtnLbl, clearBtnLbl, goToMainWIndow;
    @FXML
    private Label nameLbl,
            surnameLbl,
            bdLbl,
            mailLbl,
            mobLbl,
            periodLbl,
            zipLbl,
            cityLbl,
            streetLbl,
            releaseLbl,
            orderDateLbl;
    @FXML
    private TextField firstName,
            lastName,
            mail,
            mobile,
            street,
            city;
    @FXML
    private DatePicker birthday;
    @FXML
    private NumberTextField zipCode;
    @FXML
    private ComboBox<String> period;
    @FXML
    private CheckBox releaseDate;
    @FXML
    private DatePicker newDate;

    private java.sql.Date currentDate;
    private Customer customer;
    private int defaultYear = 1900;
    private static CustomerService customerService = new CustomerService();
    private static final Logger LOGGER = Log.getLogger(AddCustomer.class);

    public void initialize(URL location, ResourceBundle resources) {
        isChecked();
        setRessources(ResourceBundle.getBundle(R.Language.RESOURCE_BUNDLE, new Locale(R.Language.currentLanguage, R.Language.currentCountry)));
    }

    @FXML
    public void addButtonPressed(ActionEvent actionEvent) {
        setCustomer();
        try {
            if (check()) {
                Integer genID = customerService.addCustomer(this.customer);
                if (genID > 0) {
                    firstName.setText("");
                    lastName.setText("");
                    birthday.setValue(null);
                    mail.setText("");
                    mobile.setText("");
                    street.setText("");
                    city.setText("");
                    zipCode.setText("");
                    period.setValue(null);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Neue Kunde wurde angelegt");
                    alert.setHeaderText(null);
                    alert.show();
                }
                //goToMainWindow(actionEvent);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fehler");
            alert.setHeaderText(null);
            alert.show();
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }

    public void setCustomer() {
        if (check()) {
            Calendar calendarEndCalendar = Calendar.getInstance();
            calendarEndCalendar.set(Calendar.MONTH, (currentDate.getMonth() + Integer.parseInt(String.valueOf(period.getSelectionModel().getSelectedItem()))));
            java.sql.Date calendarEnd = new java.sql.Date(calendarEndCalendar.getTime().getYear(), calendarEndCalendar.getTime().getMonth(), calendarEndCalendar.getTime().getDay());
            customer = new Customer();
            customer.setFirstname(firstName.getText());
            customer.setLastname(lastName.getText());
            customer.setBirthday(getDate());
            customer.setMail(mail.getText());
            customer.setMobilephone(mobile.getText());
            customer.setZipCode(Integer.parseInt(zipCode.getText()));
            customer.setCity(city.getText());
            customer.setStreet(street.getText());
            customer.setEndDate(calendarEnd);
        }
    }

    public boolean check() {
        boolean check = false;
        String fillText = "";

        if (firstName.getText() == null || firstName.getText().isEmpty()) {
            check = true;
            fillText += "Vorname ";
        } else if (lastName.getText() == null || lastName.getText().isEmpty()) {
            check = true;
            fillText += " ";
        } else if (period.getSelectionModel() == null || period.getSelectionModel().isEmpty()) {
            check = true;
            fillText += " ";
        } else if (birthday.getEditor() == null || birthday.getEditor().getText().isEmpty()) {
            check = true;
            fillText += " ";
        } else if (zipCode.getText() == null || zipCode.getText().isEmpty()) {
            check = true;
            fillText += " ";
        } else if (city.getText() == null || city.getText().isEmpty()) {
            check = true;
            fillText += " ";
        } else if (street.getText() == null || street.getText().isEmpty()) {
            check = true;
            fillText += " ";
        } else if (releaseDate.isSelected() && !newDate.getEditor().getText().isEmpty()) {
            check = true;
        }

        if (check) {
            Alert alert = new Alert(Alert.AlertType.ERROR, fillText);
            alert.setHeaderText(null);
            alert.show();
        }

        return !check;
    }

    @FXML
    private void goToMainWindow(ActionEvent actionEvent) {
        goToScene(actionEvent, R.Pages.PATH_TO_MAIN_WINDOW);
    }

    public void cleanAll() {
        firstName.clear();
        lastName.clear();
        birthday.getEditor().clear();
        mail.clear();
        mobile.clear();
        zipCode.clear();
        city.clear();
        street.clear();
    }

    public void isChecked() {
        if (releaseDate.isSelected()) {
            orderDateLbl.setVisible(false);
            newDate.setVisible(false);
            currentDate = new java.sql.Date(new Date().getTime());
        } else {
            orderDateLbl.setVisible(true);
            newDate.setVisible(true);
            if (!newDate.getEditor().getText().isEmpty())
                currentDate = new java.sql.Date(newDate.getValue().getYear() - defaultYear, newDate.getValue().getMonth().getValue() - 1, newDate.getValue().getDayOfMonth());
        }
    }

    private java.sql.Date getDate() {
        return new java.sql.Date(birthday.getValue().getYear() - defaultYear, birthday.getValue().getMonth().getValue() - 1, birthday.getValue().getDayOfMonth());
    }

    public void checkZipLength(KeyEvent actionEvent) {
        if (zipCode.getText().length() >= 5) {
            zipCode.setText(zipCode.getText().substring(0, 5));
            zipCode.positionCaret(5);
        }
    }

    public void setRessources(ResourceBundle resourceBundle) {
        try {
            // Text
            nameLbl.setText(new String(resourceBundle.getString("lbFirstName").getBytes("ISO-8859-1"), "UTF-8"));
            surnameLbl.setText(new String(resourceBundle.getString("lbLastName").getBytes("ISO-8859-1"), "UTF-8"));
            bdLbl.setText(new String(resourceBundle.getString("lbBirthday").getBytes("ISO-8859-1"), "UTF-8"));
            mailLbl.setText(new String(resourceBundle.getString("lbEMail").getBytes("ISO-8859-1"), "UTF-8"));
            mobLbl.setText(new String(resourceBundle.getString("lbTelephone").getBytes("ISO-8859-1"), "UTF-8"));
            periodLbl.setText(new String(resourceBundle.getString("lbPeriod").getBytes("ISO-8859-1"), "UTF-8"));
            zipLbl.setText(new String(resourceBundle.getString("lbZIP").getBytes("ISO-8859-1"), "UTF-8"));
            cityLbl.setText(new String(resourceBundle.getString("lbCity").getBytes("ISO-8859-1"), "UTF-8"));
            streetLbl.setText(new String(resourceBundle.getString("lbStreet").getBytes("ISO-8859-1"), "UTF-8"));
            orderDateLbl.setText(new String(resourceBundle.getString("lbStartDate").getBytes("ISO-8859-1"), "UTF-8"));
            period.setPromptText(new String(resourceBundle.getString("lbMonth").getBytes("ISO-8859-1"), "UTF-8"));
            releaseDate.setText(new String(resourceBundle.getString("lbNow").getBytes("ISO-8859-1"), "UTF-8"));
            releaseLbl.setText(new String(resourceBundle.getString("lbConcractDate").getBytes("ISO-8859-1"), "UTF-8"));
            addBtnLbl.setText(new String(resourceBundle.getString("lbAdd").getBytes("ISO-8859-1"), "UTF-8"));
            clearBtnLbl.setText(new String(resourceBundle.getString("lbEmpty").getBytes("ISO-8859-1"), "UTF-8"));
            goToMainWIndow.setText(new String(resourceBundle.getString("lbGoHomePage").getBytes("ISO-8859-1"), "UTF-8"));

            // Promp text
            firstName.setPromptText(new String(resourceBundle.getString("lbPromptFirstName").getBytes("ISO-8859-1"), "UTF-8"));
            lastName.setPromptText(new String(resourceBundle.getString("lbPromptLastName").getBytes("ISO-8859-1"), "UTF-8"));
            mail.setPromptText(new String(resourceBundle.getString("lbPromptEmail").getBytes("ISO-8859-1"), "UTF-8"));
            mobile.setPromptText(new String(resourceBundle.getString("lbPromptMobile").getBytes("ISO-8859-1"), "UTF-8"));
            zipCode.setPromptText(new String(resourceBundle.getString("lbPromptZip").getBytes("ISO-8859-1"), "UTF-8"));
            city.setPromptText(new String(resourceBundle.getString("lbPromptCity").getBytes("ISO-8859-1"), "UTF-8"));
            street.setPromptText(new String(resourceBundle.getString("lbPromptStreet").getBytes("ISO-8859-1"), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
