package myspace.ad.rnd.pdfbox;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PDFBoxReader {

    /**
     * @param args
     */
    /*
     * public static void main(String[] args) { PDDocument pdf1; PDDocument
     * pdf2; try {
     * 
     * InputStream inputStream = PDFBoxReader.class
     * .getResourceAsStream("Sample1.pdf");
     * 
     * 
     * pdf1 = PDDocument.load("Sample1.pdf"); pdf2 =
     * PDDocument.load("Sample2.pdf");
     * 
     * PDFTextStripper stripper1 = new PDFTextStripper(); String text1 =
     * stripper1.getText(pdf1);
     * 
     * // Replace all new line characters to get the text on one line, this //
     * is done just for more detailing.
     * 
     * // Use of System.getProperty("line.separator") is suitable here as // we
     * know that the text is created on this system and not coming // from
     * another system. String textWithoutNewLines1 = text1.replaceAll(" +",
     * " ");
     * 
     * // System.out.println(textWithoutNewLines1);
     * 
     * PDFTextStripper stripper2 = new PDFTextStripper(); String text2 =
     * stripper2.getText(pdf2);
     * 
     * // Replace all new line characters to get the text on one line, this //
     * is done just for more detailing.
     * 
     * // Use of System.getProperty("line.separator") is suitable here as // we
     * know that the text is created on this system and not coming // from
     * another system. String textWithoutNewLines2 = text2.replaceAll(
     * System.getProperty("line.separator"), " ").replaceAll(" +", " ");
     * 
     * // System.out.println(textWithoutNewLines2);
     * 
     * System.out.println("String Equals: " +
     * StringUtils.equals(textWithoutNewLines1, textWithoutNewLines2));
     * 
     * System.out.println(getDifference(textWithoutNewLines1,
     * textWithoutNewLines2));
     * 
     * } catch (IOException e) { // TODO Auto-generated catch block
     * e.printStackTrace(); } }
     */

    public String readFile(String fileName) {
	PDDocument pdDocument;
	String textWithoutNewLines = StringUtils.EMPTY;
	try {
	    pdDocument = PDDocument.load(fileName);

	    PDFTextStripper stripper = new PDFTextStripper();
	    String text = stripper.getText(pdDocument);

	    /*
	     * textWithoutNewLines = text.replaceAll(
	     * System.getProperty("line.separator"), " ").replaceAll(" +", " ");
	     */
	    textWithoutNewLines = text;
	} catch (IOException e) {
	    e.printStackTrace();
	}
	return textWithoutNewLines;
    }
}
