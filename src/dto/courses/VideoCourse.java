package dto.courses;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VideoCourse extends Course {
    private StringProperty link;
    private StringProperty remark;

    public VideoCourse() {
        this.id = new SimpleIntegerProperty();
        this.courseName = new SimpleStringProperty();
        this.trainerName = new SimpleStringProperty();
        this.link = new SimpleStringProperty();
        this.remark = new SimpleStringProperty();

    }

    public String getLink() {
        return link.get();
    }

    public void setLink(String link) {
        this.link.set(link);
    }

    public StringProperty linkProperty() {
        return link;
    }

    public String getRemark() {
        return remark.get();
    }

    public void setRemark(String remark) {
        this.remark.set(remark);
    }

    public StringProperty remarkProperty() {
        return remark;
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

    public String getCourseName() {
        return courseName.get();
    }

    public void setCourseName(String courseName) {
        this.courseName.set(courseName);
    }

    public StringProperty courseNameProperty() {
        return courseName;
    }

    public String getTrainerName() {
        return trainerName.get();
    }

    public void setTrainerName(String trainerName) {
        this.trainerName.set(trainerName);
    }

    public StringProperty trainerNameProperty() {
        return trainerName;
    }

}
