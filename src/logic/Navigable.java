package logic;

import com.sun.istack.internal.NotNull;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;

public interface Navigable {
    /**
     * <b>Navigate between windows</b>
     * <br>
     * Any class that implements or interface that extended the interface Navigable will do not have to implement the method goToScene,
     * <br>
     * this is <u>already implemented</u>, but asking for 2 param
     *
     * @param actionEvent Requested Action Event from the current window
     * @param path        Requested path to the required page
     * @since JDK 1.8 because of <code>default</code> method
     */

    /**
     *  Changes the screen and loads selected view
     * @param actionEvent Button Click of a view which should be loaded
     * @param path of view which should be loaded
     */
    default void goToScene(ActionEvent actionEvent, String path) {
        try {
            @NotNull Parent home_page_parent = FXMLLoader.load(getClass().getClassLoader().getResource(path));
            Scene home_page_scene = new Scene(home_page_parent);
            Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            app_stage.setScene(home_page_scene);
            app_stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setResources(ResourceBundle resourceBundle);

}
