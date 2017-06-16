package logic.logicInterface;


import javafx.event.ActionEvent;

public interface Appendable extends Navigable {
        boolean check(int tab);
        void addButtonPressed(ActionEvent actionEvent);
}
