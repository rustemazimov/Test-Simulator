package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import models.Meta;
import models.Question;
import models.QuestionBank;

import java.io.IOException;
import java.util.Optional;

public class ExamPageController extends Controller{

    public static final ObservableList answers = FXCollections.observableArrayList();

    private int questionId;
    private ToggleGroup group = new ToggleGroup();

    @FXML private ListView<String> questionListView;

    @FXML private TextArea questionTextArea;

    @FXML private ListView<RadioListCell> answerListView;

    @FXML private Button startButton;

    @FXML private Button pauseContinueButton;

    @FXML private Button previousButton;

    @FXML private Button nextButton;


    @FXML private void initialize() {
        prepareQuestionListView();
        loadQuestionListView();
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
        if (index == Meta.getInstance().getChosenQuestionCount() - 1)
        {
            nextButton.setVisible(false);
        }
        else
        {
            nextButton.setVisible(true);
        }

        questionId = index;
        Question question = QuestionBank.getInstance().get(index);

        questionTextArea.setText(question.getQuestionTxt());

        answers.clear();
        answers.addAll(question.getAnswers());
    }

    private void loadQuestionListView() {

    }

    @FXML private void handleStartAction() {
        startButton.setVisible(false);
        loadQuestion(0);
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

    @FXML private void handlePreviousAction() {
        loadQuestion(questionId - 1);
    }

    @FXML private void handleNextAction() {
        loadQuestion(questionId + 1);
    }

    @FXML private void handleAnswerGivenAction() {

    }

    private void prepareQuestionListView() {
        //answerListView.setPrefSize(200, 250);
        answerListView.setEditable(true);

        answers.addAll(
                "Adam", "Alex", "Alfred", "Albert",
                "Brenda", "Connie", "Derek", "Donny",
                "Lynne", "Myrtle", "Rose", "Rudolph",
                "Tony", "Trudy", "Williams", "Zach"
        );

        answerListView.setItems(answers);
        answerListView.setCellFactory(new Callback<ListView<RadioListCell>, ListCell<RadioListCell>>() {
            public ListCell<RadioListCell> call(ListView<RadioListCell> param) {
                return null;
            }
        });

    }
    private void setAnswerable(boolean flag) {
        answerListView.setVisible(flag);
        previousButton.setVisible(flag);
        nextButton.setVisible(flag);
    }

    private class RadioListCell extends ListCell<String> {
        @Override
        public void updateItem(String obj, boolean empty) {
            super.updateItem(obj, empty);
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                RadioButton radioButton = new RadioButton(obj);
                radioButton.setToggleGroup(group);
                // Add Listeners if any
                setGraphic(radioButton);
            }
        }
    }
}
