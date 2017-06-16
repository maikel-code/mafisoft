package dao;

import DBHelper.DBHelper;
import dto.courses.Course;
import dto.courses.PhysicalCourse;
import dto.customer.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logic.CourseList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOCourse implements DAOCourse_I {
    private static DBHelper dbHelper = DBHelper.getInstance();

    public static void addPhysicalCourse(PhysicalCourse dtoPhysicalCourse) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            PreparedStatement preparedStatement = dbHelper.getConnection()
                    .prepareStatement("INSERT INTO course(course_name, trainer_name, start, end) VALUE (?, ?, ?, ?)");
            preparedStatement.setString(1, dtoPhysicalCourse.getCourse_name());
            preparedStatement.setString(2, dtoPhysicalCourse.getTrainer_name());
            preparedStatement.setTime(3, dtoPhysicalCourse.getStartTime());
            preparedStatement.setTime(4, dtoPhysicalCourse.getEndTime());
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Course

    public static void updateCourse(PhysicalCourse dtoPhysicalCourse) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            PreparedStatement preparedStatement = dbHelper.getConnection().prepareStatement(
                    "UPDATE course SET course_name=?, trainer_name=?, start=?, end=? WHERE course_id=?");
            preparedStatement.setInt(5, dtoPhysicalCourse.getId());
            preparedStatement.setString(1, dtoPhysicalCourse.getCourse_name());
            preparedStatement.setString(2, dtoPhysicalCourse.getTrainer_name());
            preparedStatement.setTime(3, dtoPhysicalCourse.getStartTime());
            preparedStatement.setTime(4, dtoPhysicalCourse.getEndTime());
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static PhysicalCourse searchCourse(String searchConfig, String search) {
        try {
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
                            .prepareStatement("SELECT * FROM course WHERE trainer_name=?");
                    preparedStatement.setString(1, search);
                    rs = preparedStatement.executeQuery();
            }
            PhysicalCourse course = new PhysicalCourse();

            course.setId(rs.getInt("id"));
            course.setCourse_name(rs.getString("course_name"));
            course.setTrainer_name(rs.getString("trainer_name"));
            course.setStartTime(rs.getTime("start"));
            course.setEndTime(rs.getTime("end"));

            return course;

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ResultSet getAllAvailabileCourse(int customer_id) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement preparedStatement = dbHelper.getConnection().prepareStatement(
                "SELECT course.* FROM course WHERE course_id NOT IN (SELECT cc.course_id FROM customer_course cc  WHERE cc.customer_id = ? )");
        preparedStatement.setInt(1, customer_id);
        return preparedStatement.executeQuery();
    }

    public static void addCourseToCustomer(int customer_id, int course_id) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = dbHelper.getConnection()
                .prepareStatement("INSERT INTO customer_course(customer_id, course_id) VALUE (?, ?)");
        preparedStatement.setInt(1, customer_id);
        preparedStatement.setInt(2, course_id);
        preparedStatement.executeUpdate();
    }

    public static void removeCourse(String customerID, int courseID) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = dbHelper.getConnection().prepareStatement(
                "DELETE customer_course FROM course INNER JOIN customer_course WHERE customer_course.customer_id=? AND customer_course.course_id=?");
        preparedStatement.setString(1, customerID);
        preparedStatement.setInt(2, courseID);
        preparedStatement.executeLargeUpdate();
    }

    // VIDEO COURSES
    public static void addVideoCourse(String courseName, String trainer, String link, String remark)
            throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = dbHelper.getConnection()
                .prepareStatement("INSERT INTO video_course(courseName, trainerName, link, remark) VALUE (?, ?, ?, ?)");
        preparedStatement.setString(1, courseName);
        preparedStatement.setString(2, trainer);
        preparedStatement.setString(3, link);
        preparedStatement.setString(4, remark);
        preparedStatement.executeUpdate();
    }

    public static void updateVideoCourse(int id, String courseName, String trainerName, String link, String remark)
            throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement preparedStatement = dbHelper.getConnection().prepareStatement(
                "UPDATE video_course SET courseName=?, trainerName=?, link=?, remark=? WHERE videoID=?");
        preparedStatement.setInt(5, id);
        preparedStatement.setString(1, courseName);
        preparedStatement.setString(2, trainerName);
        preparedStatement.setString(3, link);
        preparedStatement.setString(4, remark);
        preparedStatement.executeUpdate();
    }

    public static ResultSet searchVideoCourse(String searchConfig, String search)
            throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement preparedStatement;
        switch (searchConfig) {
            case "id":
            case "ID":
                preparedStatement = dbHelper.getConnection()
                        .prepareStatement("SELECT * FROM video_course WHERE videoID=?");
                preparedStatement.setString(1, search);
                return preparedStatement.executeQuery();
            default:
                preparedStatement = dbHelper.getConnection()
                        .prepareStatement("SELECT * FROM video_course WHERE courseName=? AND trainerName=?");
                preparedStatement.setString(1, search.split("\\p{Punct}")[0]);
                preparedStatement.setString(1, search.split("\\p{Punct}")[1]);
                return preparedStatement.executeQuery();
        }
    }

    public static ResultSet getAllVideoCourse() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        PreparedStatement preparedStatement = dbHelper.getConnection()
                .prepareStatement("SELECT * FROM video_course");
        return preparedStatement.executeQuery();
    }

    public ObservableList<Course> getAllCourseByCustomer(Customer dtoCustomer) {
        ObservableList<Course> row = FXCollections.observableArrayList();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            PreparedStatement preparedStatement = dbHelper.getConnection().prepareStatement(
                    "SELECT course.* FROM course JOIN customer_course cc ON cc.course_id = course.course_id  WHERE cc.customer_id = ?");
            preparedStatement.setInt(1, dtoCustomer.getCustomerID());

            ResultSet rs = preparedStatement.executeQuery();

            CourseList courseListController = new CourseList();
            while (rs.next()) {
                row.add(courseListController.fillCourse(rs));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return row;
    }

    public ObservableList<Course> getAllCourse() {
        ObservableList<Course> row = FXCollections.observableArrayList();

        try {
            Class.forName("com.mysql.jdbc.Driver");
            PreparedStatement preparedStatement = dbHelper.getConnection().prepareStatement("SELECT * FROM course");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                PhysicalCourse dtoCourse = new PhysicalCourse();

                dtoCourse.setId(rs.getInt("id"));
                dtoCourse.setCourse_name(rs.getString("course_name"));
                dtoCourse.setTrainer_name(rs.getString("trainer_name"));
                dtoCourse.setStartTime(rs.getTime("start"));
                dtoCourse.setEndTime(rs.getTime("end"));

                row.add(dtoCourse);
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return row;
    }

    @Override
    public void addCourse(Course course) {
        // TODO Auto-generated method stub

    }

    @Override
    public void updateCourse() {
        // TODO Auto-generated method stub

    }

    @Override
    public Course searchCourse() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ObservableList<Course> getAllAvailabileCourse() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addCourseToCustomer() {
        // TODO Auto-generated method stub

    }

    @Override
    public void removeCourse() {
        // TODO Auto-generated method stub

    }

}
