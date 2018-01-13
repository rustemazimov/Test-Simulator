package models.questionIO;

import models.Meta;
import models.Question;
import models.QuestionBank;

import java.io.IOException;

public class QuestionReader {
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
        char[] variantLetters = Meta.getInstance().getVariantLetters();
        question.setQuestionTxt(txt.substring(0, txt.indexOf(variantLetters[0])));
        String[] answers = question.getAnswers();
        for (int i = 0; i < answers.length - 1; i++) {
            answers[i] = txt.substring(txt.indexOf(variantLetters[i]), txt.indexOf(variantLetters[i + 1]));
        }
        answers[answers.length - 1] = txt.substring(txt.indexOf(variantLetters[variantLetters.length - 1]), txt.length());
        QuestionBank.getInstance().add(question);
    }
}
