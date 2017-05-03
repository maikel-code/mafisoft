package catalouge.customer;

import javafx.beans.property.*;

import java.sql.Date;

public class Customer {
        private IntegerProperty customerID;
        private StringProperty customer_firstname;
        private StringProperty customer_lastname;
        private SimpleObjectProperty<Date> birthday;
        private StringProperty mail;
        private StringProperty mobilephone;
        private IntegerProperty zipCode;
        private StringProperty city;
        private StringProperty street;

        public Customer() {
            this.customerID = new SimpleIntegerProperty();
            this.customer_firstname = new SimpleStringProperty();
            this.customer_lastname = new SimpleStringProperty();
            this.birthday = new SimpleObjectProperty<>();
            this.mail = new SimpleStringProperty();
            this.mobilephone = new SimpleStringProperty();
            this.zipCode = new SimpleIntegerProperty();
            this.city = new SimpleStringProperty();
            this.street = new SimpleStringProperty();

        }

        public int getCustomerID() {
            return customerID.get();
        }

        public IntegerProperty customerIDProperty() {
            return customerID;
        }

        public void setCustomerID(int customerID) {
            this.customerID.set(customerID);
        }

        public String getCustomer_firstname() {
            return customer_firstname.get();
        }

        public StringProperty customer_firstnameProperty() {
            return customer_firstname;
        }

        public void setCustomer_firstname(String customer_firstname) {
            this.customer_firstname.set(customer_firstname);
        }

        public String getCustomer_lastname() {
            return customer_lastname.get();
        }

        public StringProperty customer_lastnameProperty() {
            return customer_lastname;
        }

        public void setCustomer_lastname(String customer_lastname) {
            this.customer_lastname.set(customer_lastname);
        }

        public String getMail() {
            return mail.get();
        }

        public StringProperty mailProperty() {
            return mail;
        }

        public void setMail(String mail) {
            this.mail.set(mail);
        }

        public Date getBirthday() {
            return birthday.get();
        }

        public SimpleObjectProperty<Date> birthdayProperty() {
            return birthday;
        }

        public void setBirthday(Date birthday) {
            this.birthday.set(birthday);
        }

        public String getMobilephone() {
            return mobilephone.get();
        }

        public StringProperty mobilephoneProperty() {
            return mobilephone;
        }

        public void setMobilephone(String mobilephone) {
            this.mobilephone.set(mobilephone);
        }

        public int getZipCode() {
            return zipCode.get();
        }

        public IntegerProperty zipCodeProperty() {
            return zipCode;
        }

        public void setZipCode(int zipCode) {
            this.zipCode.set(zipCode);
        }

        public String getCity() {
            return city.get();
        }

        public StringProperty cityProperty() {
            return city;
        }

        public void setCity(String city) {
            this.city.set(city);
        }

        public String getStreet() {
            return street.get();
        }

        public StringProperty streetProperty() {
            return street;
        }

        public void setStreet(String street) {
            this.street.set(street);
        }

    }
