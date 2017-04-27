package catalouge.courses;


import javafx.beans.property.*;

import java.sql.Time;

public class Course {
    private IntegerProperty course_id;
    private StringProperty trainer_name;
    private StringProperty course_name;
    private SimpleObjectProperty<Time> startTime;
    private SimpleObjectProperty<Time> endTime;
    private IntegerProperty customerQuantity;

    public Course() {
        this.course_id = new SimpleIntegerProperty();
        this.course_name = new SimpleStringProperty();
        this.trainer_name = new SimpleStringProperty();
        this.startTime = new SimpleObjectProperty<>();
        this.endTime = new SimpleObjectProperty<>();
        this.customerQuantity = new SimpleIntegerProperty();
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

    public Time getStartTime() {
        return startTime.get();
    }

    public SimpleObjectProperty<Time> startTimeProperty() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime.set(startTime);
    }

    public Time getEndTime() {
        return endTime.get();
    }

    public SimpleObjectProperty<Time> endTimeProperty() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime.set(endTime);
    }

    public int getCustomerQuantity() {
        return customerQuantity.get();
    }

    public IntegerProperty customerQuantityProperty() {
        return customerQuantity;
    }

    public void setCustomerQuantity(int customerQuantity) {
        this.customerQuantity.set(customerQuantity);
    }

}
