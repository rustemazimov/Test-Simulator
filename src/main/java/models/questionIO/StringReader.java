package models.questionIO;

import models.Meta;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class StringReader {
    private String fileName;
    String readString() throws IOException {
        fileName = Meta.getInstance().getFileName();
        switch (fileName.substring(fileName.lastIndexOf('.'))){
            case ".docx":
                try {
                    return readFromWord();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }

    private String readFromWord() throws IOException {
        StringBuilder builder = new StringBuilder();
        List<XWPFParagraph> paragraphs = new XWPFDocument(new FileInputStream(fileName)).getParagraphs();
        for (XWPFParagraph paragraph : paragraphs) {
            builder.append(paragraph.getText());
        }
        return builder.toString();
    }
}
