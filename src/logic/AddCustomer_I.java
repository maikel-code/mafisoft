package logic;

import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;

public interface AddCustomer_I extends Navigable {
    void setCustomer();

    void cleanAll();

    void isChecked();

    void checkZipLength(KeyEvent keyEvent);

    boolean check();

    void addButtonPressed(ActionEvent actionEvent);
}
