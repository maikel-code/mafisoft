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

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddCustomer implements Initializable, AddCustomer_I {
    @FXML
    private TextField firstName,
            lastName,
            mail,
            mobile,
            street,
            city;
    @FXML
    private DatePicker birthdayTXT;
    @FXML
    private NumberTextField zipCode;
    @FXML
    private ComboBox<String> period;
    @FXML
    private CheckBox releaseDate;
    @FXML
    private DatePicker newDate;
    @FXML
    private Label orderDate;

    private java.sql.Date currentDate;
    private Customer customer;
    private int defaultYear = 1900;
    private static CustomerService customerService = new CustomerService();
    private static final Logger LOGGER = Log.getLogger(AddCustomer.class);

    public void initialize(URL location, ResourceBundle resources) {
        isChecked();
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
                    birthdayTXT.setValue(null);
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
        } else if (birthdayTXT.getEditor() == null || birthdayTXT.getEditor().getText().isEmpty()) {
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
        birthdayTXT.getEditor().clear();
        mail.clear();
        mobile.clear();
        zipCode.clear();
        city.clear();
        street.clear();
    }

    public void isChecked() {
        if (releaseDate.isSelected()) {
            orderDate.setVisible(false);
            newDate.setVisible(false);
            currentDate = new java.sql.Date(new Date().getTime());
        } else {
            orderDate.setVisible(true);
            newDate.setVisible(true);
            if (!newDate.getEditor().getText().isEmpty())
                currentDate = new java.sql.Date(newDate.getValue().getYear() - defaultYear, newDate.getValue().getMonth().getValue() - 1, newDate.getValue().getDayOfMonth());
        }
    }

    private java.sql.Date getDate() {
        return new java.sql.Date(birthdayTXT.getValue().getYear() - defaultYear, birthdayTXT.getValue().getMonth().getValue() - 1, birthdayTXT.getValue().getDayOfMonth());
    }

    public void checkZipLength(KeyEvent actionEvent) {
        if (zipCode.getText().length() >= 5) {
            zipCode.setText(zipCode.getText().substring(0, 5));
            zipCode.positionCaret(5);
        }
    }

}
