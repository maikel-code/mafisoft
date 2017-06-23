package service;

import DBHelper.DBHelper;
import config.R;
import dao.CourseDAO;
import dto.courses.PhysicalCourse;
import dto.courses.VideoCourse;
import dto.customer.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class CourseService implements CourseDAO {
    private static DBHelper dbHelper = DBHelper.getInstance();

    public ObservableList<PhysicalCourse> getAllCourseByCustomer(Customer dtoCustomer) throws SQLException {
        ObservableList<PhysicalCourse> row = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        Connection connection = null;

        try {
            Class.forName(R.DB.DB_DRIVER);
            connection = dbHelper.getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT course.* FROM course JOIN customer_course cc ON cc.course_id = course.course_id  WHERE cc.customer_id = ?");
            preparedStatement.setInt(1, dtoCustomer.getCustomerID());

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                row.add(this.createPhysicalCourseFromRow(rs));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return row;
    }

    // Course

    /**
     * @return return PhysicalCourse id in DB
     */
    public int addPhysicalCourse(PhysicalCourse dtoPhysicalCourse) throws SQLException {

        PreparedStatement preparedStatement = null;
        Connection connection = null;
        Integer id = 1;
        try {
            Class.forName(R.DB.DB_DRIVER);
            connection = dbHelper.getConnection();
            preparedStatement = connection
                    .prepareStatement("INSERT INTO course(course_name, trainer_name, start, end) VALUE (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, dtoPhysicalCourse.getCourse_name());
            preparedStatement.setString(2, dtoPhysicalCourse.getTrainer_name());
            preparedStatement.setTime(3, dtoPhysicalCourse.getStartTime());
            preparedStatement.setTime(4, dtoPhysicalCourse.getEndTime());
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

    public void updatePhysicalCourse(PhysicalCourse dtoPhysicalCourse) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            Class.forName(R.DB.DB_DRIVER);
            connection = dbHelper.getConnection();
            preparedStatement = connection.prepareStatement(
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

    public ObservableList<PhysicalCourse> searchPhysicalCourse(String searchConfig, String search) throws SQLException {
        ObservableList<PhysicalCourse> row = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        ResultSet rs;
        try {
            Class.forName(R.DB.DB_DRIVER);
            switch (searchConfig) {
                case "id":
                case "ID":
                    preparedStatement = connection
                            .prepareStatement("SELECT * FROM course WHERE course_id=?");
                    preparedStatement.setString(1, search);
                    rs = preparedStatement.executeQuery();
                    break;
                case "trainer":
                case "Trainer":
                default:
                    preparedStatement = connection
                            .prepareStatement("SELECT * FROM course WHERE trainer_name LIKE ? OR course_name LIKE ?");
                    preparedStatement.setString(1, search + "%");
                    preparedStatement.setString(2, search + "%");
                    rs = preparedStatement.executeQuery();
            }

            while (rs.next()) {
                row.add(this.createPhysicalCourseFromRow(rs));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return row;

    }

    public ObservableList<PhysicalCourse> getAllCourse() throws SQLException {
        ObservableList<PhysicalCourse> row = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            Class.forName(R.DB.DB_DRIVER);
            connection = dbHelper.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM course");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                row.add(this.createPhysicalCourseFromRow(rs));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return row;
    }

    public ObservableList<PhysicalCourse> getAllAvailabileCourse(Customer dtoCustomer) throws SQLException {
        ObservableList<PhysicalCourse> row = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            Class.forName(R.DB.DB_DRIVER);
            connection = dbHelper.getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT course.* FROM course WHERE course_id NOT IN (SELECT cc.course_id FROM customer_course cc  WHERE cc.customer_id = ? )");
            preparedStatement.setInt(1, dtoCustomer.getCustomerID());

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                row.add(this.createPhysicalCourseFromRow(rs));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return row;
    }

    public int addCourseToCustomer(Customer dtoCustomer, PhysicalCourse dtoCourse) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        Integer id = -1;
        try {
            Class.forName(R.DB.DB_DRIVER);
            connection = dbHelper.getConnection();
            preparedStatement = connection
                    .prepareStatement("INSERT INTO customer_course(customer_id, course_id) VALUE (?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, dtoCustomer.getCustomerID());
            preparedStatement.setInt(2, dtoCourse.getId());
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

    public void removeCourse(Customer dtoCustomer, PhysicalCourse dtoCourse) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            Class.forName(R.DB.DB_DRIVER);
            connection = dbHelper.getConnection();
            preparedStatement = connection.prepareStatement(
                    "DELETE customer_course FROM course INNER JOIN customer_course WHERE customer_course.customer_id=? AND customer_course.course_id=?");
            preparedStatement.setInt(1, dtoCustomer.getCustomerID());
            preparedStatement.setInt(2, dtoCourse.getId());
            preparedStatement.executeLargeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // VIDEO COURSES
    public int addVideoCourse(VideoCourse dtoVideoCourse) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        Integer id = -1;
        try {
            Class.forName(R.DB.DB_DRIVER);
            connection = dbHelper.getConnection();
            preparedStatement = connection
                    .prepareStatement("INSERT INTO video_course(courseName, trainerName, link, remark) VALUE (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, dtoVideoCourse.getCourse_name());
            preparedStatement.setString(2, dtoVideoCourse.getTrainer_name());
            preparedStatement.setString(3, dtoVideoCourse.getvLink());
            preparedStatement.setString(4, dtoVideoCourse.getvRemark());
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

    public void updateVideoCourse(VideoCourse dtoVideoCourse) throws SQLException {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            Class.forName(R.DB.DB_DRIVER);
            connection = dbHelper.getConnection();
            preparedStatement = connection.prepareStatement(
                    "UPDATE video_course SET courseName=?, trainerName=?, link=?, remark=? WHERE videoID=?");
            preparedStatement.setInt(5, dtoVideoCourse.getId());
            preparedStatement.setString(1, dtoVideoCourse.getCourse_name());
            preparedStatement.setString(2, dtoVideoCourse.getTrainer_name());
            preparedStatement.setString(3, dtoVideoCourse.getvLink());
            preparedStatement.setString(4, dtoVideoCourse.getvRemark());
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public ObservableList<VideoCourse> searchVideoCourse(String searchConfig, String search) throws SQLException {
        ObservableList<VideoCourse> videoCourseList = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        Connection connection = null;

        try {
            Class.forName(R.DB.DB_DRIVER);
            connection = dbHelper.getConnection();
            switch (searchConfig) {
                case "id":
                case "ID":
                    preparedStatement = connection
                            .prepareStatement("SELECT * FROM video_course WHERE videoID=?");
                    preparedStatement.setString(1, search);
                    break;
                default:
                    preparedStatement = connection
                            .prepareStatement("SELECT * FROM video_course WHERE courseName LIKE ? OR trainerName LIKE ?");
                    preparedStatement.setString(1, search + "%");
                    preparedStatement.setString(2, search + "%");
            }

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                videoCourseList.add(this.createVideoCourseFromRow(rs));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return videoCourseList;
    }

    public ObservableList<VideoCourse> getAllVideoCourse() throws SQLException {
        ObservableList<VideoCourse> videoCourseList = FXCollections.observableArrayList();
        PreparedStatement preparedStatement = null;
        Connection connection = null;

        try {
            Class.forName(R.DB.DB_DRIVER);
            connection = dbHelper.getConnection();
            preparedStatement = connection
                    .prepareStatement("SELECT * FROM video_course");

            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                videoCourseList.add(this.createVideoCourseFromRow(rs));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
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
