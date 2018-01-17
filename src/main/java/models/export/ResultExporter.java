package models.export;

import models.AnswerBank;
import models.ExamResult;
import models.Meta;
import models.Utils;

public class ResultExporter {
	private final String
					PDF = "PDF",
					WORD = "Word",
					EXCEL = "Excel",
					TXT = "Txt";
	public void export(String exportFileType) {
		String fileType = null;
		switch (exportFileType) {
			case PDF:
				exportToPDF();
				fileType = PDF;
				break;
			case WORD:
				exportToWord();
				fileType = WORD;
				break;
			case EXCEL:
				exportToExcel();
				fileType = EXCEL;
				break;
			case TXT:
				exportToTxt();
				fileType = TXT;
		}
		Utils.showAlertDialog(
				"Information",
				String.format("Results Exported to %s file", fileType),
				String.format("Your exam results was uploaded into %s", Meta.getInstance().getExportFileName()));
	}

	private void exportToTxt() {
	}

	private void exportToExcel() {
		String[][] matrix = prepareExamResultMatrix();
		/*for (int i = 0; i < matrix.length; i++) {
			String[] strings = matrix[i];
			for (int j = 0; j < strings.length; j++) {
				String string = strings[j];
				System.out.print(string);
			}
			System.out.println();
		}*/
	}

	private void exportToWord() {
	}

	private void exportToPDF() {
	}

	private String[][] prepareExamResultMatrix() {
		String[][] matrix = new String[14][];
		Meta metaData = Meta.getInstance();
		int questionCount = metaData.getQuestionCount();
		matrix[0] = new String[] {
				"Username:", metaData.getUsername()
		};
		matrix[2] = new String[] {
			"Question Count:", metaData.getQuestionCount() + ""
		};
		matrix[4] = new String[] {
			"Right Answers Count:", metaData.getRightAnswerCount() + ""
		};
		matrix[5] = new String[] {
				"Wrong Answers Count:", metaData.getWrongAnswerCount() + ""
		};
		matrix[6] = new String[] {
				"Unanswered Questions Count:", metaData.getUnansweredAnswerCount() + ""
		};
		matrix[8] = new String[questionCount + 1];
		matrix[8][0] = "Question Number:";
		for (int i = 1; i <= questionCount; i++) {
			matrix[8][i] = i + "";
		}


		AnswerBank answerBank = AnswerBank.getInstance();
		matrix[9] = new String[questionCount + 1];
		matrix[9][0] = "Your Answer:";
		for (int i = 1; i <= questionCount; i++) {
			matrix[9][i] = answerBank.get(i - 1, true) + "";
		}

		matrix[10] = new String[questionCount + 1];
		matrix[10][0] = "Correct Answer:";
		for (int i = 1; i <= questionCount; i++) {
			matrix[10][i] = answerBank.get(i - 1, false) + "";
		}

		ExamResult examResult = ExamResult.getInstance();
		matrix[11] = new String[questionCount + 1];
		matrix[11][0] = "";
		for (int i = 1; i <= questionCount; i++) {
			char resultStatus = examResult.get(i - 1);
			matrix[11][i] = (resultStatus == '\u2705' ? '+' : (resultStatus == '\u2716' ? '-' : '*')) + "";
		}
		matrix[13] = new String[]{
				"* -> Unanswered"
		};

		int[] emptyMatrixIndexs = {1, 3, 7, 12};
		for (int i = 0; i < emptyMatrixIndexs.length; i++) {
			matrix[emptyMatrixIndexs[i]] = new String[0];

		}
		return matrix;
	}
}
