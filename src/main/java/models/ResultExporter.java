package models;

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
	}

	private void exportToWord() {
	}

	private void exportToPDF() {
	}
}
