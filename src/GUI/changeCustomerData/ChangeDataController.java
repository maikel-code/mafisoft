package GUI.changeCustomerData;


import DBHelper.DBController;
import GUI.changeCourseList.CourseListController;
import catalouge.courses.Course;
import catalouge.customer.Customer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ChangeDataController implements Initializable {
    @FXML
    private TextField changeFirstName, changeLastName, changeMail, changePhonenummer, changeCity, changeStreet, customerTXT, customerID;
    @FXML // fx:id="courses"
    private ComboBox<String> courses;
    @FXML
    private TextField changeZipcode;
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn id, firstName, lastName, birthday, mail, phonenummer, address;
    private ObservableList<Course> allCourses;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillTale();


        courses.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> selected, String oldCourse, String newCourse) {

                for (Course oneCourse: allCourses) {
                    if(newCourse.equals(oneCourse.getCourse_name() + " ID:" + oneCourse.getCourse_id()) ) {

                    }
                }
            }
        });

    }

    private void fillTale() {
        try {
            id.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerID"));
            firstName.setCellValueFactory(new PropertyValueFactory<Customer, String>("customer_firstname"));
            lastName.setCellValueFactory(new PropertyValueFactory<Customer, String>("customer_lastname"));
            birthday.setCellValueFactory(new PropertyValueFactory<Customer, Date>("birthday"));
            mail.setCellValueFactory(new PropertyValueFactory<Customer, String>("mail"));
            phonenummer.setCellValueFactory(new PropertyValueFactory<Customer, String>("mobilephone"));
            address.setCellValueFactory(new PropertyValueFactory<Customer, String>("zipCode"));
            address.setCellValueFactory(new PropertyValueFactory<Customer, String>("city"));
            address.setCellValueFactory(new PropertyValueFactory<Customer, String>("street"));
            customerTable.setItems(getCustomerList());

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private ObservableList<Course> getCourseList(int customer_id) throws SQLException, ClassNotFoundException {
        CourseListController courseListController = new CourseListController();
        ResultSet rs = DBController.getAllAvailabileCourse(customer_id);
        ObservableList<Course> row = FXCollections.observableArrayList();
        while (rs.next()) {
            row.add(courseListController.fillCourse(rs));
        }
        return row;
    }


    private ObservableList<Customer> getCustomerList() throws SQLException, ClassNotFoundException {
        ResultSet rs = DBController.getAllCustomer();
        ObservableList<Customer> row = FXCollections.observableArrayList();
        while (rs.next()) {
            row.add(fillCustomer(rs));
        }
        return row;
    }

    private void fillChangeTabble(Customer customer) {
        changePhonenummer.clear();

        if (!customer.getMail().isEmpty())
            changeMail.setText(customer.getMail());

        if (!customer.getMobilephone().isEmpty())
            changePhonenummer.setText(customer.getMobilephone());

        customerID.setText(customer.getCustomerID() + "");
        changeFirstName.setText(customer.getCustomer_firstname());
        changeLastName.setText(customer.getCustomer_lastname());
        changeZipcode.setText(customer.getZipCode() + "");
        changeCity.setText(customer.getCity());
        changeStreet.setText(customer.getStreet());


        try {
            allCourses = getCourseList(  customer.getCustomerID() );
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        courses.getItems().clear();
        for (Course oneCourse: allCourses) {
            courses.getItems().add(oneCourse.getCourse_name() + " ID:" + oneCourse.getCourse_id());
        }
    }

    public void mouseOnClick(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            fillChangeTabble(customerTable.getSelectionModel().getSelectedItem());
        }
    }

    public void changeButton() {
        try {
            DBController.updateCustomer(Integer.parseInt(customerID.getText()), changeFirstName.getText(), changeLastName.getText(),
                    changeMail.getText(), changePhonenummer.getText(),
                    Integer.parseInt(changeZipcode.getText()), changeCity.getText(), changeStreet.getText());
            fillTale();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addCourse() {
        try {
            String[] parts = courses.getValue().split("ID:");
            DBController.addCourseToCustomer(Integer.parseInt(customerID.getText()), Integer.parseInt(parts[1]) );
            fillChangeTabble(customerTable.getSelectionModel().getSelectedItem());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchButton() {
        ResultSet rs;

        String search = customerTXT.getText();
        try {
            if (search.matches("\\b\\d+\\b")) {
                rs = DBController.searchCustomer("ID", search);
            } else {
                rs = DBController.searchCustomer("name", search);
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

    private Customer fillCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();

        customer.setCustomerID(rs.getInt("customer_id"));
        customer.setCustomer_firstname(rs.getString("customer_firstname"));
        customer.setCustomer_lastname(rs.getString("customer_lastname"));
        customer.setBirthday(rs.getDate("birthday"));
        customer.setMail(rs.getString("email"));
        customer.setMobilephone(rs.getString("mobilephone"));
        customer.setZipCode(rs.getInt("zipCode"));
        customer.setCity(rs.getString("city"));
        customer.setStreet(rs.getString("street"));

        // TODO: create Course Object List from database
        return customer;
    }

    public void goToMainWindow(ActionEvent actionEvent) {
        try {
            Parent home_page_parent = FXMLLoader.load(getClass().getResource("/GUI/MaFiSoftHomepage/Homepage.fxml"));
            Scene home_page_scene = new Scene(home_page_parent);
            Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            app_stage.hide();
            app_stage.setScene(home_page_scene);
            app_stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
