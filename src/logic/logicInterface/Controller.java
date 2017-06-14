package logic.logicInterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public interface Controller {

    default void goToMainWindow(ActionEvent actionEvent, String path) {
        try {
            Parent home_page_parent = FXMLLoader.load(getClass().getClassLoader().getResource(path));
            Scene home_page_scene = new Scene(home_page_parent);
            Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            app_stage.setScene(home_page_scene);
            app_stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void cleanAll();

    boolean check(int tab);

}
