package logic;

import javafx.event.ActionEvent;

/**
 * Created by maikel on 18.06.17.
 */

public interface AddCourse_I extends Navigable {
    boolean check(int tab);
    void addButtonPressed(ActionEvent actionEvent);
}
