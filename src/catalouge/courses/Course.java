package catalouge.courses;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Course {
    protected IntegerProperty course_id;
    protected StringProperty trainer_name;
    protected StringProperty course_name;


    public Course() {
        this.course_id = new SimpleIntegerProperty();
        this.course_name = new SimpleStringProperty();
        this.trainer_name = new SimpleStringProperty();
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

    public String getTrainer_name() {
        return trainer_name.get();
    }

    public StringProperty trainer_nameProperty() {
        return trainer_name;
    }

    public void setTrainer_name(String trainer_name) {
        this.trainer_name.set(trainer_name);
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



}
