package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;

import java.io.File;
import java.util.List;

public class HomePageController {

    @FXML private TextField filePathLabel;

    @FXML private Spinner<Integer> questionCountSpinner;

    @FXML private Spinner<Integer> variantCountSpinner;

    @FXML private TextField usernameTextField;

    @FXML private PasswordField passwordField;

    @FXML private void initialize() {

    }

    @FXML private void handleDragFileAction(DragEvent event) {
        Dragboard db = event.getDragboard();
        if (db.hasFiles())
        {
            List<File> files = db.getFiles();

            if (files.size() != 1)
            {
                filePathLabel.setVisible(false);
                filePathLabel.setText("");
                return;
            }

            filePathLabel.setText(files.get(0).getAbsolutePath());
        }
        event.consume();
    }

    @FXML private void handleStartExamAction(){

    }

}
