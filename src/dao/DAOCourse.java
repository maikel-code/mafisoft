package dao;

import DBHelper.DBHelper;
import dto.courses.PhysicalCourse;
import dto.courses.VideoCourse;
import dto.customer.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOCourse implements DAOCourse_I {
    private static DBHelper dbHelper = DBHelper.getInstance();

    public ObservableList<PhysicalCourse> getAllCourseByCustomer(Customer dtoCustomer) throws SQLException, ClassNotFoundException {
        ObservableList<PhysicalCourse> row = FXCollections.observableArrayList();
        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement preparedStatement = dbHelper.getConnection().prepareStatement(
                "SELECT course.* FROM course JOIN customer_course cc ON cc.course_id = course.course_id  WHERE cc.customer_id = ?");
        preparedStatement.setInt(1, dtoCustomer.getCustomerID());

        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            row.add(this.createPhysicalCourseFromRow(rs));
        }

        return row;
    }

    // Course

    public void addCourse(PhysicalCourse dtoPhysicalCourse) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement preparedStatement = dbHelper.getConnection()
                .prepareStatement("INSERT INTO course(course_name, trainer_name, start, end) VALUE (?, ?, ?, ?)");
        preparedStatement.setString(1, dtoPhysicalCourse.getCourse_name());
        preparedStatement.setString(2, dtoPhysicalCourse.getTrainer_name());
        preparedStatement.setTime(3, dtoPhysicalCourse.getStartTime());
        preparedStatement.setTime(4, dtoPhysicalCourse.getEndTime());
        preparedStatement.executeUpdate();

    }

    public void updateCourse(PhysicalCourse dtoPhysicalCourse) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement preparedStatement = dbHelper.getConnection().prepareStatement(
                "UPDATE course SET course_name=?, trainer_name=?, start=?, end=? WHERE course_id=?");
        preparedStatement.setInt(5, dtoPhysicalCourse.getId());
        preparedStatement.setString(1, dtoPhysicalCourse.getCourse_name());
        preparedStatement.setString(2, dtoPhysicalCourse.getTrainer_name());
        preparedStatement.setTime(3, dtoPhysicalCourse.getStartTime());
        preparedStatement.setTime(4, dtoPhysicalCourse.getEndTime());
        preparedStatement.executeUpdate();
    }

    public ObservableList<PhysicalCourse> searchCourse(String searchConfig, String search) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        ResultSet rs;
        PreparedStatement preparedStatement;
        switch (searchConfig) {
            case "id":
            case "ID":
                preparedStatement = dbHelper.getConnection()
                        .prepareStatement("SELECT * FROM course WHERE course_id=?");
                preparedStatement.setString(1, search);
                rs = preparedStatement.executeQuery();
                break;
            case "trainer":
            case "Trainer":
            default:
                preparedStatement = dbHelper.getConnection()
                        .prepareStatement("SELECT * FROM course WHERE trainer_name LIKE ?");
                preparedStatement.setString(1, search + "%");
                rs = preparedStatement.executeQuery();
        }
        ObservableList<PhysicalCourse> row = FXCollections.observableArrayList();

        while (rs.next()) {
            row.add(this.createPhysicalCourseFromRow(rs));
        }
        return row;

    }

    public ObservableList<PhysicalCourse> getAllCourse() throws SQLException, ClassNotFoundException {
        ObservableList<PhysicalCourse> row = FXCollections.observableArrayList();

        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement preparedStatement = dbHelper.getConnection().prepareStatement("SELECT * FROM course");
        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            row.add(this.createPhysicalCourseFromRow(rs));
        }
        return row;
    }

    public ObservableList<PhysicalCourse> getAllAvailabileCourse(Customer dtoCustomer) throws SQLException, ClassNotFoundException {
        ObservableList<PhysicalCourse> row = FXCollections.observableArrayList();

        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement preparedStatement = dbHelper.getConnection().prepareStatement(
                "SELECT course.* FROM course WHERE course_id NOT IN (SELECT cc.course_id FROM customer_course cc  WHERE cc.customer_id = ? )");
        preparedStatement.setInt(1, dtoCustomer.getCustomerID());

        ResultSet rs = preparedStatement.executeQuery();

        while (rs.next()) {
            row.add(this.createPhysicalCourseFromRow(rs));
        }

        return row;
    }

    public void addCourseToCustomer(Customer dtoCustomer, PhysicalCourse dtoCourse) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = dbHelper.getConnection()
                .prepareStatement("INSERT INTO customer_course(customer_id, course_id) VALUE (?, ?)");
        preparedStatement.setInt(1, dtoCustomer.getCustomerID());
        preparedStatement.setInt(2, dtoCourse.getId());
        preparedStatement.executeUpdate();
    }

    public void removeCourse(Customer dtoCustomer, PhysicalCourse dtoCourse) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = dbHelper.getConnection().prepareStatement(
                "DELETE customer_course FROM course INNER JOIN customer_course WHERE customer_course.customer_id=? AND customer_course.course_id=?");
        preparedStatement.setInt(1, dtoCustomer.getCustomerID());
        preparedStatement.setInt(2, dtoCourse.getId());
        preparedStatement.executeLargeUpdate();
    }

    // VIDEO COURSES
    public void addVideoCourse(VideoCourse dtoVideoCourse)
            throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = dbHelper.getConnection()
                .prepareStatement("INSERT INTO video_course(courseName, trainerName, link, remark) VALUE (?, ?, ?, ?)");
        preparedStatement.setString(1, dtoVideoCourse.getCourse_name());
        preparedStatement.setString(2, dtoVideoCourse.getTrainer_name());
        preparedStatement.setString(3, dtoVideoCourse.getvLink());
        preparedStatement.setString(4, dtoVideoCourse.getvRemark());
        preparedStatement.executeUpdate();
    }

    public void updateVideoCourse(VideoCourse dtoVideoCourse)
            throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement preparedStatement = dbHelper.getConnection().prepareStatement(
                "UPDATE video_course SET courseName=?, trainerName=?, link=?, remark=? WHERE videoID=?");
        preparedStatement.setInt(5, dtoVideoCourse.getId());
        preparedStatement.setString(1, dtoVideoCourse.getCourse_name());
        preparedStatement.setString(2, dtoVideoCourse.getTrainer_name());
        preparedStatement.setString(3, dtoVideoCourse.getvLink());
        preparedStatement.setString(4, dtoVideoCourse.getvRemark());
        preparedStatement.executeUpdate();
    }

    public ObservableList<VideoCourse> searchVideoCourse(String searchConfig, String search)
            throws SQLException, ClassNotFoundException {
        ObservableList<VideoCourse> videoCourseList = FXCollections.observableArrayList();
        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement preparedStatement;
        switch (searchConfig) {
            case "id":
            case "ID":
                preparedStatement = dbHelper.getConnection()
                        .prepareStatement("SELECT * FROM video_course WHERE videoID=?");
                preparedStatement.setString(1, search);
                break;
            default:
                preparedStatement = dbHelper.getConnection()
                        .prepareStatement("SELECT * FROM video_course WHERE courseName LIKE ? OR trainerName LIKE ?");
                preparedStatement.setString(1, search.split("\\p{Punct}")[0] + "%");
                preparedStatement.setString(1, search.split("\\p{Punct}")[1] + "%");
        }

        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            videoCourseList.add(this.createVideoCourseFromRow(rs));
        }

        return videoCourseList;
    }

    public ObservableList<VideoCourse> getAllVideoCourse() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        ObservableList<VideoCourse> videoCourseList = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = dbHelper.getConnection()
                .prepareStatement("SELECT * FROM video_course");

        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            videoCourseList.add(this.createVideoCourseFromRow(rs));
        }

        return videoCourseList;
    }

    private VideoCourse createVideoCourseFromRow(ResultSet rs) throws SQLException {
        VideoCourse dtoVideoCourse = new VideoCourse();

        dtoVideoCourse.setId(rs.getInt("videoID"));
        dtoVideoCourse.setCourse_name(rs.getString("courseName"));
        dtoVideoCourse.setTrainer_name(rs.getString("trainerName"));
        dtoVideoCourse.setvLink(rs.getString("link"));
        dtoVideoCourse.setvRemark(rs.getString("remark"));

        return dtoVideoCourse;
    }

    private PhysicalCourse createPhysicalCourseFromRow(ResultSet rs) throws SQLException {
        PhysicalCourse dtoCourse = new PhysicalCourse();

        dtoCourse.setId(rs.getInt("course_id"));
        dtoCourse.setCourse_name(rs.getString("course_name"));
        dtoCourse.setTrainer_name(rs.getString("trainer_name"));
        dtoCourse.setStartTime(rs.getTime("start"));
        dtoCourse.setEndTime(rs.getTime("end"));

        return dtoCourse;
         
    }

}
