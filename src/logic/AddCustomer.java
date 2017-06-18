package logic;

import DBHelper.DBHelper;
import dto.customer.Customer;
import dto.zipCode.NumberTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class AddCustomer implements Initializable, AddCustomer_I {
    @FXML
    private TextField                   firstNameTXT,
                                        lastNameTXT,
                                        mailTXT,
                                        phoneNumberTXT,
                                        streetTXT,
                                        city;
    @FXML
    private DatePicker                  birthdayTXT;
    @FXML
    private NumberTextField             zipCode;
    @FXML
    private ComboBox<String>            period;
    @FXML
    private CheckBox                    now_checkBox;
    @FXML
    private DatePicker                  newDate;
    @FXML
    private Label                       orderDate;

    private java.sql.Date               currentDate;
    private int                         defaultYear             =           1900;
    private String                      pathToMainWindow        =           "gui/Homepage.fxml";
    private Customer                    customer;
    private static DBHelper                    dbHelper                =       DBHelper.getInstance();

    public void initialize(URL location, ResourceBundle resources) {
        isChecked();
    }

    @FXML
    public void addButtonPressed(ActionEvent actionEvent) {
        setCustomer();
        try {
            if (check(0)) {
               // dbHelper.testAddCusotmer(customer.getCustomer_firstname(), customer.getCustomer_lastname(), customer.getBirthday(),
                 //       customer.getMail(), customer.getMobilephone(), customer.getZipCode(),
                   //     customer.getCity(), customer.getStreet(), currentDate, calendarEnd);
                Integer genID = dbHelper.addCustomer(this.customer);
                if(genID > 0) {
                    firstNameTXT.setText("");
                    lastNameTXT.setText("");
                    birthdayTXT.setValue(null);
                    mailTXT.setText("");
                    phoneNumberTXT.setText("");
                    streetTXT.setText("");
                    city.setText("");
                    zipCode.setText("");
                    period.setValue(null);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Video Kurs wurde angelegt");
                    alert.setHeaderText(null);
                    alert.show();
                }
                //goToMainWindow(actionEvent);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fehler");
            alert.setHeaderText(null);
            alert.show();
        }
    }

    public void setCustomer() {
        if (check(0)) {
            Calendar calendarEndCalendar = Calendar.getInstance();
            calendarEndCalendar.set(Calendar.MONTH, (currentDate.getMonth() + Integer.parseInt(String.valueOf(period.getSelectionModel().getSelectedItem()))));
            java.sql.Date calendarEnd = new java.sql.Date(calendarEndCalendar.getTime().getYear(), calendarEndCalendar.getTime().getMonth(), calendarEndCalendar.getTime().getDay());
            customer = new Customer();
            customer.setCustomer_firstname(firstNameTXT.getText());
            customer.setCustomer_lastname(lastNameTXT.getText());
            customer.setBirthday(getDate());
            customer.setMail(mailTXT.getText());
            customer.setMobilephone(phoneNumberTXT.getText());
            customer.setZipCode(Integer.parseInt(zipCode.getText()));
            customer.setCity(city.getText());
            customer.setStreet(streetTXT.getText());
            customer.setEndDate(calendarEnd);
        }
    }

    public boolean check(int tab) {
        boolean check = false;
        String fillText = "";

        if (firstNameTXT.getText() == null || firstNameTXT.getText().isEmpty()) {
            check = true;
            fillText += "Vorname ";
        } else if (lastNameTXT.getText() == null || lastNameTXT.getText().isEmpty()) {
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
        } else if (streetTXT.getText() == null || streetTXT.getText().isEmpty()) {
            check = true;
            fillText += " ";
        } else if (now_checkBox.isSelected() && !newDate.getEditor().getText().isEmpty()) {
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
        goToScene(actionEvent, pathToMainWindow);
    }

    public void cleanAll() {
        firstNameTXT.clear();
        lastNameTXT.clear();
        birthdayTXT.getEditor().clear();
        mailTXT.clear();
        phoneNumberTXT.clear();
        zipCode.clear();
        city.clear();
        streetTXT.clear();
    }

    public void isChecked() {
        if (now_checkBox.isSelected()) {
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
