package logic;

import config.R;
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
import javafx.util.Callback;
import service.CourseService;
import service.CustomerService;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChangeCustomerData implements Initializable, ChangeCustomerData_I {
    @FXML
    private TextField changeFirstName,
            changeLastName,
            changeMail,
            changePhonenummer,
            changeCity,
            changeStreet,
            customerTXT,
            customerID;
    @FXML
    private ComboBox<PhysicalCourse> coursesCombobox;
    @FXML
    private TextField changeZipcode;
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableView<PhysicalCourse> customerCourseTable;
    @FXML
    private TableColumn<Customer, Integer> id;
    @FXML
    private TableColumn<Customer, String> firstName;
    @FXML
    private TableColumn<Customer, String> lastName;
    @FXML
    private TableColumn<Customer, Date> birthday;
    @FXML
    private TableColumn<Customer, String> mail;
    @FXML
    private TableColumn<Customer, String> phonenummer;
    @FXML
    private TableColumn<Customer, String> address;
    @FXML
    private TableColumn<Course, String> customerCourseList;
    private static CustomerService customerService = new CustomerService();
    private static CourseService courseService = new CourseService();
    private Customer editingCustomer = null;


    public void initialize(URL location, ResourceBundle resources) {
        fillTable();


        coursesCombobox.setCellFactory(new Callback<ListView<PhysicalCourse>, ListCell<PhysicalCourse>>() {
            @Override
            public ListCell<PhysicalCourse> call(ListView<PhysicalCourse> param) {

                return new ListCell<PhysicalCourse>() {
                    @Override
                    public void updateItem(PhysicalCourse oneCourse, boolean empty) {
                        super.updateItem(oneCourse, empty);
                        if (!empty) {
                            setText(oneCourse.getCourse_name() + " ID:" + oneCourse.getId());
                            setGraphic(null);
                        }
                    }
                };
            }
        });

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
            customerTable.setItems(customerService.getAllCustomer());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void fillEditingFormular(Object o) {
        cleanAll();

        Customer customer = (Customer) o;

        customerID.setText(customer.getCustomerID() + "");
        changeFirstName.setText(customer.getCustomer_firstname());
        changeLastName.setText(customer.getCustomer_lastname());
        changeZipcode.setText(customer.getZipCode() + "");
        changeCity.setText(customer.getCity());
        changeStreet.setText(customer.getStreet());

        ObservableList<PhysicalCourse> allCourses;
        try {
            allCourses = courseService.getAllAvailabileCourse(customer);
            customerCourseTable.setItems(courseService.getAllCourseByCustomer(customer));
            customerCourseList.setCellValueFactory(new PropertyValueFactory<>("course_name"));


            if (coursesCombobox != null) {
                coursesCombobox.getItems().clear();

                for (PhysicalCourse oneCourse : allCourses) {
                    coursesCombobox.getItems().add(oneCourse);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean check(int tab) {
        return false;
    }

    // Buttons

    public void addButtonPressed(ActionEvent actionEvent) {
        try {
            if (coursesCombobox.getValue() == null) {
                return;
            }
            //String[] parts = coursesCombobox.getValue().split("ID:");
            // TODO: Add provide Objects not strings
            courseService.addCourseToCustomer(editingCustomer, coursesCombobox.getValue());
            fillEditingFormular(customerTable.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void removeCourse() {
        if (customerCourseTable.getSelectionModel().getSelectedItem() != null) {
            PhysicalCourse course = customerCourseTable.getSelectionModel().getSelectedItem();
            try {
                courseService.removeCourse(editingCustomer, course);
                fillEditingFormular(customerTable.getSelectionModel().getSelectedItem());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void searchButton() {

        String search = customerTXT.getText();
        try {
            ObservableList<Customer> filteredCustomerList;

            if (search.matches("\\b\\d+\\b")) {
                filteredCustomerList = customerService.searchCustomer("ID", search);
            } else {
                filteredCustomerList = customerService.searchCustomer("name", search);
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

    public void deleteSearchButton() {
        try {
            customerTable.setItems(customerService.getAllCustomer());
            customerTXT.setText("");
        } catch (Exception e) {
            e.printStackTrace();
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

            customerService.updateCustomer(dtoCustomer);
            fillTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //


    public void cleanAll() {
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
        if (mouseEvent.getClickCount() == 1) {
            editingCustomer = customerTable.getSelectionModel().getSelectedItem();
            fillEditingFormular(editingCustomer);
        }
    }

    @FXML
    private void goToMainWindow(ActionEvent actionEvent) {
        goToScene(actionEvent, R.Pages.PATH_TO_MAIN_WINDOW);
    }

}
