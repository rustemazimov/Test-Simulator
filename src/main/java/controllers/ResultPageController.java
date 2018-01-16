package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.AnswerBank;
import models.Meta;
import models.QuestionBank;

import java.io.IOException;

public class ResultPageController extends Controller{

    @FXML private Label usernameLabel, rightAnswerCountLabel,
                        wrongAnswerCountLabel, unansweredAnswerCountLabel;

    @FXML private void initialize() {
        evaluate();
        Meta metaData = Meta.getInstance();
        usernameLabel.setText(metaData.getUsername());
        rightAnswerCountLabel.setText(metaData.getRightAnswerCount() + " right");
        wrongAnswerCountLabel.setText(metaData.getWrongAnswerCount() + " wrong");
        unansweredAnswerCountLabel.setText(metaData.getUnansweredAnswerCount() + " unanswered");

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
                    rightVariant = answerBank.get(i, true),
                    wrongVariant = answerBank.get(i, false);

            if (rightVariant == '\u0000')
            {
                continue;
            }
            else
            {
                metaData.decrementUnansweredCount();
                boolean isRight;
                if (rightVariant == wrongVariant)
                {
                    isRight = true;
                }
                else
                {
                    isRight = false;
                }
                metaData.incrementAnswerCount(isRight);
            }
        }
    }
}
