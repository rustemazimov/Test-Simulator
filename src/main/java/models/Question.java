package models;

public class Question {
    private int id;
    private String questionTxt;
    private String[] answers = new String[Meta.getInstance().getVariantCount()];
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

    public String[] getAnswers() {
        return answers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

}
