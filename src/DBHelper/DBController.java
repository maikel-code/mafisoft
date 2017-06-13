package DBHelper;

import javafx.scene.control.Alert;

import java.sql.*;

public class DBController {
    private static Connection connection;

    private DBController() {
    }

    public static synchronized Connection getConnection() throws SQLException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            if (connection != null) {
                return connection;
            } else {
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mafisoftBD?autoReconnect=true&useSSL=false", "root", "mafisoftPW");
            }
        } catch (SQLException sql) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Datenank existiert nicht oder wird nicht gefunden");
            alert.setHeaderText(null);
            alert.showAndWait();
        } catch (ClassNotFoundException cnfe) {
            Alert alert = new Alert(Alert.AlertType.ERROR, cnfe.getLocalizedMessage());
            alert.setHeaderText(null);
            alert.showAndWait();
        }
        return connection;
    }

    // Customer

    public static void addCustomer(String firstName, String lastName, java.sql.Date birthday, String email, String phone, int zipCode, String city, String street, java.sql.Date beginnDate, java.sql.Date endDate) throws SQLException, ClassNotFoundException {
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

    public static void updateCustomer() throws SQLException, ClassNotFoundException {

        // int id, String firstName, String lastName, String email, String phone, int zipCode, String city, String street
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

    public static ResultSet searchCustomer(String searchConfig, String search) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement preparedStatement;
        switch (searchConfig) {
            case "name":
            case "Name":
                preparedStatement = getConnection().prepareStatement("SELECT * FROM customer WHERE customer_firstname=? AND customer_lastname=?");
                preparedStatement.setString(1, search.split("\\p{Punct} ")[0]);
                preparedStatement.setString(2, search.split("\\p{Punct} ")[1]);
                return preparedStatement.executeQuery();
            case "id":
            case "ID":
                preparedStatement = getConnection().prepareStatement("SELECT * FROM customer WHERE customer_id=?");
                preparedStatement.setString(1, search);
                return preparedStatement.executeQuery();
        }

        return null;
    }

    public static ResultSet getAllCustomer() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM customer");
        return preparedStatement.executeQuery();
    }


    // Video

    // TODO: Add methods which creates DTO´s and provides them

}
