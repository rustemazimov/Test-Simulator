package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Meta;
import models.Utils;
import models.questionIO.QuestionReader;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;

public class HomePageController extends Controller{

    @FXML private TextField filePathLabel;

    @FXML private Spinner<Integer> questionCountSpinner;

    @FXML private Spinner<Integer> variantCountSpinner;

    @FXML private TextField usernameTextField;

    @FXML private PasswordField passwordField;

    @FXML private void initialize() {
        ObservableList<Integer> numList = FXCollections.observableArrayList();
        for (int i = 1; i < 125; i++) {
            numList.add(i);
        }
        questionCountSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(numList));
        variantCountSpinner.setValueFactory(new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(FXCollections.observableArrayList(1, 2, 3, 4, 5)));
    }

    @FXML private void handleShowExplorerAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Microsoft Word", "*.docx"));
        File file = fileChooser.showOpenDialog(getStage());
        if (file != null)
        {
            filePathLabel.setText(file.getAbsolutePath());
            filePathLabel.setVisible(true);
        }
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
            filePathLabel.setVisible(true);
            System.out.println(files.get(0).getAbsolutePath());
            filePathLabel.setText(files.get(0).getAbsolutePath());
        }
        event.consume();
    }

    @FXML private void handleStartExamAction() {

        SecureRandom random = new SecureRandom();
        String[] methodTypes = new String[] {
                "MD5",
                "SHA1",
                "SHA256",
                "SHA512",
                "BCRYPT",
                "PBKDF2"
        };

        String[] hashMethods = new String[random.nextInt(1_000_000_000) % 10];
        for (int i = 0; i < hashMethods.length; i++) {
            hashMethods[i] = methodTypes[random.nextInt(1_000_000_000) % methodTypes.length];
        }

        Meta metaData = Meta.getInstance();

        metaData.setHashMethods(hashMethods);
        String fileName = this.filePathLabel.getText();
        if (fileName.isEmpty())
        {
            Utils.showAlertDialog(
                                "Information",
                        "File wasn't selected",
                    "A text, word or pdf file should be selected for taking questions from it.");
            return;
        }
        String
                username = usernameTextField.getText(),
                password = passwordField.getText();
        boolean
                isUsernameEmpty = username.isEmpty();
        if (isUsernameEmpty || password.isEmpty())
        {
            Utils.showAlertDialog(
                    "Information",
                    String.format("%s mustn't be empty", isUsernameEmpty ? "Username" : "Password"),
                        "Username and Password must be specified in order to ...");
            return;
        }
        if (!Utils.PasswordStrenghtChecker.isPasswordStrong(password))
        {
            Utils.showAlertDialog(
                    "Information",
                    "Password isn't strong",
                    "Password must have at least one <digit>, <upper-case letter>, <lower-case letter>"
                            + "and must be at least in the length of <10>"
            );
            return;
        }
        metaData.setFileName(fileName);
        metaData.setQuestionCount(questionCountSpinner.getValue());
        metaData.setUsername(usernameTextField.getText());
        metaData.setPassword(models.encryption.Function.getInstance().hash(passwordField.getText()));
        metaData.setVariantCount(variantCountSpinner.getValue());

        try {
            new QuestionReader().readQuestions();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("views/ExamPage.fxml"));
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

    @FXML private void handleKeyPressedAction(KeyEvent event) {
        /*String txt = "<" + event.getText() + ">";
        System.out.println(txt.contains("<"));
        *//*
        if (event.getText().equals("\n"))
        {
            handleStartExamAction();
        }*/
    }

}
