package logic.logicInterface;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public interface ChangeCustomerData_I extends Changed {

    void changeButtonPressed(ActionEvent actionEvent);

    void mouseOnClick(MouseEvent mouseEvent);

}
