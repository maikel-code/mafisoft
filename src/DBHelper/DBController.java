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
                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mafisoftBD?autoReconnect=true&useSSL=false", "root", "mafisoft");
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

    public static void updateCustomer(int id, String firstName, String lastName, String email, String phone, int zipCode, String city, String street) throws SQLException, ClassNotFoundException {
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

    public static ResultSet getAllCourseByCustomer(int customerID) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT course.* FROM course JOIN customer_course cc ON cc.course_id = course.course_id  WHERE cc.customer_id = ?");
        preparedStatement.setInt(1, customerID);
        return preparedStatement.executeQuery();
    }

    // Course

    public static void addCourse(String courseName, String trainer, Time startTime, Time endTime) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO course(course_name, trainer_name, start, end) VALUE (?, ?, ?, ?)");
        preparedStatement.setString(1, courseName);
        preparedStatement.setString(2, trainer);
        preparedStatement.setTime(3, startTime);
        preparedStatement.setTime(4, endTime);
        preparedStatement.executeUpdate();
    }

    public static void updateCourse(int id, String courseName, String trainer, Time startTime, Time endTime) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE course SET course_name=?, trainer_name=?, start=?, [end]=? WHERE course_id=?");
        preparedStatement.setInt(5, id);
        preparedStatement.setString(1, courseName);
        preparedStatement.setString(2, trainer);
        preparedStatement.setTime(3, startTime);
        preparedStatement.setTime(4, endTime);
        preparedStatement.executeUpdate();
    }

    public static ResultSet searchCourse(String searchConfig, String search) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement preparedStatement;
        switch (searchConfig) {
            case "trainer":
            case "Trainer":
                preparedStatement = getConnection().prepareStatement("SELECT * FROM course WHERE trainer_name=?");
                preparedStatement.setString(1, search);
                return preparedStatement.executeQuery();
            case "id":
            case "ID":
                preparedStatement = getConnection().prepareStatement("SELECT * FROM course WHERE course_id=?");
                preparedStatement.setString(1, search);
                return preparedStatement.executeQuery();
        }

        return null;
    }

    public static ResultSet getAllCourse() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM course");
        return preparedStatement.executeQuery();
    }

    public static ResultSet getAllAvailabileCourse(int customer_id) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT course.* FROM course WHERE course_id NOT IN (SELECT cc.course_id FROM customer_course cc  WHERE cc.customer_id = ? )");
        preparedStatement.setInt(1, customer_id);
        return preparedStatement.executeQuery();
    }

    public static void addCourseToCustomer(int customer_id, int course_id) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO customer_course(customer_id, course_id) VALUE (?, ?)");
        preparedStatement.setInt(1, customer_id);
        preparedStatement.setInt(2, course_id);
        preparedStatement.executeUpdate();
    }

    public static void removeCourse(String customerID, int courseID) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("DELETE customer_course FROM course INNER JOIN customer_course WHERE customer_course.customer_id=? AND customer_course.course_id=?");
        preparedStatement.setString(1, customerID);
        preparedStatement.setInt(2, courseID);
        preparedStatement.executeLargeUpdate();
    }

    // Video

    public static void addVideoCourse(String courseName, String trainer, String link, String remark) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = getConnection().prepareStatement("INSERT INTO video_course(courseName, trainerName, link, remark) VALUE (?, ?, ?, ?)");
        preparedStatement.setString(1, courseName);
        preparedStatement.setString(2, trainer);
        preparedStatement.setString(3, link);
        preparedStatement.setString(4, remark);
        preparedStatement.executeUpdate();
    }

    public static void updateVideoCourse(int id, String courseName, String trainerName, String link, String remark) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement preparedStatement = getConnection().prepareStatement("UPDATE video_course SET courseName=?, trainerName=?, link=?, remark=? WHERE videoID=?");
        preparedStatement.setInt(5, id);
        preparedStatement.setString(1, courseName);
        preparedStatement.setString(2, trainerName);
        preparedStatement.setString(3, link);
        preparedStatement.setString(4, remark);
        preparedStatement.executeUpdate();
    }

    public static ResultSet searchVideoCourse(String searchConfig, String search) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement preparedStatement;
        switch (searchConfig) {
            case "id":
            case "ID":
                preparedStatement = getConnection().prepareStatement("SELECT * FROM video_course WHERE videoID=?");
                preparedStatement.setString(1, search);
                return preparedStatement.executeQuery();
            default:
                preparedStatement = getConnection().prepareStatement("SELECT * FROM video_course WHERE courseName=? AND trainerName=?");
                preparedStatement.setString(1, search.split("\\p{Punct}")[0]);
                preparedStatement.setString(1, search.split("\\p{Punct}")[1]);
                return preparedStatement.executeQuery();
        }
    }

    public static ResultSet getAllVideoCourse() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement preparedStatement = getConnection().prepareStatement("SELECT * FROM video_course");
        return preparedStatement.executeQuery();
    }

}
