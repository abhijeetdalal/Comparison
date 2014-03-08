package myspace.ad.rnd.main;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import myspace.ad.rnd.googlediff.diff_match_patch;
import myspace.ad.rnd.googlediff.diff_match_patch.Diff;
import myspace.ad.rnd.pdfbox.PDFBoxReader;

import org.apache.commons.lang3.StringUtils;

public class CompareApplet extends JApplet {
    /**
     * Generated serial version number.
     */
    private static final long serialVersionUID = -417287614858661228L;

    final JFileChooser fileChooser1 = new JFileChooser();

    public CompareApplet() {
	this.getContentPane().setBounds(new Rectangle(0, 0, 800, 600));
	this.getContentPane().setVisible(true);
	getContentPane().setLayout(null);

	final JPanel jPanel = new JPanel();
	jPanel.setBounds(new Rectangle(0, 0, 800, 600));
	jPanel.setLayout(null);

	JLabel jLabel1 = new JLabel("File 1");
	jLabel1.setBounds(10, 10, 30, 20);

	JLabel jLabel2 = new JLabel("File 2");
	jLabel2.setBounds(10, 40, 30, 20);

	final JTextField jTextField1 = new JTextField();
	jTextField1.setBounds(50, 10, 400, 20);

	final JTextField jTextField2 = new JTextField();
	jTextField2.setBounds(50, 40, 400, 20);

	final JTextPane jTextPane = new JTextPane();
	jTextPane.setAutoscrolls(true);
	jTextPane.setContentType("text/html");

	JScrollPane jScrollPane = new JScrollPane(jTextPane);
	jScrollPane.setBounds(50, 100, 700, 470);

	/*
	 * final JEditorPane jEditorPane = new JEditorPane();
	 * jEditorPane.setBounds(50, 100, 700, 470);
	 * jEditorPane.setAutoscrolls(true);
	 * jEditorPane.setContentType("text/html");
	 */

	JButton jButton1 = new JButton("Browse");
	jButton1.setBounds(460, 10, 80, 20);

	jButton1.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		int returnVal = fileChooser1.showOpenDialog(CompareApplet.this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
		    File file = fileChooser1.getSelectedFile();
		    jTextField1.setText(file.getAbsolutePath());
		}
	    }
	});

	JButton jButton2 = new JButton("Browse");
	jButton2.setBounds(460, 40, 80, 20);
	jButton2.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		int returnVal = fileChooser1.showOpenDialog(CompareApplet.this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
		    File file = fileChooser1.getSelectedFile();
		    jTextField2.setText(file.getAbsolutePath());
		}
	    }
	});

	JButton jButton3 = new JButton("Compare");
	jButton3.setBounds(50, 70, 100, 20);
	jButton3.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		final String file1 = jTextField1.getText();
		final String file2 = jTextField2.getText();
		if (StringUtils.isBlank(file1)) {
		    JOptionPane.showMessageDialog(jPanel,
			    "File 1 cannot be empty");
		}
		if (StringUtils.isBlank(file2)) {
		    JOptionPane.showMessageDialog(jPanel,
			    "File 2 cannot be empty");
		}
		if (StringUtils.isNotBlank(file1)
			&& StringUtils.isNotBlank(file2)) {
		    StringBuffer differenceHTML = new StringBuffer("<html>");
		    differenceHTML.append(compareFiles(file1, file2));
		    differenceHTML.append("</html>");
		    System.out.println(differenceHTML);
		    jTextPane.setText(differenceHTML.toString().replaceAll("<del",
			    "<strike").replaceAll("</del>", "</strike>").replaceAll("&para;", "").replaceAll("<ins style=\"background:#e6ffe6;\">", "<span style=\"background:#e6ffe6;\"><u>").replaceAll("</ins>", "</span></u>"));
		}
	    }
	});

	jPanel.add(jLabel1);
	jPanel.add(jTextField1);
	jPanel.add(jButton1);
	jPanel.add(jLabel2);
	jPanel.add(jTextField2);
	jPanel.add(jButton2);
	jPanel.add(jButton3);
	jPanel.add(jScrollPane);

	this.getContentPane().add(jPanel);

    }

    private String compareFiles(String file1, String file2) {
	PDFBoxReader pdfBoxReader = new PDFBoxReader();

	String textWithoutNewLines1 = pdfBoxReader.readFile(file1);
	String textWithoutNewLines2 = pdfBoxReader.readFile(file2);

	return getDifference(textWithoutNewLines1, textWithoutNewLines2);
    }

    private String getDifference(String textWithoutNewLines1,
	    String textWithoutNewLines2) {
	diff_match_patch diff_match_patch = new diff_match_patch();
	LinkedList<Diff> d = diff_match_patch.diff_main(textWithoutNewLines1,
		textWithoutNewLines2);
	diff_match_patch.diff_cleanupSemantic(d);
	return diff_match_patch.diff_prettyHtml(d);
    }
}
