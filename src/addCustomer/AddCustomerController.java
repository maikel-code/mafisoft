package addCustomer;

import DBHelper.DBController;
import customer_course.Customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import validateTextFieldFXML.zipCode.NumberTextField;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {
    @FXML
    private TextField firstNameTXT, lastNameTXT, mailTXT, phoneNumberTXT, streetTXT, city;
    @FXML
    private DatePicker birthdayTXT;
    @FXML
    private NumberTextField zipCode;
    @FXML
    private ComboBox period;
    @FXML
    private CheckBox now_checkBox;
    @FXML
    private DatePicker newDate;
    @FXML
    private Label orderDate, firstName_label, lastName_label, birthday_label, zipCode_label, city_label, street_label, time_label, startData_label;

    private java.sql.Date currentDate;
    private int defaultYear = 1900;
    private Customer customer;
    private java.sql.Date calendarEnd;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isChecked();
    }

    @FXML
    private void addButtonPressed(ActionEvent actionEvent) {
        setCustomer();
        try {
            if (check())
                DBController.addCustomer(customer.getCustomer_firstname(), customer.getCustomer_lastname(), customer.getBirthday(),
                        customer.getMail(), customer.getMobilephone(), customer.getZipCode(),
                        customer.getCity(), customer.getStreet(), currentDate, calendarEnd);

            goToMainWindow(actionEvent);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Fehler");
            alert.setHeaderText(null);
            alert.show();
        }
    }

    private void setCustomer() {
        if (check()) {
            Calendar calendarEndCalendar = Calendar.getInstance();
            calendarEndCalendar.set(Calendar.MONTH, (currentDate.getMonth() + Integer.parseInt(String.valueOf(period.getSelectionModel().getSelectedItem()))));
            calendarEnd = new java.sql.Date(calendarEndCalendar.getTime().getYear(), calendarEndCalendar.getTime().getMonth(), calendarEndCalendar.getTime().getDay());
            customer = new Customer();
            customer.setCustomer_firstname(firstNameTXT.getText());
            customer.setCustomer_lastname(lastNameTXT.getText());
            customer.setBirthday(getDate());
            customer.setMail(mailTXT.getText());
            customer.setMobilephone(phoneNumberTXT.getText());
            customer.setZipCode(Integer.parseInt(zipCode.getText()));
            customer.setCity(city.getText());
            customer.setStreet(streetTXT.getText());
        }
    }

    private boolean check() {
        boolean check = true;

        if (firstNameTXT.getText().isEmpty()) {
            firstName_label.setText("Muss ausgefüllt werden");
            check = false;
        } else if (lastNameTXT.getText().isEmpty()) {
            lastName_label.setText("Muss ausgefüllt werden");
            check = false;
        } else if (period.getSelectionModel().isEmpty()) {
            time_label.setText("Muss ausgefüllt werden");
            check = false;
        } else if (birthdayTXT.getEditor().getText().isEmpty()) {
            birthday_label.setText("Muss ausgefüllt werden");
            check = false;
        } else if (zipCode.getText().isEmpty()) {
            zipCode_label.setText("Muss ausgefüllt werden");
            check = false;
        } else if (city.getText().isEmpty()) {
            city_label.setText("Muss ausgefüllt werden");
            check = false;
        } else if (streetTXT.getText().isEmpty()) {
            street_label.setText("Muss ausgefüllt werden");
            check = false;
        } else if (!now_checkBox.isSelected() && newDate.getEditor().getText().isEmpty()) {
            startData_label.setText("Muss ausgefüllt werden");
            check = false;
        }

        return check;
    }

    @FXML
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

    @FXML
    private void isChecked() {
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

    @FXML
    public void goToMainWindow(ActionEvent actionEvent) {
        try {
            Parent home_page_parent = FXMLLoader.load(getClass().getResource("/MaFiSoftHomepage/Homepage.fxml"));
            Scene home_page_scene = new Scene(home_page_parent);
            Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            app_stage.hide();
            app_stage.setScene(home_page_scene);
            app_stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public java.sql.Date getDate() {
        return new java.sql.Date(birthdayTXT.getValue().getYear() - defaultYear, birthdayTXT.getValue().getMonth().getValue() - 1, birthdayTXT.getValue().getDayOfMonth());
    }

    public void checkZipLength(KeyEvent actionEvent) {
        if (zipCode.getText().length() >= 5) {
            zipCode.setText(zipCode.getText().substring(0, 5));
            zipCode.positionCaret(5);
        }
    }

}
