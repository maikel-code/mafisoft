package logic.logicInterface;


import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public interface Changed extends Navigable, Appendable {
    void fillTable();
    void fillChangeTable(Object o);
    void cleanAll(int tab);
    void changeButtonPressed(ActionEvent actionEvent, int tab);
    void mouseOnClick(MouseEvent mouseEvent, int tab);
    void searchButton();
}
