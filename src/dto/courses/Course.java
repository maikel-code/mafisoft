package dto.courses;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Course {
    protected IntegerProperty id;
    protected StringProperty trainerName;
    protected StringProperty courseName;


    public Course() {
        this.id = new SimpleIntegerProperty();
        this.courseName = new SimpleStringProperty();
        this.trainerName = new SimpleStringProperty();
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

    public String getTrainerName() {
        return trainerName.get();
    }

    public void setTrainerName(String trainerName) {
        this.trainerName.set(trainerName);
    }

    public StringProperty trainerNameProperty() {
        return trainerName;
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


}
