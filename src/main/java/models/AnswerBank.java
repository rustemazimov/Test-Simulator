package models;

import java.util.HashMap;

public class AnswerBank {
	private static AnswerBank instance;

	private char[] userAnswers, realAnswers;

	private AnswerBank() {
		int questionCount = Meta.getInstance().getQuestionCount();

		userAnswers = new char[questionCount];
		realAnswers = new char[questionCount];


	}

	public static AnswerBank getInstance() {
		if (instance == null)
		{
			instance = new AnswerBank();
		}
		return instance;
	}

	public void set(int index, char variant, boolean isUser) {
		if (isUser)
		{
			userAnswers[index] = variant;
		}
		else
		{
			realAnswers[index] = variant;
		}
	}

	public char get(int index, boolean isUser) {
		if (isUser)
		{
			return userAnswers[index];
		}
		else
		{
			return realAnswers[index];
		}
	}

	public void remove(int index) {
		userAnswers[index] = '\u0000';
	}
}
