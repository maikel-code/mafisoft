package logicInterface;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public interface MainWindow {
	default void buttonPressed(ActionEvent actionEvent, String path) {
		try {
			Parent home_page_parent = FXMLLoader.load(getClass().getClassLoader().getResource(path));
			Scene home_page_scene = new Scene(home_page_parent);
			Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
			app_stage.setResizable(false);
			app_stage.setScene(home_page_scene);
			app_stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}