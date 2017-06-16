package logic;

import dao.DAOCustomer;
import dto.courses.Course;
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
import java.sql.ResultSet;
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
    private ComboBox<String>                        courses;
    @FXML
    private TextField                               changeZipcode;
    @FXML
    private TableView<Customer>                     customerTable;
    @FXML
    private TableView<Course>                       customerCourseTable;
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
    private ObservableList<Course>                  allCourses;
    private String                                  pathToMainWindow            =               "gui/Homepage.fxml";
    private DAOCustomer                             daoCustomer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillTable();
    }

    @Override
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
            customerTable.setItems(daoCustomer.getCustomerList());
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void fillChangeTable(Object o) {
        cleanAll(1);

        Customer customer = (Customer) o;

        customerID.setText(customer.getCustomerID() + "");
        changeFirstName.setText(customer.getCustomer_firstname());
        changeLastName.setText(customer.getCustomer_lastname());
        changeZipcode.setText(customer.getZipCode() + "");
        changeCity.setText(customer.getCity());
        changeStreet.setText(customer.getStreet());

        try {
            allCourses = daoCustomer.getCourseList(customer.getCustomerID());
            customerCourseTable.setItems(getAllCustomerCourse(customer));
            customerCourseList.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        courses.getItems().clear();

        for (Course oneCourse : allCourses) {
            courses.getItems().add(oneCourse.getCourse_name() + " ID:" + oneCourse.getId());
        }
    }

    @Override
    public boolean check(int tab) {
        return false;
    }

    // Buttons

    @Override
    public void addButtonPressed(ActionEvent actionEvent){
        try {
            if (courses.getValue() == null) {
                return;
            }
            String[] parts = courses.getValue().split("ID:");
            daoCustomer.addCourseToCustomer(Integer.parseInt(customerID.getText()), Integer.parseInt(parts[1]));
            fillChangeTable(customerTable.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void searchButton() {
        ResultSet rs;

        String search = customerTXT.getText();

        try {
            if (search.matches("\\b\\d+\\b")) {
                rs = daoCustomer.searchCustomer("ID", search);
            } else {
                rs = dbHelper.searchCustomer("name", search);
            }

            if (rs != null && rs.next()) {
                fillChangeTabble(fillCustomer(rs));
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kunde " + search + " nicht gefunden");
            alert.setHeaderText(null);
            alert.show();
        }
    }

    @Override
    public void changeButtonPressed(ActionEvent actionEvent, int tab) {

    }

    //

    @Override
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

    @Override
    public void mouseOnClick(MouseEvent mouseEvent, int tab) {
            if (mouseEvent.getClickCount() == 2) {
                fillChangeTable(customerTable.getSelectionModel().getSelectedItem());
        }
    }

    @FXML
    private void goToMainWindow(ActionEvent actionEvent) {
        goToScene(actionEvent, pathToMainWindow);
    }

}
