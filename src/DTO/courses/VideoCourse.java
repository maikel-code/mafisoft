package DTO.courses;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VideoCourse extends Course {
    private StringProperty vLink;
    private StringProperty vRemark;

    public VideoCourse() {
        this.course_id = new SimpleIntegerProperty();
        this.course_name = new SimpleStringProperty();
        this.trainer_name = new SimpleStringProperty();
        this.vLink = new SimpleStringProperty();
        this.vRemark = new SimpleStringProperty();

    }

    public String getvLink() {
        return vLink.get();
    }

    public StringProperty vLinkProperty() {
        return vLink;
    }

    public void setvLink(String vLink) {
        this.vLink.set(vLink);
    }

    public String getvRemark() {
        return vRemark.get();
    }

    public StringProperty vRemarkProperty() {
        return vRemark;
    }

    public void setvRemark(String vRemark) {
        this.vRemark.set(vRemark);
    }

    public int getCourse_id() {
        return course_id.get();
    }

    public IntegerProperty course_idProperty() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id.set(course_id);
    }

    public String getCourse_name() {
        return course_name.get();
    }

    public StringProperty course_nameProperty() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name.set(course_name);
    }

    public String getTrainer_name() {
        return trainer_name.get();
    }

    public StringProperty trainer_nameProperty() {
        return trainer_name;
    }

    public void setTrainer_name(String trainer_name) {
        this.trainer_name.set(trainer_name);
    }

}
