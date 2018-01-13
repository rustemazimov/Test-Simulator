package controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import models.AnswerBank;
import models.Meta;
import models.Question;
import models.QuestionBank;

import java.io.IOException;
import java.util.Optional;

public class ExamPageController extends Controller{

    private int questionId;

    private RadioButton[] answerRadioButtons;

    @FXML private VBox answersVBox;

    @FXML private ListView<String> questionListView;

    @FXML private TextArea questionTextArea;

    @FXML private Button startButton;

    @FXML private Button pauseContinueButton;

    @FXML private Button previousButton;

    @FXML private Button nextButton;

    @FXML private Button evaluateButton;


    @FXML private void initialize() {
        prepareQuestionListView();
        loadQuestionListView();
        prepareAnswersVBox();
        pauseContinueButton.setDisable(true);
        evaluateButton.setDisable(true);
        previousButton.setDisable(true);
        nextButton.setDisable(true);
    }

    private void prepareQuestionListView() {
        questionListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                loadQuestion(questionListView.getSelectionModel().getSelectedIndex());
            }
        });
        questionListView.setDisable(true);
    }

    private void loadQuestion(int index) {
        if (index == 0)
        {
            previousButton.setVisible(false);
        }
        else
        {
            previousButton.setVisible(true);
        }
        if (index == Meta.getInstance().getQuestionCountt() - 1)
        {
            nextButton.setVisible(false);
        }
        else
        {
            nextButton.setVisible(true);
        }

        questionId = index;
        Question question = QuestionBank.getInstance().get(questionId);

        questionTextArea.setText(question.getQuestionTxt());

        String[] answers = question.getAnswers();
        for (int i = 0; i < answerRadioButtons.length; i++) {
            answerRadioButtons[i].setText(answers[i]);
        }
    }

    private void loadQuestionListView() {
        QuestionBank questionBank = QuestionBank.getInstance();
        for (int i = 0; i < questionBank.size(); i++) {
            questionListView.getItems().add(questionBank.get(i).getId() + "");
        }
    }

    @FXML private void handleStartAction() {
        startButton.setDisable(true);
        loadQuestion(0);
        pauseContinueButton.setDisable(false);
        questionListView.setDisable(false);
        answersVBox.setDisable(false);
        previousButton.setDisable(false);
        nextButton.setDisable(false);
        evaluateButton.setDisable(false);
    }

    @FXML private void handlePauseContinueAction() {
        if (pauseContinueButton.getText().equals("Pause"))
        {
            pauseContinueButton.setText("Continue");
            setAnswerable(false);
        }
        else
        {
            pauseContinueButton.setText("Pause");
            setAnswerable(true);
        }
    }

    @FXML private void handleEvaluateAction() {
        int unansweredAnswerCount = Meta.getInstance().getUnansweredAnswerCount();

        if (unansweredAnswerCount > 0)
        {
            Alert alertDialog = new Alert(Alert.AlertType.CONFIRMATION);
            alertDialog.setTitle("Confirmation Dialog");
            alertDialog.setHeaderText(unansweredAnswerCount + " unanswered question"
                    + (unansweredAnswerCount > 1  ? "s were" : "was")
                    + "found!");
            alertDialog.setContentText("Do you want to continue?");
            alertDialog.show();
            Optional<ButtonType> result = alertDialog.showAndWait();
            if (result.get() != ButtonType.OK){
                return;
            }
        }

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("views/LoginPage.fxml"));
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

    @FXML private void handlePreviousAction() { loadQuestion(questionId - 1); }

    @FXML private void handleNextAction() {
        loadQuestion(questionId + 1);
    }


    private void prepareAnswersVBox() {
        answersVBox.setDisable(true);
        ObservableList<Node> answers = answersVBox.getChildren();
        answerRadioButtons = new RadioButton[Meta.getInstance().getVariantCount()];
        for (int i = 0; i < answerRadioButtons.length; i++) {
            answerRadioButtons[i] = new RadioButton();
            RadioButton answerRadioButton = answerRadioButtons[i];
            answerRadioButton.setPrefSize(answersVBox.getPrefWidth(), answersVBox.getPrefHeight() / answerRadioButtons.length);
            answers.add(answerRadioButton);
            RadioButton finalAnswerRadioButton = answerRadioButton;
            int finalI = i;
            answerRadioButton.setOnAction(event -> {
                if (finalAnswerRadioButton.isSelected())
                {
                    for (int j = 0; j < answerRadioButtons.length; j++) {
                        if (finalI != j)
                        {
                            answerRadioButtons[j].setSelected(false);
                        }
                    }
                    AnswerBank.getInstance().set(finalI, finalAnswerRadioButton.getText().charAt(0), false);
                }
            });
        }




    }
    private void setAnswerable(boolean flag) {
        answersVBox.setDisable(!flag);
        previousButton.setDisable(!flag);
        nextButton.setDisable(!flag);
        questionListView.setDisable(!flag);
    }
}
