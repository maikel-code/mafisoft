package logic;

import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import DBHelper.DBController;
import DTO.courses.Course;
import DTO.customer.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import logicInterface.Controller;

public class ChangeCustomerData implements Initializable, Controller {
	@FXML
	private TextField changeFirstName, changeLastName, changeMail, changePhonenummer, changeCity, changeStreet,
			customerTXT, customerID;
	@FXML
	private ComboBox<String> courses;
	@FXML
	private TextField changeZipcode;
	@FXML
	private TableView<Customer> customerTable;
	@FXML
	private TableView<Course> customerCourseTable;
	@FXML
	private TableColumn<Customer, Integer> id;
	@FXML
	private TableColumn<Customer, String> firstName;
	@FXML
	private TableColumn<Customer, String> lastName;
	@FXML
	private TableColumn<Customer, Date> birthday;
	@FXML
	private TableColumn<Customer, String> mail;
	@FXML
	private TableColumn<Customer, String> phonenummer;
	@FXML
	private TableColumn<Customer, String> address;
	@FXML
	private TableColumn<Course, String> customerCourseList;
	private ObservableList<Course> allCourses;
    private String pathToMainWindow = "gui/Homepage.fxml";

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		fillTale();
	}

	private void fillTale() {
		try {
			id.setCellValueFactory(new PropertyValueFactory<>("customerID"));
			firstName.setCellValueFactory(new PropertyValueFactory<>("customer_firstname"));
			lastName.setCellValueFactory(new PropertyValueFactory<>("customer_lastname"));
			birthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
			mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
			phonenummer.setCellValueFactory(new PropertyValueFactory<>("mobilephone"));
			address.setCellValueFactory(new PropertyValueFactory<>("zipCode"));
			address.setCellValueFactory(new PropertyValueFactory<>("city"));
			address.setCellValueFactory(new PropertyValueFactory<>("street"));
			customerTable.setItems(getCustomerList());

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private ObservableList<Course> getCourseList(int customer_id) throws SQLException, ClassNotFoundException {
		CourseList courseListController = new CourseList();

		ResultSet rs = DBController.getAllAvailabileCourse(customer_id);
		ObservableList<Course> row = FXCollections.observableArrayList();
		while (rs.next()) {
			row.add(courseListController.fillCourse(rs));
		}
		return row;
	}

	private ObservableList<Customer> getCustomerList() throws SQLException, ClassNotFoundException {
		ResultSet rs = DBController.getAllCustomer();
		ObservableList<Customer> row = FXCollections.observableArrayList();

		while (rs.next()) {
			row.add(fillCustomer(rs));
		}

		return row;
	}

	private void fillChangeTabble(Customer customer) {
		cleanAll();

		if (customer.getMail() != null || !customer.getMail().isEmpty())
			changeMail.setText(customer.getMail());

		if (customer.getMobilephone() != null || !customer.getMobilephone().isEmpty())
			changePhonenummer.setText(customer.getMobilephone());

		customerID.setText(customer.getCustomerID() + "");
		changeFirstName.setText(customer.getCustomer_firstname());
		changeLastName.setText(customer.getCustomer_lastname());
		changeZipcode.setText(customer.getZipCode() + "");
		changeCity.setText(customer.getCity());
		changeStreet.setText(customer.getStreet());

		try {
			allCourses = getCourseList(customer.getCustomerID());
			customerCourseTable.setItems(getAllCustomerCourse(customer));
			customerCourseList.setCellValueFactory(new PropertyValueFactory<>("course_name"));
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		courses.getItems().clear();

		for (Course oneCourse : allCourses) {
			courses.getItems().add(oneCourse.getCourse_name() + " ID:" + oneCourse.getCourse_id());
		}

	}

	public void mouseOnClick(MouseEvent mouseEvent) {
		if (mouseEvent.getClickCount() == 2) {
			fillChangeTabble(customerTable.getSelectionModel().getSelectedItem());
		}
	}

	public void changeButton() {
		// TODO Create Customer and send it to DB
		try {
			DBController.updateCustomer(Integer.parseInt(customerID.getText()), changeFirstName.getText(),
					changeLastName.getText(), changeMail.getText(), changePhonenummer.getText(),
					Integer.parseInt(changeZipcode.getText()), changeCity.getText(), changeStreet.getText());
			fillTale();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addCourse() {
		try {
			if (courses.getValue() == null) {
				return;
			}
			String[] parts = courses.getValue().split("ID:");
			DBController.addCourseToCustomer(Integer.parseInt(customerID.getText()), Integer.parseInt(parts[1]));
			fillChangeTabble(customerTable.getSelectionModel().getSelectedItem());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void searchButton() {
		ResultSet rs;

		String search = customerTXT.getText();
		try {
			if (search.matches("\\b\\d+\\b")) {
				rs = DBController.searchCustomer("ID", search);
			} else {
				rs = DBController.searchCustomer("name", search);
			}

			if (rs != null && rs.next()) {
				fillChangeTabble(fillCustomer(rs));
			}

		} catch (Exception e) {
			Alert alert = new Alert(Alert.AlertType.ERROR, "Kunde " + search + " nicht gefunden");
			alert.setHeaderText(null);
			alert.show();
		}
	}

	private Customer fillCustomer(ResultSet rs) throws SQLException {
		Customer customer = new Customer();

		customer.setCustomerID(rs.getInt("customer_id"));
		customer.setCustomer_firstname(rs.getString("customer_firstname"));
		customer.setCustomer_lastname(rs.getString("customer_lastname"));
		customer.setBirthday(rs.getDate("birthday"));
		customer.setMail(rs.getString("email"));
		customer.setMobilephone(rs.getString("mobilephone"));
		customer.setZipCode(rs.getInt("zipCode"));
		customer.setCity(rs.getString("city"));
		customer.setStreet(rs.getString("street"));

		return customer;
	}

	@FXML
	private void goToMainWindow(ActionEvent actionEvent) {
		goToMainWindow(actionEvent, pathToMainWindow);
	}

	private ObservableList<Course> getAllCustomerCourse(Customer customer) throws SQLException, ClassNotFoundException {
		ResultSet rs = DBController.getAllCourseByCustomer(customer.getCustomerID());
		ObservableList<Course> row = FXCollections.observableArrayList();
		CourseList courseListController = new CourseList();

		while (rs.next()) {
			row.add(courseListController.fillCourse(rs));
		}

		return row;
	}

	@FXML
	private void removeCourse() {
		if (customerCourseTable.getSelectionModel().getSelectedItem() != null) {
			int courseID = customerCourseTable.getSelectionModel().getSelectedItem().getCourse_id();
			try {
				DBController.removeCourse(customerID.getText(), courseID);
				fillChangeTabble(customerTable.getSelectionModel().getSelectedItem());
			} catch (SQLException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void cleanAll() {
		changeFirstName.clear();
		changeLastName.clear();
		changeMail.clear();
		changePhonenummer.clear();
		changeCity.clear();
		changeStreet.clear();
		customerTXT.clear();
		customerID.clear();
	}

	@Override
	public boolean check(int tab) {
		return false;
	}
}
