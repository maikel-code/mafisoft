package logic.logicInterface;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public interface ChangeCustomerData_I extends Navigable, Appendable {

    void changeButtonPressed(ActionEvent actionEvent);

    void mouseOnClick(MouseEvent mouseEvent);

    void fillTable();
    void fillEditingFormular(Object o);
    void cleanAll(int tab);

    void searchButton();
}
