package controllers;

import com.sun.scenario.animation.shared.TimerReceiver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Meta;
import models.Utils;

import java.io.IOException;
import java.util.TimerTask;

public class LoginPageController extends Controller{

    private Stage ownStage;
    @FXML private TextField usernameTextField, passwordTextField;

    @FXML private void handleEvaluateAction() {
        Meta metaData = Meta.getInstance();
        System.out.printf("Username: %s\n", usernameTextField.getText());
        System.out.printf("Password: %s\n", passwordTextField.getText());
        System.out.printf("Encrypted password: %s\n", models.encryption.Function.getInstance().hash(passwordTextField.getText()));
        System.out.printf("Saved Username: %s\n", metaData.getUsername());
        System.out.printf("Saved password: %s\n", metaData.getPassword());
        if (
                metaData.getUsername().equals(usernameTextField.getText())
                        &&
                metaData.getPassword().equals(models.encryption.Function.getInstance().hash(passwordTextField.getText()))
           )
        {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getClassLoader().getResource("views/ResultPage.fxml"));
                AnchorPane pane = (AnchorPane) loader.load();
                Controller controller = loader.getController();
                controller.setStage(getStage());
                Scene scene = new Scene(pane);
                getStage().setWidth(pane.getPrefWidth());
                getStage().setHeight(pane.getPrefHeight());
                getStage().setScene(scene);
                getOwnStage().close();
            } catch (IOException e) {

            }
        }
        else
        {
            Utils.showAlertDialog(
                    "Information",
                    "Wrong!",
                    "Username or Password or both aren't correct"
            );
        }
    }

    public Stage getOwnStage() {
        return ownStage;
    }

    public void setOwnStage(Stage ownStage) {
        this.ownStage = ownStage;
    }
}
