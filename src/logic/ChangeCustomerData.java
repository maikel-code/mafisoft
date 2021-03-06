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
import logger.Log;
import service.CourseService;
import service.CustomerService;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Logic class for listing all customers and editing existing customer, as well as adding courses to customers.
 */
public class ChangeCustomerData implements Initializable, ChangeCustomerData_I {
    @FXML
    private Button deleteSearchBtn, goToMainWindowBtn, changeBtn, addBtn, removeBtn;
    @FXML
    private TextField firstName,
            lastName,
            mail,
            mobile,
            city,
            street,
            id,
            zipCode,
            searchCustomer;
    @FXML
    private ComboBox<PhysicalCourse> coursesCombobox;
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableView<PhysicalCourse> customerCourseTable;
    @FXML
    private TableColumn<Customer, Integer> columnId;
    @FXML
    private TableColumn<Customer, String> columnFirstName;
    @FXML
    private TableColumn<Customer, String> columnLastName;
    @FXML
    private TableColumn<Customer, Date> columnBirthday;
    @FXML
    private TableColumn<Customer, String> columnMail;
    @FXML
    private TableColumn<Customer, String> columnMobile;
    @FXML
    private TableColumn<Customer, String> columnAddress;
    @FXML
    private TableColumn<Course, String> customerCourseList;
    private static CustomerService customerService = new CustomerService();
    private static CourseService courseService = new CourseService();
    private Customer editingCustomer = null;
    private static final Logger LOGGER = Log.getLogger(ChangeCustomerData.class);

    public void initialize(URL location, ResourceBundle resources) {
        setResources(ResourceBundle.getBundle(R.Language.RESOURCE_BUNDLE, new Locale(R.Language.currentLanguage, R.Language.currentCountry)));
        fillTable();


        coursesCombobox.setCellFactory(new Callback<ListView<PhysicalCourse>, ListCell<PhysicalCourse>>() {
            @Override
            public ListCell<PhysicalCourse> call(ListView<PhysicalCourse> param) {

                return new ListCell<PhysicalCourse>() {
                    @Override
                    public void updateItem(PhysicalCourse oneCourse, boolean empty) {
                        super.updateItem(oneCourse, empty);
                        if (!empty) {
                            setText(oneCourse.getCourseName() + " ID:" + oneCourse.getId());
                            setGraphic(null);
                        }
                    }
                };
            }
        });

    }

