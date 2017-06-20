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
        customer.setEndDate(new Date(118, 9, 1));

        String id = dbHelper.addCustomer(customer) + "";

        Customer testCustomer = dbHelper.searchCustomer("ID", id).get(0);

        Assert.assertEquals(customer.getMail(), testCustomer.getMail());
        Assert.assertEquals(customer.getZipCode(), testCustomer.getZipCode());
        Assert.assertEquals(customer.getCustomer_firstname(), testCustomer.getCustomer_firstname());
        Assert.assertEquals(customer.getCustomer_lastname(), testCustomer.getCustomer_lastname());
        Assert.assertEquals(new Date(90, 12, 1), testCustomer.getBirthday());
    }

    @Test
    public void testAddPhysicalCourse() throws SQLException, ClassNotFoundException {
        physicalCourse.setCourse_name("Best training");
        physicalCourse.setTrainer_name("John Cena");
        physicalCourse.setStartTime(new Time(14, 0, 0));
        physicalCourse.setEndTime(new Time(16, 0, 0));

        String id = dbHelper.addPhysicalCourse(physicalCourse) + "";

        PhysicalCourse testCourse = dbHelper.searchPhysicalCourse("id", id).get(0);

        Assert.assertEquals(physicalCourse.getCourse_name(), testCourse.getCourse_name());
        Assert.assertEquals(physicalCourse.getTrainer_name(), testCourse.getTrainer_name());
        Assert.assertEquals(new Time(14, 0, 0), testCourse.getStartTime());
    }

    @Test
    public void testAddVideoCourse() throws SQLException, ClassNotFoundException {
        videoCourse.setCourse_name("Best video training");
        videoCourse.setTrainer_name("Undertaker");
        videoCourse.setvLink("youtube.com");
        videoCourse.setvRemark("The best video course ever\n" + "Undertaker as most popular WWE master");

        String id = dbHelper.addVideoCourse(videoCourse) + "";

        VideoCourse testCourse = dbHelper.searchVideoCourse("id", id).get(0);

        Assert.assertEquals(videoCourse.getCourse_name(), testCourse.getCourse_name());
        Assert.assertEquals(videoCourse.getTrainer_name(), testCourse.getTrainer_name());
        Assert.assertEquals(videoCourse.getvLink(), testCourse.getvLink());
        Assert.assertEquals(videoCourse.getvRemark(), testCourse.getvRemark());
    }

    @Test
    public void testRemovePCourseFromCustomer() throws SQLException, ClassNotFoundException {
        customer = dbHelper.getAllCustomer().get(0);
        physicalCourse = dbHelper.getAllCourse().get(0);
        dbHelper.addCourseToCustomer(customer, physicalCourse);

        int size = dbHelper.getAllCourseByCustomer(customer).size();

        Assert.assertNotNull(size);

        dbHelper.removeCourse(customer, physicalCourse);

        int sizeAfterRemove = dbHelper.getAllCourseByCustomer(customer).size();

        Assert.assertNotEquals(size, sizeAfterRemove);

    }
}
