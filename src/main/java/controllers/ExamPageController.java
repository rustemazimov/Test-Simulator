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
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import models.*;

import java.io.IOException;
import java.util.Optional;

public class ExamPageController extends Controller{

    private int questionId;

    private RadioButton[] answerRadioButtons;

    private boolean isForOnlyLookThrough;
    @FXML private VBox answersVBox;

    @FXML private ListView<String> questionListView;

    @FXML private TextArea questionTextArea;

    @FXML private Button startButton;

    @FXML private Button pauseContinueButton;

    @FXML private Button previousButton;

    @FXML private Button nextButton;

    @FXML private Button evaluateButton;

    private boolean isTheFirstTime = true;

    @FXML private void initialize() {
        pauseContinueButton.setDisable(true);
        evaluateButton.setDisable(true);
        previousButton.setDisable(true);
        nextButton.setDisable(true);
    }

    @FXML private void handleStartAction() {
        prepareQuestionListView();
        loadQuestionListView();
        prepareAnswersVBox();

        loadQuestion(0);

        questionListView.setDisable(false);
        answersVBox.setDisable(false);
        previousButton.setDisable(false);
        nextButton.setDisable(false);

        if (!isForOnlyLookThrough)
        {
            pauseContinueButton.setDisable(false);
            evaluateButton.setDisable(false);
        }
        startButton.setDisable(true);
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
            Optional<ButtonType> result = alertDialog.showAndWait();
            if (result.get() != ButtonType.OK){
                return;
            }
        }

        try {
            Stage loginStage = new Stage();
            loginStage.initModality(Modality.APPLICATION_MODAL);
            loginStage.initOwner(getStage());
            loginStage.setTitle("Sign in");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("views/LoginPage.fxml"));
            AnchorPane pane = (AnchorPane) loader.load();
            LoginPageController controller = loader.getController();
            controller.setStage(getStage());
            controller.setOwnStage(loginStage);
            Scene scene = new Scene(pane);
            loginStage.setScene(scene);
            loginStage.show();
        } catch (IOException e) {

        }
    }

    @FXML private void handlePreviousAction() {
        loadQuestion(questionId - 1);
    }

    @FXML private void handleNextAction() {
        loadQuestion(questionId + 1);
    }

    private void prepareQuestionListView() {
        questionListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int selectedIndex = questionListView.getSelectionModel().getSelectedIndex();
                if (selectedIndex == questionId)
                {
                    return;
                }
                loadQuestion(selectedIndex);
            }
        });
        questionListView.setDisable(true);
    }

    private void loadQuestion(int index) {
        resetAnswerRadioButtons();
        if (index == 0)
        {
            previousButton.setVisible(false);
        }
        else
        {
            previousButton.setVisible(true);
        }
        if (index == Meta.getInstance().getQuestionCount() - 1)
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

        char userAnswerForQuestion = AnswerBank.getInstance()
                .get(questionId, true);
        if (userAnswerForQuestion != '\u0000')
        {
            int
                    indexOfUserAnswer = Meta.getInstance().getIndexForVariant(userAnswerForQuestion),
                    indexOfRealAnswer = Meta.getInstance().getIndexForVariant(AnswerBank.getInstance().get(questionId, false));
            answerRadioButtons[indexOfUserAnswer].setSelected(true);
            if (isForOnlyLookThrough)
            {
                if (indexOfUserAnswer == indexOfRealAnswer)
                {
                    RadioButton userAnswerRadioButton = answerRadioButtons[indexOfUserAnswer];
                    userAnswerRadioButton.setStyle("-fx-background-color: Green;");
                    userAnswerRadioButton.setTooltip(new Tooltip("Correct answer"));
                }
                else
                {
                    RadioButton
                            userAnswerRadioButton = answerRadioButtons[indexOfUserAnswer],
                            realAnswerRadioButton = answerRadioButtons[indexOfRealAnswer];
                    userAnswerRadioButton.setStyle("-fx-background-color: Red;");
                    userAnswerRadioButton.setTooltip(new Tooltip("Your answer"));

                    realAnswerRadioButton.setStyle("-fx-background-color: Blue");
                    realAnswerRadioButton.setTooltip(new Tooltip("Correct answer"));
                }
            }
        }
        String[] answers = question.getAnswers();
        for (int i = 0; i < answerRadioButtons.length; i++) {
            answerRadioButtons[i].setText(answers[i]);
        }
    }

    private void loadQuestionListView() {
        QuestionBank questionBank = QuestionBank.getInstance();
        for (int i = 0; i < questionBank.size(); i++) {
            questionListView.getItems().add("Question - " + (questionBank.get(i).getId() + 1) + " "
                        + (isForOnlyLookThrough ? ExamResult.getInstance().get(i) : '\u25CE' ));
        }
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
            if (!isForOnlyLookThrough)
            {
                answerRadioButton.setOnAction(event -> {
                    ObservableList<String> questionListViewItems = questionListView.getItems();
                    String olderQuestionItem = questionListViewItems.get(questionId);
                    final char ANSWERED_SIGN = '\u25C9';
                    boolean isLastCharPlusOfQuestionOnListView = olderQuestionItem.charAt(olderQuestionItem.length() - 1) == ANSWERED_SIGN;
                    if (finalAnswerRadioButton.isSelected())
                    {
                        if (!isLastCharPlusOfQuestionOnListView)
                        {
                            String questionTitleOnQuestionListView = questionListViewItems.get(questionId);
                            questionListViewItems.set(questionId, questionTitleOnQuestionListView.substring(0, questionTitleOnQuestionListView.length() - 2) + " " + ANSWERED_SIGN);
                        }
                        for (int j = 0; j < answerRadioButtons.length; j++) {
                            if (finalI != j)
                            {
                                answerRadioButtons[j].setSelected(false);
                            }
                        }
                        String radioButtonText = finalAnswerRadioButton.getText();
                        if (radioButtonText.length() >= 1)
                        {
                            AnswerBank.getInstance().set(questionId, radioButtonText.charAt(0), true);
                            Meta.getInstance().decrementUnansweredCount();
                        }
                    }
                    else
                    {
                        AnswerBank.getInstance().remove(questionId);
                        Meta.getInstance().incrementUnansweredCount();
                        if (isLastCharPlusOfQuestionOnListView)
                        {
                            questionListViewItems.set(questionId, olderQuestionItem.substring(0, olderQuestionItem.indexOf(ANSWERED_SIGN) - 1) + " " + '\u25CE');
                        }
                    }
                });
            }
            else
            {
                answerRadioButton.setDisable(true);
            }
        }




    }
    private void setAnswerable(boolean flag) {
        answersVBox.setDisable(!flag);
        previousButton.setDisable(!flag);
        nextButton.setDisable(!flag);
        questionListView.setDisable(!flag);
    }

    private void resetAnswerRadioButtons() {
        for (int i = 0; i < answerRadioButtons.length; i++) {
            RadioButton answerRadioButton = answerRadioButtons[i];
            answerRadioButton.setSelected(false);
            answerRadioButton.setStyle("");
        }
    }

    public void setForOnlyLookThrough(boolean forOnlyLookThrough) {
//        startButton.setDisable(true);
        isForOnlyLookThrough = forOnlyLookThrough;
    }
}
