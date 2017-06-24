package dao;

import dto.customer.Customer;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface CustomerDAO {

    int addCustomer(Customer customer) throws SQLException;

    void updateCustomer(Customer updateCustomer) throws SQLException;

    ObservableList<Customer> searchCustomer(String searchConfig, String search) throws SQLException;

    ObservableList<Customer> getAllCustomer() throws SQLException;

    void removeCustomerById(int id);

}
