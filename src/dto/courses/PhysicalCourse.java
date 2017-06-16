package dto.courses;

import javafx.beans.property.*;

import java.sql.Time;

public class PhysicalCourse extends Course {
    private SimpleObjectProperty<Time>              startTime;
    private SimpleObjectProperty<Time>              endTime;

    public PhysicalCourse() {
        this.id = new SimpleIntegerProperty();
        this.course_name = new SimpleStringProperty();
        this.trainer_name = new SimpleStringProperty();
        this.startTime = new SimpleObjectProperty<>();
        this.endTime = new SimpleObjectProperty<>();
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

    public String getCourse_name() {
        return course_name.get();
    }

    public void setCourse_name(String course_name) {
        this.course_name.set(course_name);
    }

    public StringProperty course_nameProperty() {
        return course_name;
    }

    public String getTrainer_name() {
        return trainer_name.get();
    }

    public void setTrainer_name(String trainer_name) {
        this.trainer_name.set(trainer_name);
    }

    public StringProperty trainer_nameProperty() {
        return trainer_name;
    }

    public Time getStartTime() {
        return startTime.get();
    }

    public void setStartTime(Time startTime) {
        this.startTime.set(startTime);
    }

    public SimpleObjectProperty<Time> startTimeProperty() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime.get();
    }

    public void setEndTime(Time endTime) {
        this.endTime.set(endTime);
    }

    public SimpleObjectProperty<Time> endTimeProperty() {
        return endTime;
    }


}

