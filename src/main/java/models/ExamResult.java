package models;

import java.util.HashMap;

public class ExamResult {
	private static ExamResult instance;

	private HashMap<Integer, Character> resultMap;
	private ExamResult() {
		this.resultMap = new HashMap<>(Meta.getInstance().getQuestionCount());
	}

	public static ExamResult getInstance() {
		if (instance == null)
		{
			instance = new ExamResult();
		}
		return instance;
	}

	public void put(int index, char resultStatus) {
		this.resultMap.put(index, resultStatus);
	}

	public char get(int index) {
		return this.resultMap.get(index);
	}
}
