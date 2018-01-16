package models.questionIO;

import models.AnswerBank;
import models.Meta;
import models.Question;
import models.QuestionBank;

import java.io.IOException;

public class QuestionReader {
    private int questonId = 0;
    public void readQuestions() throws IOException {
        String txt = new StringReader().readString();
        for (int i = 0, j = 1; i < txt.length(); i++, j++) {
            int
                beginIndex = txt.indexOf(j + ")"),
                endIndex = txt.indexOf(j + 1 + ")");
            if (beginIndex == -1)
            {
                break;
            }
            if (endIndex == -1)
            {
                loadQuestion(txt.substring(beginIndex, txt.length()));
                continue;
            }
            loadQuestion(txt.substring(beginIndex, endIndex));
        }
    }

    private void loadQuestion(String txt) {
        Question question = new Question();
        question.setId(this.questonId);
        char[] variantLetters = Meta.getInstance().getVariantLetters();
        question.setQuestionTxt(txt.substring(0, txt.indexOf(variantLetters[0])));
        String[] answers = question.getAnswers();
        for (int i = 0; i < answers.length - 1; i++) {
            String newAnswer = txt.substring(txt.indexOf(variantLetters[i]), txt.indexOf(variantLetters[i + 1]));
            int indexOfVariant = newAnswer.indexOf(variantLetters[i]);
            if (newAnswer.charAt(indexOfVariant + 2) == ')')
            {
                AnswerBank.getInstance().set(this.questonId, variantLetters[i], false);
                newAnswer = newAnswer.substring(0, indexOfVariant + 2) + newAnswer.substring(indexOfVariant + 3, newAnswer.length());
            }
            answers[i] = newAnswer;
        }
        answers[answers.length - 1] = txt.substring(txt.indexOf(variantLetters[variantLetters.length - 1]), txt.length());
        QuestionBank.getInstance().add(question);
        this.questonId++;
    }
}
