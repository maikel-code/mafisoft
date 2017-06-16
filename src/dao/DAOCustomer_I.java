package dao;

import dto.customer.Customer;
import javafx.collections.ObservableList;

public interface DAOCustomer_I {


    void addCustomer(Customer customer) throws SQLException, ClassNotFoundException;
    void addCustomer(Customer updateCustomer) throws SQLException, ClassNotFoundException;

    ObservableList<Customer> searchCustomer(String searchConfig, String search) throws SQLException, ClassNotFoundException;
    ObservableList<Customer> getAllCustomer() throws SQLException, ClassNotFoundException;


}
