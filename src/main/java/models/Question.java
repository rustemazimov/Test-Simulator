package models;

public class Question {
    private String questionTxt;
    private char rightAnswerVariant;

    public String getQuestionTxt() {
        return questionTxt;
    }

    public void setQuestionTxt(String questionTxt) {
        this.questionTxt = questionTxt;
    }

    public char getRightAnswerVariant() {
        return rightAnswerVariant;
    }

    public void setRightAnswerVariant(char rightAnswerVariant) {
        this.rightAnswerVariant = rightAnswerVariant;
    }
}
