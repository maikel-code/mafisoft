package changeCustomerData;


import DBHelper.DBController;
import customer.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
    @FXML
    private TextField changeZipcode;
    @FXML
    private TableView<Customer> customerTable;
    @FXML
    private TableColumn id, firstName, lastName, birthday, mail, phonenummer, address;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillTale();
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

        return customer;
    }

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

}
