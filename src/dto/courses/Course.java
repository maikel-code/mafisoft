package dto.courses;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Course {
    protected IntegerProperty id;
    StringProperty trainerName;
    StringProperty courseName;


    public Course() {
        this.id = new SimpleIntegerProperty();
        this.courseName = new SimpleStringProperty();
        this.trainerName = new SimpleStringProperty();
    }

    public int getId() {
        return id.get();
    }

    public Course setId(int id) {
        this.id.set(id);
        return this;
    }

    public String getTrainerName() {
        return trainerName.get();
    }

    public Course setTrainerName(String trainerName) {
        this.trainerName.set(trainerName);
        return this;
    }

    public String getCourseName() {
        return courseName.get();
    }

    public Course setCourseName(String courseName) {
        this.courseName.set(courseName);
        return this;
    }

}
