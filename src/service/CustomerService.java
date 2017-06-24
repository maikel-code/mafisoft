package service;

import DBHelper.DBHelper;
import config.R;
import dao.CustomerDAO;
import dto.customer.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class CustomerService implements CustomerDAO {
    private static DBHelper dbHelper = DBHelper.getInstance();

    public int addCustomer(Customer customer) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        Integer id = -1;

        try {
            Class.forName(R.DB.DB_DRIVER);
            connection = dbHelper.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO customer(customer_firstname, customer_lastname, birthday, email, mobilephone, zipCode, city, street, end_time, create_time)" +
                    " VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, customer.getCustomer_firstname());
            preparedStatement.setString(2, customer.getCustomer_lastname());
            preparedStatement.setDate(3, customer.getBirthday());
            preparedStatement.setString(4, customer.getMail());
            preparedStatement.setString(5, customer.getMobilephone());
            preparedStatement.setInt(6, customer.getZipCode());
            preparedStatement.setString(7, customer.getCity());
            preparedStatement.setString(8, customer.getStreet());
            preparedStatement.setDate(9, customer.getEndDate());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void updateCustomer(Customer customer) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;

        try {
            Class.forName(R.DB.DB_DRIVER);
            connection = dbHelper.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE customer SET customer_firstname=?, customer_lastname=?, email=?, mobilephone=?, zipCode=?, city=?, street=? " +
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
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Customer> searchCustomer(String searchConfig, String search) throws SQLException {
        ObservableList<Customer> row = FXCollections.observableArrayList();
        ResultSet rs;
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            Class.forName(R.DB.DB_DRIVER);
            connection = dbHelper.getConnection();
            switch (searchConfig) {
                case "name":
                case "Name":
                    preparedStatement = connection.prepareStatement("SELECT * FROM customer WHERE customer_firstname LIKE ? OR customer_lastname LIKE ?");
                    preparedStatement.setString(1, search + "%");
                    preparedStatement.setString(2, search + "%");
                    rs = preparedStatement.executeQuery();
                    break;
                case "id":
                case "ID":
                default:
                    preparedStatement = connection.prepareStatement("SELECT * FROM customer WHERE customer_id=?");
                    preparedStatement.setString(1, search);
                    rs = preparedStatement.executeQuery();
            }

            while (rs.next()) {
                row.add(this.createCustomerFromRow(rs));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return row;
    }

    public ObservableList<Customer> getAllCustomer() throws SQLException {
        ObservableList<Customer> row = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet rs;

        try {
            Class.forName(R.DB.DB_DRIVER);
            connection = dbHelper.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM customer");

            rs = preparedStatement.executeQuery();

            while (rs.next()) {
                row.add(this.createCustomerFromRow(rs));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
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