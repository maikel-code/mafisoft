package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.EventObject;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private Label wrongConnection;
    @FXML
    private TextField logIn_txt;
    @FXML
    private PasswordField pw_txt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void onAction(EventObject actionEvent) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/mafisoftBD", "root", "mafisoft");
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Employee WHERE username=? AND password=?");
            preparedStatement.setString(1, logIn_txt.getText());
            preparedStatement.setString(2, pw_txt.getText());
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Parent home_page_parent = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
                Scene home_page_scene = new Scene(home_page_parent);
                Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                app_stage.hide();
                app_stage.setScene(home_page_scene);
                app_stage.show();
            } else {
                wrongConnection.setText("Invalid Username or Password");
            }
        } catch (ClassNotFoundException | IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            onAction(keyEvent);
        }
    }

    public void loginButtonHandler(ActionEvent actionEvent) {
        onAction(actionEvent);
    }

}
