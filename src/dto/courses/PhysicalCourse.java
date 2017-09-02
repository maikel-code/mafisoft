package dto.courses;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

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

    public PhysicalCourse setId(int id) {
        this.id.set(id);
        return this;
    }

    public String getCourseName() {
        return courseName.get();
    }

    public PhysicalCourse setCourseName(String courseName) {
        this.courseName.set(courseName);
        return this;
    }

    public String getTrainerName() {
        return trainerName.get();
    }

    public PhysicalCourse setTrainerName(String trainerName) {
        this.trainerName.set(trainerName);
        return this;
    }

    public Time getStartTime() {
        return startTime.get();
    }

    public PhysicalCourse setStartTime(Time startTime) {
        this.startTime.set(startTime);
        return this;
    }

    public Time getEndTime() {
        return endTime.get();
    }

    public PhysicalCourse setEndTime(Time endTime) {
        this.endTime.set(endTime);
        return this;
    }

}

