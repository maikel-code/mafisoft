package dto.customer;

import javafx.beans.property.*;

import java.sql.Date;

public class Customer {
    private IntegerProperty id;
    private StringProperty firstname;
    private StringProperty lastname;
    private SimpleObjectProperty<Date> birthday;
    private StringProperty mail;
    private StringProperty mobilephone;
    private IntegerProperty zipCode;
    private StringProperty city;
    private StringProperty street;
    private SimpleObjectProperty<Date> endDate;

    public Customer() {
        this.id = new SimpleIntegerProperty();
        this.firstname = new SimpleStringProperty();
        this.lastname = new SimpleStringProperty();
        this.birthday = new SimpleObjectProperty<>();
        this.mail = new SimpleStringProperty();
        this.mobilephone = new SimpleStringProperty();
        this.zipCode = new SimpleIntegerProperty();
        this.city = new SimpleStringProperty();
        this.street = new SimpleStringProperty();
        this.endDate = new SimpleObjectProperty<>();

    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getFirstname() {
        return firstname.get();
    }

    public void setFirstname(String firstname) {
        this.firstname.set(firstname);
    }

    public StringProperty firstnameProperty() {
        return firstname;
    }

    public String getLastname() {
        return lastname.get();
    }

    public void setLastname(String lastname) {
        this.lastname.set(lastname);
    }

    public StringProperty lastnameProperty() {
        return lastname;
    }

    public String getMail() {
        return mail.get();
    }

    public void setMail(String mail) {
        this.mail.set(mail);
    }

    public StringProperty mailProperty() {
        return mail;
    }

    public Date getBirthday() {
        return birthday.get();
    }

    public void setBirthday(Date birthday) {
        this.birthday.set(birthday);
    }

    public Date getEndDate() {
        return endDate.get();
    }

    public void setEndDate(Date date) {
        this.endDate.set(date);
    }

    public SimpleObjectProperty<Date> birthdayProperty() {
        return birthday;
    }

    public String getMobilephone() {
        return mobilephone.get();
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone.set(mobilephone);
    }

    public StringProperty mobilephoneProperty() {
        return mobilephone;
    }

    public int getZipCode() {
        return zipCode.get();
    }

    public void setZipCode(int zipCode) {
        this.zipCode.set(zipCode);
    }

    public IntegerProperty zipCodeProperty() {
        return zipCode;
    }

    public String getCity() {
        return city.get();
    }

    public void setCity(String city) {
        this.city.set(city);
    }

    public StringProperty cityProperty() {
        return city;
    }

    public String getStreet() {
        return street.get();
    }

    public void setStreet(String street) {
        this.street.set(street);
    }

    public StringProperty streetProperty() {
        return street;
    }

}
