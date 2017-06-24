package tests;

import dto.courses.PhysicalCourse;
import dto.courses.VideoCourse;
import dto.customer.Customer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import service.CourseService;
import service.CustomerService;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;


public class MafisoftTest {
    private Customer customer;
    private PhysicalCourse physicalCourse;
    private VideoCourse videoCourse;
    private CourseService courseService;
    private CustomerService customerService;

    public MafisoftTest() {
    }

    @Before
    public void initialize() {
        customer = new Customer();
        videoCourse = new VideoCourse();
        physicalCourse = new PhysicalCourse();
        courseService = new CourseService();
        customerService = new CustomerService();
    }

    @Test
    public void testAddCustomer() throws SQLException {

        customer.setFirstname("Max");
        customer.setLastname("Mustermann");
        customer.setBirthday(new Date(90, 12, 1));
        customer.setMail("max.m@gmail.com");
        customer.setMobilephone("+4901234567890");
        customer.setZipCode(12345);
        customer.setCity("Narnia");
        customer.setStreet("BestStreet 1");
        customer.setEndDate(new Date(118, 9, 1));

        String id = customerService.addCustomer(customer) + "";

        Customer testCustomer = customerService.searchCustomer("ID", id).get(0);

        Assert.assertEquals(customer.getMail(), testCustomer.getMail());
        Assert.assertEquals(customer.getZipCode(), testCustomer.getZipCode());
        Assert.assertEquals(customer.getFirstname(), testCustomer.getFirstname());
        Assert.assertEquals(customer.getLastname(), testCustomer.getLastname());
        Assert.assertEquals(new Date(90, 12, 1), testCustomer.getBirthday());
    }

    @Test
    public void testAddPhysicalCourse() throws SQLException {
        physicalCourse.setCourseName("Best training");
        physicalCourse.setTrainerName("John Cena");
        physicalCourse.setStartTime(new Time(14, 0, 0));
        physicalCourse.setEndTime(new Time(16, 0, 0));

        String id = courseService.addPhysicalCourse(physicalCourse) + "";

        PhysicalCourse testCourse = courseService.searchPhysicalCourse("id", id).get(0);

        Assert.assertEquals(physicalCourse.getCourseName(), testCourse.getCourseName());
        Assert.assertEquals(physicalCourse.getTrainerName(), testCourse.getTrainerName());
        Assert.assertEquals(physicalCourse.getStartTime(), testCourse.getStartTime());
    }

    @Test
    public void testAddVideoCourse() throws SQLException {
        videoCourse.setCourseName("Best video training");
        videoCourse.setTrainerName("Undertaker");
        videoCourse.setLink("youtube.com");
        videoCourse.setRemark("The best video course ever\n" + "Undertaker as most popular WWE master");

        String id = courseService.addVideoCourse(videoCourse) + "";

        VideoCourse testCourse = courseService.searchVideoCourse("id", id).get(0);

        Assert.assertEquals(videoCourse.getCourseName(), testCourse.getCourseName());
        Assert.assertEquals(videoCourse.getTrainerName(), testCourse.getTrainerName());
        Assert.assertEquals(videoCourse.getLink(), testCourse.getLink());
        Assert.assertEquals(videoCourse.getRemark(), testCourse.getRemark());
    }

    @Test
    public void testRemovePCourseFromCustomer() throws SQLException {
        customer = customerService.getAllCustomer().get(0);
        physicalCourse = courseService.getAllCourse().get(0);
        courseService.addCourseToCustomer(customer, physicalCourse);

        int size = courseService.getAllCourseByCustomer(customer).size();

        Assert.assertNotNull(size);

        courseService.removeCourse(customer, physicalCourse);

        int sizeAfterRemove = courseService.getAllCourseByCustomer(customer).size();

        Assert.assertNotEquals(size, sizeAfterRemove);

    }
}
