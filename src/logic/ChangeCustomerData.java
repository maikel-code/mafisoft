package logic;

import DBHelper.DBHelper;
import dto.courses.Course;
import dto.courses.PhysicalCourse;
import dto.customer.Customer;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import logic.logicInterface.ChangeCustomerData_I;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChangeCustomerData implements Initializable, ChangeCustomerData_I {
    @FXML
    private TextField                               changeFirstName,
                                                    changeLastName,
                                                    changeMail,
                                                    changePhonenummer,
                                                    changeCity,
                                                    changeStreet,
                                                    customerTXT,
                                                    customerID;
    @FXML
    private ComboBox<Course>                        coursesCombobox;
    @FXML
    private TextField                               changeZipcode;
    @FXML
    private TableView<Customer>                     customerTable;
    @FXML
    private TableView<PhysicalCourse>                       customerCourseTable;
    @FXML
    private TableColumn<Customer, Integer>          id;
    @FXML
    private TableColumn<Customer, String>           firstName;
    @FXML
    private TableColumn<Customer, String>           lastName;
    @FXML
    private TableColumn<Customer, Date>             birthday;
    @FXML
    private TableColumn<Customer, String>           mail;
    @FXML
    private TableColumn<Customer, String>           phonenummer;
    @FXML
    private TableColumn<Customer, String>           address;
    @FXML
    private TableColumn<Course, String>             customerCourseList;
    private ObservableList<PhysicalCourse>                  allCourses;
    private String                                  pathToMainWindow            =               "gui/Homepage.fxml";
    private DBHelper                                dbHelper                =       DBHelper.getInstance();


    public void initialize(URL location, ResourceBundle resources) {
        fillTable();
    }


    public void fillTable() {
        id.setCellValueFactory(new PropertyValueFactory<>("customerID"));
        firstName.setCellValueFactory(new PropertyValueFactory<>("customer_firstname"));
        lastName.setCellValueFactory(new PropertyValueFactory<>("customer_lastname"));
        birthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        phonenummer.setCellValueFactory(new PropertyValueFactory<>("mobilephone"));
        address.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        address.setCellValueFactory(new PropertyValueFactory<>("city"));
        address.setCellValueFactory(new PropertyValueFactory<>("street"));
        try {
            customerTable.setItems(dbHelper.getAllCustomer());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void fillEditingFormular(Object o) {
        cleanAll(1);

        Customer customer = (Customer) o;

        customerID.setText(customer.getCustomerID() + "");
        changeFirstName.setText(customer.getCustomer_firstname());
        changeLastName.setText(customer.getCustomer_lastname());
        changeZipcode.setText(customer.getZipCode() + "");
        changeCity.setText(customer.getCity());
        changeStreet.setText(customer.getStreet());

        try {
            allCourses = dbHelper.getAllAvailabileCourse(customer);
            customerCourseTable.setItems(dbHelper.getAllCourseByCustomer(customer));
            customerCourseList.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        coursesCombobox.getItems().clear();

        for (Course oneCourse : allCourses) {
            coursesCombobox.getItems().add(oneCourse);
            //coursesCombobox.getItems().add(oneCourse.getCourse_name() + " ID:" + oneCourse.getId());
        }
    }

    public boolean check(int tab) {
        return false;
    }

    // Buttons

    public void addButtonPressed(ActionEvent actionEvent){
        try {
            if (coursesCombobox.getValue() == null) {
                return;
            }
          //  String[] parts = coursesCombobox.getValue().split("ID:");
            // TODO: Add provide Objects not strings
            //dbHelper.addCourseToCustomer(Integer.parseInt(customerID.getText()), Integer.parseInt(parts[1]));
            fillEditingFormular(customerTable.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void searchButton() {

        String search = customerTXT.getText();
        try {
            ObservableList<Customer> filteredCustomerList;

            if (search.matches("\\b\\d+\\b")) {
                filteredCustomerList = dbHelper.searchCustomer("ID", search);
            } else {
                filteredCustomerList = dbHelper.searchCustomer("name", search);
            }
            customerTable.setItems(filteredCustomerList);
            /*if (rs != null && rs.next()) {
                fillEditingFormular(fillCustomer(rs));
            }*/

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kunde " + search + " nicht gefunden");
            alert.setHeaderText(null);
            alert.show();
        }
    }

    public void changeButtonPressed(ActionEvent actionEvent) {
        try {
            Customer dtoCustomer = new Customer();
            dtoCustomer.setCustomerID(Integer.parseInt(customerID.getText()));
            dtoCustomer.setCustomer_firstname(changeFirstName.getText());
            dtoCustomer.setCustomer_lastname(changeLastName.getText());
            dtoCustomer.setMail(changeMail.getText());
            dtoCustomer.setMobilephone(changePhonenummer.getText());
            dtoCustomer.setZipCode(Integer.parseInt(changeZipcode.getText()));
            dtoCustomer.setCity(changeCity.getText());
            dtoCustomer.setStreet(changeCity.getText());

            dbHelper.updateCustomer(dtoCustomer);
            fillTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //


    public void cleanAll(int tab) {
        changeFirstName.clear();
        changeLastName.clear();
        changeMail.clear();
        changePhonenummer.clear();
        changeCity.clear();
        changeStreet.clear();
        customerTXT.clear();
        customerID.clear();
    }


    public void mouseOnClick(MouseEvent mouseEvent) {
            if (mouseEvent.getClickCount() == 2) {
                fillEditingFormular(customerTable.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void goToMainWindow(ActionEvent actionEvent) {
        goToScene(actionEvent, pathToMainWindow);
    }

}
