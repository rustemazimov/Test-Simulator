package controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.*;
import models.export.ResultExporter;

import java.io.File;
import java.io.IOException;

public class ResultPageController extends Controller{

    @FXML private Label usernameLabel, rightAnswerCountLabel,
                        wrongAnswerCountLabel, unansweredAnswerCountLabel;

    @FXML private Menu exportMenu;

    @FXML private void initialize() {
        evaluate();
        Meta metaData = Meta.getInstance();
        usernameLabel.setText(metaData.getUsername());
        rightAnswerCountLabel.setText(metaData.getRightAnswerCount() + " right");
        wrongAnswerCountLabel.setText(metaData.getWrongAnswerCount() + " wrong");
        unansweredAnswerCountLabel.setText(metaData.getUnansweredAnswerCount() + " unanswered");
        prepareExportMenu();
    }

    private void prepareExportMenu() {
        ObservableList<MenuItem> exportMenuItems = this.exportMenu.getItems();
        for (MenuItem exportMenuItem : exportMenuItems) {
            String menuItemText = exportMenuItem.getText();
            exportMenuItem.setOnAction(event -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save File Dialog");
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(findAppName(menuItemText), findFileExtension(menuItemText)));
                File file = fileChooser.showSaveDialog(getStage());
                if (file == null)
                {
                    Utils.showAlertDialog("Information", "File not selected", "You should specify file to export results");
                }
                else
                {
                    new ResultExporter().export(menuItemText);
                }
            });
        }
    }
    private String findFileExtension(String itemName) {
        switch (itemName) {
            case "PDF":
                return "*.pdf";
            case "Word":
                return "*.docx";
            case "Excel":
                return "*.xlsx";
            case "Txt":
                return "*.txt";
        }
        return null;
    }
    private String findAppName(String itemName) {
        switch (itemName) {
            case "PDF":
                return "Portable Digital Format";
            case "Word":
                return "Microsoft Word";
            case "Excel":
                return "Microsoft Excel";
            case "Txt":
                return "Microsoft Windows Notepad";
        }
        return null;
    }

    @FXML private void handleLookThroughSimulatorAction() throws IOException {
        Stage simulatorStage = new Stage();
        simulatorStage.initModality(Modality.APPLICATION_MODAL);
        simulatorStage.initOwner(getStage());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("views/ExamPage.fxml"));
        AnchorPane pane = (AnchorPane) loader.load();
        ExamPageController controller = loader.getController();
        controller.setStage(getStage());
        controller.setForOnlyLookThrough(true);
//        controller.setOwnStage(simulatorStage);
        Scene scene = new Scene(pane);
        simulatorStage.setScene(scene);
        simulatorStage.show();
    }

    /*===============================Helper=========================================================*/
    private void evaluate() {
        AnswerBank answerBank = AnswerBank.getInstance();
        Meta metaData = Meta.getInstance();
        for (int i = 0; i < metaData.getQuestionCount(); i++) {
            char
                    userVariant = answerBank.get(i, true),
                    realVariant = answerBank.get(i, false);

            if (userVariant == '\u0000')
            {
                continue;
            }
//                metaData.decrementUnansweredCount();
                boolean isRight;
                ExamResult examResult = ExamResult.getInstance();
                if (userVariant == realVariant)
                {
                    isRight = true;
                    examResult.put(i, '\u2705');
                }
                else
                {
                    isRight = false;
                    examResult.put(i, '\u2716');
                }
                metaData.incrementAnswerCount(isRight);

        }
    }
}
