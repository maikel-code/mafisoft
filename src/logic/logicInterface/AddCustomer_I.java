package logic.logicInterface;

import javafx.scene.input.KeyEvent;

public interface AddCustomer_I extends Appendable {
    void setCustomer();

    void cleanAll();

    void isChecked();

    void checkZipLength(KeyEvent keyEvent);
}
