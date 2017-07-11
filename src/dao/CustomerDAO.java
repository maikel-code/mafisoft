package dao;

import dto.customer.Customer;
import javafx.collections.ObservableList;

public interface CustomerDAO {

    int addCustomer(Customer customer) ;

    void updateCustomer(Customer updateCustomer) ;

    ObservableList<Customer> searchCustomer(String searchConfig, String search) ;

    ObservableList<Customer> getAllCustomer() ;

    void removeCustomerById(int id);

}
