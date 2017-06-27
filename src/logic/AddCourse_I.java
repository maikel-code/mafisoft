package logic;

import javafx.event.ActionEvent;

public interface AddCourse_I extends Navigable {
    boolean check(int tab);

    void addButtonPressed(ActionEvent actionEvent);
}
