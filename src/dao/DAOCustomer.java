package dao;

import DBHelper.DBHelper;
import dto.customer.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAOCustomer implements DAOCustomer_I {
    private static DBHelper dbHelper = DBHelper.getInstance();


    public int addCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement preparedStatement = dbHelper.getConnection().prepareStatement("INSERT INTO customer(customer_firstname, customer_lastname, birthday, email, mobilephone, zipCode, city, street, create_time, end_time)" +
                " VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        preparedStatement.setString(1, customer.getCustomer_firstname());
        preparedStatement.setString(2, customer.getCustomer_lastname());
        preparedStatement.setDate(3, customer.getBirthday());
        preparedStatement.setString(4, customer.getMail());
        preparedStatement.setString(5, customer.getMobilephone());
        preparedStatement.setInt(6, customer.getZipCode());
        preparedStatement.setString(7, customer.getCity());
        preparedStatement.setString(8, customer.getStreet());
        return preparedStatement.executeUpdate(null, Statement.RETURN_GENERATED_KEYS);
    }

    public void updateCustomer(Customer customer) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement preparedStatement = dbHelper.getConnection().prepareStatement("UPDATE customer SET customer_firstname=?, customer_lastname=?, email=?, mobilephone=?, zipCode=?, city=?, street=? " +
                "WHERE customer_id=?");

        preparedStatement.setInt(8, customer.getCustomerID());
        preparedStatement.setString(1, customer.getCustomer_firstname());
        preparedStatement.setString(2, customer.getCustomer_lastname());
        preparedStatement.setString(3, customer.getMail());
        preparedStatement.setString(4, customer.getMobilephone());
        preparedStatement.setInt(5, customer.getZipCode());
        preparedStatement.setString(6, customer.getCity());
        preparedStatement.setString(7, customer.getStreet());
        preparedStatement.executeUpdate();
    }

    public ObservableList<Customer> searchCustomer(String searchConfig, String search) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        ResultSet rs;
        PreparedStatement preparedStatement;
        switch (searchConfig) {
            case "name":
            case "Name":
                preparedStatement = dbHelper.getConnection().prepareStatement("SELECT * FROM customer WHERE customer_firstname LIKE ? OR customer_lastname LIKE ?");
                preparedStatement.setString(1, search.split("\\p{Punct} ")[0] + "%");
                preparedStatement.setString(2, search.split("\\p{Punct} ")[1] + "%");
                rs = preparedStatement.executeQuery();
            break;
            case "id":
            case "ID":
            default:
                preparedStatement = dbHelper.getConnection().prepareStatement("SELECT * FROM customer WHERE customer_id=?");
                preparedStatement.setString(1, search);
                rs = preparedStatement.executeQuery();
        }
        ObservableList<Customer> row = FXCollections.observableArrayList();

        while (rs.next()) {
            row.add(this.createCustomerFromRow(rs));
        }
        return row;
    }

    public  ObservableList<Customer> getAllCustomer() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement preparedStatement = dbHelper.getConnection().prepareStatement("SELECT * FROM customer");

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
