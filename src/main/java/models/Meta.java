package models;

public class Meta {
    private static Meta instance;

    private int allQuestionCount;
    private int chosenQuestionCount;
    private int variantCount;
    private String username;
    private String password;

    private Meta() {}

    public static Meta getInstance() {
        if (instance == null)
        {
            instance = new Meta();
        }
        return instance;
    }

    public int getAllQuestionCount() {
        return allQuestionCount;
    }

    public void setAllQuestionCount(int allQuestionCount) {
        this.allQuestionCount = allQuestionCount;
    }

    public int getChosenQuestionCount() {
        return chosenQuestionCount;
    }

    public void setChosenQuestionCount(int chosenQuestionCount) {
        this.chosenQuestionCount = chosenQuestionCount;
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
}

