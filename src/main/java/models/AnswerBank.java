package models;

public class AnswerBank {
	private static AnswerBank instance;

	private char[] rightAnswers, wrongAnswers;

	private AnswerBank() {
		int variantCount = Meta.getInstance().getVariantCount();

		rightAnswers = new char[variantCount];
		wrongAnswers = new char[variantCount];
	}

	public static AnswerBank getInstance() {
		if (instance == null)
		{
			instance = new AnswerBank();
		}
		return instance;
	}

	public void set(int index, char variant, boolean isRight) {
		if (isRight)
		{
			rightAnswers[index] = variant;
		}
		else
		{
			wrongAnswers[index] = variant;
		}
	}
}