    /**
     * Customer table ist filled with all customer data from DB
     */
    public void fillTable() {
        columnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        columnFirstName.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        columnLastName.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        columnBirthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        columnMail.setCellValueFactory(new PropertyValueFactory<>("mail"));
        columnMobile.setCellValueFactory(new PropertyValueFactory<>("mobilephone"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("city"));
        columnAddress.setCellValueFactory(new PropertyValueFactory<>("street"));
        try {
            customerTable.setItems(customerService.getAllCustomer());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }

    /**
     * Fills the right handed Formular with selected row with customer data and connected courses to customer
     *
     * @param o Row data of a customer equivalent with a customer object
     */
    public void fillEditingFormular(Object o) {
        cleanAll();

        Customer customer = (Customer) o;

        id.setText(customer.getId() + "");
        firstName.setText(customer.getFirstname());
        lastName.setText(customer.getLastname());
        zipCode.setText(customer.getZipCode() + "");
        city.setText(customer.getCity());
        street.setText(customer.getStreet());
        mobile.setText(customer.getMobilephone());
        mail.setText(customer.getMail());

        ObservableList<PhysicalCourse> allCourses;
        try {
            allCourses = courseService.getAllAvailabileCourse(customer);
            customerCourseTable.setItems(courseService.getAllCourseByCustomer(customer));
            customerCourseList.setCellValueFactory(new PropertyValueFactory<>("courseName"));


            if (coursesCombobox != null) {
                coursesCombobox.getItems().clear();

                for (PhysicalCourse oneCourse : allCourses) {
                    coursesCombobox.getItems().add(oneCourse);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }

    /**
     * checks for not empty editigns input fields
     *
     * @return
     */
    public boolean check() {
        boolean check = true;

        if (firstName.getText() == null || firstName.getText().isEmpty()) {
            check = false;
        }

        if (lastName.getText() == null || lastName.getText().isEmpty()) {
            check = false;
        }

        if (city.getText() == null || city.getText().isEmpty()) {
            check = false;
        }

        if (street.getText() == null || street.getText().isEmpty()) {
            check = false;
        }

        if (zipCode.getText() == null || zipCode.getText().isEmpty()) {
            check = false;
        }

        return check;
    }

    // Buttons

    /**
     * Links selected course to customer.
     *
     * @param actionEvent EventOject of clicked Add course button
     */
    public void addButtonPressed(ActionEvent actionEvent) {
        try {
            if (coursesCombobox.getValue() == null) {
                return;
            }
            courseService.addCourseToCustomer(editingCustomer, coursesCombobox.getValue());
            fillEditingFormular(customerTable.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }

    /**
     * Removes linked course from customer
     */
    @FXML
    private void removeCourse() {
        if (customerCourseTable.getSelectionModel().getSelectedItem() != null) {
            try {
                PhysicalCourse course = customerCourseTable.getSelectionModel().getSelectedItem();
                courseService.removeCourseByCustomer(editingCustomer, course);
                fillEditingFormular(customerTable.getSelectionModel().getSelectedItem());
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
            }
        }
    }

    /**
     * Executes search with provided text in search field, and filters list of customer
     */
    public void searchButton() {

        String search = searchCustomer.getText();
        try {
            ObservableList<Customer> filteredCustomerList;

            if (search.matches("\\b\\d+\\b")) {
                filteredCustomerList = customerService.searchCustomer("ID", search);
            } else {
                filteredCustomerList = customerService.searchCustomer("name", search);
            }
            customerTable.setItems(filteredCustomerList);

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Kunde " + search + " nicht gefunden");
            alert.setHeaderText(null);
            alert.show();
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }

    /**
     * clear search input field
     */
    public void deleteSearchButton() {
        try {
            customerTable.setItems(customerService.getAllCustomer());
            searchCustomer.setText("");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }

    /**
     * check required field for notempty
     * then creates new customer object from field data and calls customer service updateCustomer function with customer object as param
     *
     * @param actionEvent click event of change customer data button
     */
    public void changeButtonPressed(ActionEvent actionEvent) {
        try {
            if (check()) {
                Customer dtoCustomer = new Customer();
                dtoCustomer.setId(Integer.parseInt(id.getText()));
                dtoCustomer.setFirstname(firstName.getText());
                dtoCustomer.setLastname(lastName.getText());

                if (mail.getText() != null || mail.getText().isEmpty()) {
                    dtoCustomer.setMail(mail.getText());
                }

                if (mobile.getText() != null || !mobile.getText().isEmpty()) {
                    dtoCustomer.setMobilephone(mobile.getText());
                }


                dtoCustomer.setZipCode(Integer.parseInt(zipCode.getText()));
                dtoCustomer.setCity(city.getText());
                dtoCustomer.setStreet(city.getText());

                customerService.updateCustomer(dtoCustomer);
                LOGGER.info("Change data by customer id:" + dtoCustomer.getId());
                fillTable();
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }

    /**
     * clears input field data of editing form
     */
    public void cleanAll() {
        firstName.clear();
        lastName.clear();
        mail.clear();
        mobile.clear();
        city.clear();
        street.clear();
        searchCustomer.clear();
        id.clear();
    }

    /**
     * Listens for click in customer list and loads customer data into editing form
     *
     * @param mouseEvent
     */
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

    /**
     * Set's all  labels with internationalalbe resourceBundles
     *
     * @param resourceBundle for properties
     */
    public void setResources(ResourceBundle resourceBundle) {
        try {
            // Columns
            columnId.setText(new String(resourceBundle.getString("lbId").getBytes("ISO-8859-1"), "UTF-8"));
            columnFirstName.setText(new String(resourceBundle.getString("lbFirstName").getBytes("ISO-8859-1"), "UTF-8"));
            columnLastName.setText(new String(resourceBundle.getString("lbLastName").getBytes("ISO-8859-1"), "UTF-8"));
            columnBirthday.setText(new String(resourceBundle.getString("lbBirthday").getBytes("ISO-8859-1"), "UTF-8"));
            columnMail.setText(new String(resourceBundle.getString("lbEMail").getBytes("ISO-8859-1"), "UTF-8"));
            columnMobile.setText(new String(resourceBundle.getString("lbTelephone").getBytes("ISO-8859-1"), "UTF-8"));
            columnAddress.setText(new String(resourceBundle.getString("lbAdress").getBytes("ISO-8859-1"), "UTF-8"));

            // Text
            customerCourseList.setText(new String(resourceBundle.getString("lbCourses").getBytes("ISO-8859-1"), "UTF-8"));

            // Promp text
            id.setPromptText(new String(resourceBundle.getString("lbId").getBytes("ISO-8859-1"), "UTF-8"));
            firstName.setPromptText(new String(resourceBundle.getString("lbFirstName").getBytes("ISO-8859-1"), "UTF-8"));
            lastName.setPromptText(new String(resourceBundle.getString("lbLastName").getBytes("ISO-8859-1"), "UTF-8"));
            mail.setPromptText(new String(resourceBundle.getString("lbEMail").getBytes("ISO-8859-1"), "UTF-8"));
            mobile.setPromptText(new String(resourceBundle.getString("lbTelephone").getBytes("ISO-8859-1"), "UTF-8"));
            zipCode.setPromptText(new String(resourceBundle.getString("lbZIP").getBytes("ISO-8859-1"), "UTF-8"));
            city.setPromptText(new String(resourceBundle.getString("lbCity").getBytes("ISO-8859-1"), "UTF-8"));
            street.setPromptText(new String(resourceBundle.getString("lbStreet").getBytes("ISO-8859-1"), "UTF-8"));
            searchCustomer.setPromptText(new String(resourceBundle.getString("lbPrompSearch").getBytes("ISO-8859-1"), "UTF-8"));
            coursesCombobox.setPromptText(new String(resourceBundle.getString("lbAllCourse").getBytes("ISO-8859-1"), "UTF-8"));

            // Buttons
            goToMainWindowBtn.setText(new String(resourceBundle.getString("lbGoHomePage").getBytes("ISO-8859-1"), "UTF-8"));
            deleteSearchBtn.setText(new String(resourceBundle.getString("lbResetSearch").getBytes("ISO-8859-1"), "UTF-8"));
            addBtn.setText(new String(resourceBundle.getString("lbAdd").getBytes("ISO-8859-1"), "UTF-8"));
            changeBtn.setText(new String(resourceBundle.getString("lbChange").getBytes("ISO-8859-1"), "UTF-8"));
            removeBtn.setText(new String(resourceBundle.getString("lbRemove").getBytes("ISO-8859-1"), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
