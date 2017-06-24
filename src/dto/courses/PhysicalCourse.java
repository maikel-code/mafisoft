package dto.courses;

import javafx.beans.property.*;

import java.sql.Time;

public class PhysicalCourse extends Course {
    private SimpleObjectProperty<Time> startTime;
    private SimpleObjectProperty<Time> endTime;

    public PhysicalCourse() {
        this.id = new SimpleIntegerProperty();
        this.courseName = new SimpleStringProperty();
        this.trainerName = new SimpleStringProperty();
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

