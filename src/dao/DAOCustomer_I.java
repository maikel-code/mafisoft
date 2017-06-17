package dao;

import dto.customer.Customer;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface DAOCustomer_I {

    void addCustomer(Customer customer) throws SQLException, ClassNotFoundException;
    void updateCustomer(Customer updateCustomer) throws SQLException, ClassNotFoundException;

    ObservableList<Customer> searchCustomer(String searchConfig, String search) throws SQLException, ClassNotFoundException;
    ObservableList<Customer> getAllCustomer() throws SQLException, ClassNotFoundException;

}
