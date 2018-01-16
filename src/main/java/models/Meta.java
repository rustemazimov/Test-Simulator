package models;

import javafx.stage.Stage;

import java.security.SecureRandom;
import java.util.HashMap;

public class Meta {
    private static Meta instance;

    private int questionCount;
    private int variantCount;
    private String username;
    private String password;
    private String fileName;
    private int rightAnswerCount;
    private int wrongAnswerCount;
    private int unansweredAnswerCount = questionCount;
    private char[] variantLetters;
    private String[] hashMethods;

    private HashMap<Character, Byte> answerIndexMap;

    private Meta() { }

    public static Meta getInstance() {
        if (instance == null)
        {
            instance = new Meta();
        }
        return instance;
    }

    public void prepareAnswerIndexMap() {

    }

    public byte getIndexForVariant(char c) {
        if (answerIndexMap == null)
        {
            answerIndexMap = new HashMap<>(variantCount);
            for (byte i = 0; i < variantLetters.length; i++) {
                answerIndexMap.put(variantLetters[i], i);
            }
        }
        return answerIndexMap.get(c);
    }
    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public int getVariantCount() {
        return variantCount;
    }

    public void setVariantCount(int variantCount) {
        this.variantCount = variantCount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getRightAnswerCount() {
        return rightAnswerCount;
    }

    public void setRightAnswerCount(int rightAnswerCount) {
        this.rightAnswerCount = rightAnswerCount;
    }

    public int getWrongAnswerCount() {
        return wrongAnswerCount;
    }

    public void setWrongAnswerCount(int wrongAnswerCount) {
        this.wrongAnswerCount = wrongAnswerCount;
    }

    public int getUnansweredAnswerCount() {
        return unansweredAnswerCount;
    }

    public void setUnansweredAnswerCount(int unansweredAnswerCount) {
        this.unansweredAnswerCount = unansweredAnswerCount;
    }

    public char[] getVariantLetters() {
        if (variantLetters == null)
        {
            variantLetters = new char[variantCount];
            char variantTemp = 'A';
            for (int i = 0; i < variantLetters.length; i++) {
                variantLetters[i] = (char) variantTemp++;
            }
        }
        return variantLetters;
    }

    public String[] getHashMethods() {
        if (this.hashMethods == null)
        {
            SecureRandom random = new SecureRandom();
            String[] methodTypes = new String[] {
                    "MD5",
                    "SHA1",
                    "SHA256",
                    "SHA512",
                    "BCRYPT"/*,
                    "PBKDF2"*/
            };

            this.hashMethods = new String[random.nextInt(1_000_000_000) % 10];
            for (int i = 0; i < this.hashMethods.length; i++) {
                this.hashMethods[i] = methodTypes[random.nextInt(1_000_000_000) % methodTypes.length];
            }
        }
        return this.hashMethods;
    }

    /*public void setHashMethods(String[] hashMethods) {
        this.hashMethods = hashMethods;
    }*/

    public void incrementAnswerCount(boolean isRight) {
        if (isRight)
        {
            rightAnswerCount++;
        }
        else
        {
            wrongAnswerCount++;
        }
    }

    public void decrementUnansweredCount() {
        unansweredAnswerCount--;
    }
}

