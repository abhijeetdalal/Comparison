package myspace.ad.rnd.pdfbox;

import java.io.IOException;

import myspace.ad.rnd.pdfbox.exception.PDFBoxReaderException;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PDFBoxReader {

    /**
     * Reads PDF documents and returns it as a string using PDFBox API.
     * @param fileName (String).
     * @return converted file into String
     * @throws PDFBoxReaderException (PDFBoxReaderException thrown).
     */
    public String readFile(String fileName) throws PDFBoxReaderException {
	if (StringUtils.isBlank(fileName)) {
	    return StringUtils.EMPTY;
	}
	PDDocument pdDocument;
	try {
	    pdDocument = PDDocument.load(fileName);

	    PDFTextStripper stripper = new PDFTextStripper();
	    return stripper.getText(pdDocument);

	} catch (IOException e) {
	    throw new PDFBoxReaderException(e);
	}
    }
}
