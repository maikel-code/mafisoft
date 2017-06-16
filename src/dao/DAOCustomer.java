package dao;

import DBHelper.DBHelper;
import dto.customer.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOCustomer {
    private DBHelper dbHelper = DBHelper.getInstance();


    public void addCustomer(String firstName, String lastName, java.sql.Date birthday, String email, String phone, int zipCode, String city, String street, java.sql.Date beginnDate, java.sql.Date endDate) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO customer(customer_firstname, customer_lastname, birthday, email, mobilephone, zipCode, city, street, create_time, end_time)" +
                " VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.setDate(3, birthday);
        preparedStatement.setString(4, email);
        preparedStatement.setString(5, phone);
        preparedStatement.setInt(6, zipCode);
        preparedStatement.setString(7, city);
        preparedStatement.setString(8, street);
        preparedStatement.setDate(9, beginnDate);
        preparedStatement.setDate(10, endDate);
        preparedStatement.executeUpdate();
    }

    public void updateCustomer(int id, String firstName, String lastName, String email, String phone, int zipCode, String city, String street) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE customer SET customer_firstname=?, customer_lastname=?, email=?, mobilephone=?, zipCode=?, city=?, street=? " +
                "WHERE customer_id=?");

        preparedStatement.setInt(8, id);
        preparedStatement.setString(1, firstName);
        preparedStatement.setString(2, lastName);
        preparedStatement.setString(3, email);
        preparedStatement.setString(4, phone);
        preparedStatement.setInt(5, zipCode);
        preparedStatement.setString(6, city);
        preparedStatement.setString(7, street);
        preparedStatement.executeUpdate();
    }

    public ObservableList<Customer> searchCustomer(String searchConfig, String search) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        ResultSet rs;
        PreparedStatement preparedStatement;
        switch (searchConfig) {
            case "name":
            case "Name":
                preparedStatement = getConnection().prepareStatement("SELECT * FROM customer WHERE customer_firstname LIKE ? OR customer_lastname LIKE ?");
                preparedStatement.setString(1, search.split("\\p{Punct} ")[0] + "%");
                preparedStatement.setString(2, search.split("\\p{Punct} ")[1] + "%");
                rs = preparedStatement.executeQuery();
            break;
            case "id":
            case "ID":
            default:
                preparedStatement = getConnection().prepareStatement("SELECT * FROM customer WHERE customer_id=?");
                preparedStatement.setString(1, search);
                rs = preparedStatement.executeQuery();
        }
        ObservableList<Customer> row = FXCollections.observableArrayList();

        while (rs.next()) {
            row.add(this.createCustomerFromRow(rs));
        }
        return row;
    }

    public ObservableList<Customer> getAllCustomer() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM customer");

        ResultSet rs = preparedStatement.executeQuery();
        ObservableList<Customer> row = FXCollections.observableArrayList();

        while (rs.next()) {
            row.add(this.createCustomerFromRow(rs));
        }
        return row;

    }

    private Customer createCustomerFromRow(ResultSet rs) throws SQLException {
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




}
