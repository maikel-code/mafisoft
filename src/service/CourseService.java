package service;

import DBHelper.DBHelper;
import config.R;
import dao.CourseDAO;
import dto.courses.PhysicalCourse;
import dto.courses.VideoCourse;
import dto.customer.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logger.Log;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CourseService implements CourseDAO {
    private static DBHelper dbHelper = DBHelper.getInstance();
    private static final Logger LOGGER = Log.getLogger(CourseService.class);

    public ObservableList<PhysicalCourse> getAllCourseByCustomer(Customer dtoCustomer) {
        ObservableList<PhysicalCourse> row = FXCollections.observableArrayList();
        PreparedStatement preparedStatement;
        Connection connection;

        try {
            Class.forName(R.DB.DB_DRIVER);
            connection = dbHelper.getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT course.* FROM course JOIN customer_course cc ON cc.course_id = course.course_id  WHERE cc.customer_id = ?");
            preparedStatement.setInt(1, dtoCustomer.getId());

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                row.add(this.createPhysicalCourseFromRow(rs));
            }
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        return row;
    }

    // Course

    /**
     * <b>Insert in to Datenbank</b>
     *
     * @param dtoPhysicalCourse created Physical Cours
     * @return return PhysicalCourse id in DB
     */
    public int addPhysicalCourse(PhysicalCourse dtoPhysicalCourse) {

        PreparedStatement preparedStatement;
        Connection connection;
        Integer id = 1;
        try {
            Class.forName(R.DB.DB_DRIVER);
            connection = dbHelper.getConnection();
            preparedStatement = connection
                    .prepareStatement("INSERT INTO course(course_name, trainer_name, start, end) VALUE (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, dtoPhysicalCourse.getCourseName());
            preparedStatement.setString(2, dtoPhysicalCourse.getTrainerName());
            preparedStatement.setTime(3, dtoPhysicalCourse.getStartTime());
            preparedStatement.setTime(4, dtoPhysicalCourse.getEndTime());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            LOGGER.info("Add new course name = " + dtoPhysicalCourse.getCourseName());
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        return id;
    }

    public void updatePhysicalCourse(PhysicalCourse dtoPhysicalCourse) {
        PreparedStatement preparedStatement;
        Connection connection;
        try {
            Class.forName(R.DB.DB_DRIVER);
            connection = dbHelper.getConnection();
            preparedStatement = connection.prepareStatement(
                    "UPDATE course SET course_name=?, trainer_name=?, start=?, end=? WHERE course_id=?");
            preparedStatement.setInt(5, dtoPhysicalCourse.getId());
            preparedStatement.setString(1, dtoPhysicalCourse.getCourseName());
            preparedStatement.setString(2, dtoPhysicalCourse.getTrainerName());
            preparedStatement.setTime(3, dtoPhysicalCourse.getStartTime());
            preparedStatement.setTime(4, dtoPhysicalCourse.getEndTime());
            preparedStatement.executeUpdate();

            LOGGER.info("Update videocourse id = " + dtoPhysicalCourse.getId() + " name = " + dtoPhysicalCourse.getCourseName());
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }

    public ObservableList<PhysicalCourse> searchPhysicalCourse(String searchConfig, String search) {
        ObservableList<PhysicalCourse> row = FXCollections.observableArrayList();
        PreparedStatement preparedStatement;
        Connection connection;
        ResultSet rs;
        try {
            Class.forName(R.DB.DB_DRIVER);
            connection = dbHelper.getConnection();
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
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        return row;

    }

    public ObservableList<PhysicalCourse> getAllCourse() {
        ObservableList<PhysicalCourse> row = FXCollections.observableArrayList();
        PreparedStatement preparedStatement;
        Connection connection;
        try {
            Class.forName(R.DB.DB_DRIVER);
            connection = dbHelper.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM course");
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                row.add(this.createPhysicalCourseFromRow(rs));
            }
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        return row;
    }

    public ObservableList<PhysicalCourse> getAllAvailabileCourse(Customer dtoCustomer) {
        ObservableList<PhysicalCourse> row = FXCollections.observableArrayList();
        PreparedStatement preparedStatement;
        Connection connection;
        try {
            Class.forName(R.DB.DB_DRIVER);
            connection = dbHelper.getConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT course.* FROM course WHERE course_id NOT IN (SELECT cc.course_id FROM customer_course cc  WHERE cc.customer_id = ? )");
            preparedStatement.setInt(1, dtoCustomer.getId());

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                row.add(this.createPhysicalCourseFromRow(rs));
            }
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }

        return row;
    }

    public void addCourseToCustomer(Customer dtoCustomer, PhysicalCourse dtoCourse) {
        PreparedStatement preparedStatement;
        Connection connection;
        Integer id = -1;
        try {
            Class.forName(R.DB.DB_DRIVER);
            connection = dbHelper.getConnection();
            preparedStatement = connection
                    .prepareStatement("INSERT INTO customer_course(customer_id, course_id) VALUE (?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, dtoCustomer.getId());
            preparedStatement.setInt(2, dtoCourse.getId());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            LOGGER.info("Add course " + dtoCourse.getCourseName() + " to customer id = " + dtoCustomer.getId());
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }

    public void removeCourseByCustomer(Customer dtoCustomer, PhysicalCourse dtoCourse) {
        PreparedStatement preparedStatement;
        Connection connection;
        try {
            Class.forName(R.DB.DB_DRIVER);
            connection = dbHelper.getConnection();
            preparedStatement = connection.prepareStatement(
                    "DELETE customer_course FROM course INNER JOIN customer_course WHERE customer_course.customer_id=? AND customer_course.course_id=?");
            preparedStatement.setInt(1, dtoCustomer.getId());
            preparedStatement.setInt(2, dtoCourse.getId());
            preparedStatement.executeLargeUpdate();

            LOGGER.info("Remove course " + dtoCourse.getCourseName() + " from customer id = " + dtoCustomer.getId());
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
    }

    // VIDEO COURSES
    public int addVideoCourse(VideoCourse dtoVideoCourse) {
        PreparedStatement preparedStatement;
        Connection connection;
        Integer id = -1;
        try {
            Class.forName(R.DB.DB_DRIVER);
            connection = dbHelper.getConnection();
            preparedStatement = connection
                    .prepareStatement("INSERT INTO video_course(courseName, trainerName, link, remark) VALUE (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, dtoVideoCourse.getCourseName());
            preparedStatement.setString(2, dtoVideoCourse.getTrainerName());
            preparedStatement.setString(3, dtoVideoCourse.getLink());
            preparedStatement.setString(4, dtoVideoCourse.getRemark());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            }

            LOGGER.info("Add new videocourse name= " + dtoVideoCourse.getCourseName());
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        return id;
    }

    public void updateVideoCourse(VideoCourse dtoVideoCourse) {
        PreparedStatement preparedStatement;
        Connection connection;
        try {
            Class.forName(R.DB.DB_DRIVER);
            connection = dbHelper.getConnection();
            preparedStatement = connection.prepareStatement(
                    "UPDATE video_course SET courseName=?, trainerName=?, link=?, remark=? WHERE videoID=?");
            preparedStatement.setInt(5, dtoVideoCourse.getId());
            preparedStatement.setString(1, dtoVideoCourse.getCourseName());
            preparedStatement.setString(2, dtoVideoCourse.getTrainerName());
            preparedStatement.setString(3, dtoVideoCourse.getLink());
            preparedStatement.setString(4, dtoVideoCourse.getRemark());
            preparedStatement.executeUpdate();
            LOGGER.info("Update videocourse id = " + dtoVideoCourse.getId() + " name = " + dtoVideoCourse.getCourseName());
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }

    }

    public ObservableList<VideoCourse> searchVideoCourse(String searchConfig, String search) {
        ObservableList<VideoCourse> videoCourseList = FXCollections.observableArrayList();
        PreparedStatement preparedStatement;
        Connection connection;

        try {
            Class.forName(R.DB.DB_DRIVER);
            connection = dbHelper.getConnection();
            switch (searchConfig) {
                case "id":
                case "ID":
                    preparedStatement = connection
                            .prepareStatement("SELECT * FROM video_course WHERE videoID=?");
                    preparedStatement.setString(1, search);
                    LOGGER.info("Search videocourse by id= " + search);
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
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        return videoCourseList;
    }

    public ObservableList<VideoCourse> getAllVideoCourse() {
        ObservableList<VideoCourse> videoCourseList = FXCollections.observableArrayList();
        PreparedStatement preparedStatement;
        Connection connection;

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
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }
        return videoCourseList;
    }

    private VideoCourse createVideoCourseFromRow(ResultSet rs) {
        VideoCourse dtoVideoCourse = new VideoCourse();

        try {
            dtoVideoCourse.setId(rs.getInt("videoID"));
            dtoVideoCourse.setCourseName(rs.getString("courseName"));
            dtoVideoCourse.setTrainerName(rs.getString("trainerName"));
            dtoVideoCourse.setLink(rs.getString("link"));
            dtoVideoCourse.setRemark(rs.getString("remark"));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }

        return dtoVideoCourse;
    }

    private PhysicalCourse createPhysicalCourseFromRow(ResultSet rs) {
        PhysicalCourse dtoCourse = new PhysicalCourse();

        try {
            dtoCourse.setId(rs.getInt("course_id"));
            dtoCourse.setCourseName(rs.getString("course_name"));
            dtoCourse.setTrainerName(rs.getString("trainer_name"));
            dtoCourse.setStartTime(rs.getTime("start"));
            dtoCourse.setEndTime(rs.getTime("end"));
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
        }

        return dtoCourse;
    }

}
