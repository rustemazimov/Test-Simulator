package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import models.AnswerBank;
import models.Meta;
import models.QuestionBank;

public class ResultPageController extends Controller{

    @FXML private Label usernameLabel, rightAnswerCountLabel,
                        wrongAnswerCountLabel, unansweredAnswerCountLabel;

    @FXML private void initialize() {
        Meta metaData = Meta.getInstance();

        usernameLabel.setText(metaData.getUsername());
        rightAnswerCountLabel.setText(metaData.getRightAnswerCount() + " right");
        wrongAnswerCountLabel.setText(metaData.getWrongAnswerCount() + " wrong");
        unansweredAnswerCountLabel.setText(metaData.getUnansweredAnswerCount() + " unanswered");

    }

    private void evaluate() {
        AnswerBank answerBank = AnswerBank.getInstance();

        for (int i = 0; i < Meta.getInstance().getQuestionCount(); i++) {
//            char rightVariant
        }
    }
}
