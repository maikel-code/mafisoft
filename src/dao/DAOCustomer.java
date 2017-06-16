package dao;

import DBHelper.DBHelper;
import dto.customer.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOCustomer {
    private DBHelper dbHelper = DBHelper.getInstance();

    public ObservableList<Customer> getCustomerList() throws SQLException, ClassNotFoundException {
        ResultSet rs = dbHelper.getAllCustomer();
        ObservableList<Customer> row = FXCollections.observableArrayList();

        while (rs.next()) {
            row.add(fillCustomer(rs));
        }

        return row;
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

    public void addCourseToCustomer(int i, int i1) {
    }
}
