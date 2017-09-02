package dto.courses;


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

    public VideoCourse setLink(String link) {
        this.link.set(link);
        return this;
    }

    public String getRemark() {
        return remark.get();
    }

    public void setRemark(String remark) {
        this.remark.set(remark);
    }

    public int getId() {
        return id.get();
    }

    public VideoCourse setId(int id) {
        this.id.set(id);
        return this;
    }

    public String getCourseName() {
        return courseName.get();
    }

    public VideoCourse setCourseName(String courseName) {
        this.courseName.set(courseName);
        return this;
    }

    public String getTrainerName() {
        return trainerName.get();
    }

    public VideoCourse setTrainerName(String trainerName) {
        this.trainerName.set(trainerName);
        return this;
    }

}
