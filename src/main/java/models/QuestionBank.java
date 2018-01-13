package models;

import java.util.ArrayList;

public class QuestionBank {
    private static QuestionBank instance;
    private ArrayList<Question> list;
    private QuestionBank() {
        list = new ArrayList<Question>();
    }
    public static QuestionBank getInstance() {
        if (instance == null)
        {
            instance = new QuestionBank();
        }
        return instance;
    }

    public void add(Question question) {
        this.list.add(question);
    }

    public Question get(int index) {
        return this.list.get(index);
    }

    public int indexOf(Question question) {
        return this.list.indexOf(question);
    }

    public int size() { return this.list.size(); }
}
