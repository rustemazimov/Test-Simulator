package controllers;

import com.sun.scenario.animation.shared.TimerReceiver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.Meta;
import models.Utils;

import java.io.IOException;
import java.util.TimerTask;

public class LoginPageController extends Controller{

    @FXML private TextField usernameTextField, passwordTextField;

    @FXML private void handleEvaluateAction() {
        Meta metaData = Meta.getInstance();

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

}
