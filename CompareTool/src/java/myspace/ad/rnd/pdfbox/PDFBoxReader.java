package myspace.ad.rnd.pdfbox;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PDFBoxReader {

    /**
     * Reads PDF documents and returns it as a string using PDFBox API.
     * @param fileName (String).
     * @return converted file into String
     */
    public String readFile(String fileName) {
	PDDocument pdDocument;
	String textWithoutNewLines = StringUtils.EMPTY;
	try {
	    pdDocument = PDDocument.load(fileName);

	    PDFTextStripper stripper = new PDFTextStripper();
	    String text = stripper.getText(pdDocument);

	    textWithoutNewLines = text;
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return textWithoutNewLines;
    }
}
