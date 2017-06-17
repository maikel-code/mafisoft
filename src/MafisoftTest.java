import DBHelper.DBHelper;
import dto.courses.PhysicalCourse;
import dto.courses.VideoCourse;
import dto.customer.Customer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;


public class MafisoftTest {
    private Customer customer;
    private PhysicalCourse physicalCourse;
    private VideoCourse videoCourse;
    private DBHelper dbHelper;

    public MafisoftTest() {
    }

    @Before
    public void initialize() {
        customer = new Customer();
        dbHelper = DBHelper.getInstance();
        videoCourse = new VideoCourse();
        physicalCourse = new PhysicalCourse();
    }

    @Test
    public void testAddCustomer() throws SQLException, ClassNotFoundException {

        customer.setCustomer_firstname("Max");
        customer.setCustomer_lastname("Mustermann");
        customer.setBirthday(new Date(90, 12, 1));
        customer.setMail("max.m@gmail.com");
        customer.setMobilephone("+4901234567890");
        customer.setZipCode(12345);
        customer.setCity("Narnia");
        customer.setStreet("BestStreet 1");

        String id = dbHelper.addCustomer(customer) + "";

        Customer testCustomer = dbHelper.searchCustomer("ID", id).get(0);

        Assert.assertSame(customer, testCustomer);

    }

    @Test
    public void testAddPhysicalCourse() throws SQLException, ClassNotFoundException {
        physicalCourse.setCourse_name("Best training");
        physicalCourse.setTrainer_name("John Cena");
        physicalCourse.setStartTime(new Time(14,0,0));
        physicalCourse.setEndTime(new Time(16,0,0));

    }
}
