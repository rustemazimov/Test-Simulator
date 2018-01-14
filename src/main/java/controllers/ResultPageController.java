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
        evaluate();
        Meta metaData = Meta.getInstance();
        usernameLabel.setText(metaData.getUsername());
        rightAnswerCountLabel.setText(metaData.getRightAnswerCount() + " right");
        wrongAnswerCountLabel.setText(metaData.getWrongAnswerCount() + " wrong");
        unansweredAnswerCountLabel.setText(metaData.getUnansweredAnswerCount() + " unanswered");

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
