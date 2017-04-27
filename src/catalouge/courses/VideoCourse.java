package catalouge.courses;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class VideoCourse {
    private IntegerProperty videoID;
    private StringProperty vCourseName;
    private StringProperty vTrainerName;
    private StringProperty vLink;
    private StringProperty vRemark;

    public VideoCourse() {
        this.vCourseName = new SimpleStringProperty();
        this.vTrainerName = new SimpleStringProperty();
        this.vLink = new SimpleStringProperty();
        this.vRemark = new SimpleStringProperty();
        this.videoID = new SimpleIntegerProperty();
    }

    public String getvCourseName() {
        return vCourseName.get();
    }

    public StringProperty vCourseNameProperty() {
        return vCourseName;
    }

    public void setvCourseName(String vCourseName) {
        this.vCourseName.set(vCourseName);
    }

    public String getvTrainerName() {
        return vTrainerName.get();
    }

    public StringProperty vTrainerNameProperty() {
        return vTrainerName;
    }

    public void setvTrainerName(String vTrainerName) {
        this.vTrainerName.set(vTrainerName);
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

    public int getVideoID() {
        return videoID.get();
    }

    public IntegerProperty videoIDProperty() {
        return videoID;
    }

    public void setVideoId(int videoID) {
        this.videoID.set(videoID);
    }
}
