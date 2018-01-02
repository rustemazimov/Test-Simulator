package models.questionIO;

import models.Meta;
import models.Question;
import models.QuestionBank;

import java.io.IOException;

public class QuestionReader {
    public void readQuestions() throws IOException {
        String text = new StringReader().readString();
        Question question;
        char[] variantLetters = Meta.getInstance().getVariantLetters();
        for (int i = 0; i < text.length(); i++) {
            question = new Question();
            int questionBeginningIndex = text.indexOf((i + 1) + ")");
            text = text.substring(questionBeginningIndex);
            int firstVariantLetterIndex = text.indexOf(variantLetters[0] + ")");
            question.setQuestionTxt(text.substring(questionBeginningIndex, firstVariantLetterIndex));
            for (int j = 1; j < variantLetters.length; j++) {
                question.getAnswers()[j - 1] = text.substring(firstVariantLetterIndex, variantLetters[j]);
            }
            QuestionBank.getInstance().add(question);
        }
    }
}
