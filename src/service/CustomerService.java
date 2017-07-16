package service;

import DBHelper.DBHelper;
import config.R;
import dao.CustomerDAO;
import dto.customer.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logger.Log;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Contains all DB operations for the ChangeCustomerData and AddCustomer logic layer
 */
public class CustomerService implements CustomerDAO {
    private static DBHelper dbHelper = DBHelper.getInstance();
    private static final Logger LOGGER = Log.getLogger(CustomerService.class);

    public int addCustomer(Customer customer) {
        PreparedStatement preparedStatement;
        Connection connection;
        Integer id = -1;

        try {
            Class.forName(R.DB.DB_DRIVER);
            connection = dbHelper.getConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO customer(customer_firstname, customer_lastname, birthday, email, mobilephone, zipCode, city, street, end_time, create_time)" +
                    " VALUE (?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, customer.getFirstname());
            preparedStatement.setString(2, customer.getLastname());
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

            LOGGER.info("Add customer id = " + id);
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        return id;
    }

    public void updateCustomer(Customer customer) {
        PreparedStatement preparedStatement;
        Connection connection;

        try {
            Class.forName(R.DB.DB_DRIVER);
            connection = dbHelper.getConnection();
            preparedStatement = connection.prepareStatement("UPDATE customer SET customer_firstname=?, customer_lastname=?, email=?, mobilephone=?, zipCode=?, city=?, street=? " +
                    "WHERE customer_id=?");

            preparedStatement.setInt(8, customer.getId());
            preparedStatement.setString(1, customer.getFirstname());
            preparedStatement.setString(2, customer.getLastname());
            preparedStatement.setString(3, customer.getMail());
            preparedStatement.setString(4, customer.getMobilephone());
            preparedStatement.setInt(5, customer.getZipCode());
            preparedStatement.setString(6, customer.getCity());
            preparedStatement.setString(7, customer.getStreet());
            preparedStatement.executeUpdate();

            LOGGER.info("Update customer id = " + customer.getId());
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }

    public ObservableList<Customer> searchCustomer(String searchConfig, String search) {
        ObservableList<Customer> row = FXCollections.observableArrayList();
        ResultSet rs;
        PreparedStatement preparedStatement;
        Connection connection;
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
                    LOGGER.info("Search customer by name= " + search);
                    break;
                case "id":
                case "ID":
                default:
                    preparedStatement = connection.prepareStatement("SELECT * FROM customer WHERE customer_id=?");
                    preparedStatement.setString(1, search);
                    rs = preparedStatement.executeQuery();
                    LOGGER.info("Search customer by id= " + search);
            }

            while (rs.next()) {
                row.add(this.createCustomerFromRow(rs));
            }


        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        return row;
    }

    public void removeCustomerById(int id) {
        PreparedStatement preparedStatement;
        Connection connection;
        try {
            Class.forName(R.DB.DB_DRIVER);
            connection = dbHelper.getConnection();
            preparedStatement = connection.prepareStatement("SELECT customer.* FROM customer WHERE customer_id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            LOGGER.info("Delete customer by id = " + id);
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }

    }

    public ObservableList<Customer> getAllCustomer() {
        ObservableList<Customer> row = FXCollections.observableArrayList();
        PreparedStatement preparedStatement;
        Connection connection;
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
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }

        return row;

    }

    private Customer createCustomerFromRow(ResultSet rs) {
        Customer customer = new Customer();

        try {
            customer.setId(rs.getInt("customer_id"));
            customer.setFirstname(rs.getString("customer_firstname"));
            customer.setLastname(rs.getString("customer_lastname"));
            customer.setBirthday(rs.getDate("birthday"));
            customer.setMail(rs.getString("email"));
            customer.setMobilephone(rs.getString("mobilephone"));
            customer.setZipCode(rs.getInt("zipCode"));
            customer.setCity(rs.getString("city"));
            customer.setStreet(rs.getString("street"));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }

        return customer;
    }

}
