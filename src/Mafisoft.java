import config.R;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Mafisoft extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        checkLogs();
        launch(args);
    }

    /**
     * Starts application with main window enabled and visible
     *
      * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource(R.Pages.PATH_TO_MAIN_WINDOW));
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private static void checkLogs() {
        Path path = Paths.get("logs");
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}