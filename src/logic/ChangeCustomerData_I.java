package logic;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public interface ChangeCustomerData_I extends Navigable {

    void changeButtonPressed(ActionEvent actionEvent);

    void mouseOnClick(MouseEvent mouseEvent);

    void fillTable();

    void fillEditingFormular(Object o);

    void cleanAll();

    void searchButton();

    void deleteSearchButton();

    boolean check();

    void addButtonPressed(ActionEvent actionEvent);
}
