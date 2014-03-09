package myspace.ad.rnd.main;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import myspace.ad.rnd.googlediff.Diff_Match_Patch;
import myspace.ad.rnd.googlediff.Diff_Match_Patch.Diff;
import myspace.ad.rnd.pdfbox.PDFBoxReader;

import org.apache.commons.lang3.StringUtils;

public class CompareApplet extends JApplet {
    /**
     * Generated serial version number.
     */
    private static final long serialVersionUID = -417287614858661228L;

    final JFileChooser fileChooser1 = new JFileChooser();
    final JTextPane jTextPaneOutput = new JTextPane();
    String fileType = "pdf";

    /**
     * Public constructor with all applet initialization code.
     */
    public CompareApplet() {
	this.getContentPane().setBounds(new Rectangle(0, 0, 800, 600));
	this.getContentPane().setVisible(true);
	getContentPane().setLayout(null);

	final JPanel jPanel = new JPanel();
	jPanel.setBounds(new Rectangle(0, 0, 800, 600));
	jPanel.setLayout(null);

	JLabel jLabelFile1 = new JLabel("File 1");
	jLabelFile1.setBounds(10, 10, 30, 20);

	JLabel jLabelFile2 = new JLabel("File 2");
	jLabelFile2.setBounds(10, 40, 30, 20);

	final JTextField jTextFieldFile1 = new JTextField();
	jTextFieldFile1.setBounds(50, 10, 400, 20);

	final JTextField jTextFieldFile2 = new JTextField();
	jTextFieldFile2.setBounds(50, 40, 400, 20);

	jTextPaneOutput.setAutoscrolls(true);
	jTextPaneOutput.setContentType("text/html");

	JScrollPane jScrollPaneOutput = new JScrollPane(jTextPaneOutput);
	jScrollPaneOutput.setBounds(50, 100, 700, 470);

	JButton jButtonFile1 = new JButton("Browse");
	jButtonFile1.setBounds(460, 10, 80, 20);

	jButtonFile1.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		int returnVal = fileChooser1.showOpenDialog(CompareApplet.this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
		    File file = fileChooser1.getSelectedFile();
		    jTextFieldFile1.setText(file.getAbsolutePath());
		}
	    }
	});

	JButton jButtonFile2 = new JButton("Browse");
	jButtonFile2.setBounds(460, 40, 80, 20);
	jButtonFile2.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		int returnVal = fileChooser1.showOpenDialog(CompareApplet.this);

		if (returnVal == JFileChooser.APPROVE_OPTION) {
		    File file = fileChooser1.getSelectedFile();
		    jTextFieldFile2.setText(file.getAbsolutePath());
		}
	    }
	});

	String[] fileTypes = { "pdf", "txt" };
	JComboBox<String> jComboBoxFileType = new JComboBox<String>(fileTypes);
	jComboBoxFileType.setBounds(550, 10, 80, 20);
	jComboBoxFileType.addActionListener(new ActionListener() {

	    @SuppressWarnings("unchecked")
	    @Override
	    public void actionPerformed(ActionEvent e) {
		JComboBox<String> cb = (JComboBox<String>) e.getSource();
		fileType = (String) cb.getSelectedItem();

	    }
	});

	JButton jButtonCompare = new JButton("Compare");
	jButtonCompare.setBounds(50, 70, 100, 20);
	jButtonCompare.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		final String file1 = jTextFieldFile1.getText();
		final String file2 = jTextFieldFile2.getText();
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
		    doComparison(file1, file2);
		}
	    }
	});

	jPanel.add(jLabelFile1);
	jPanel.add(jTextFieldFile1);
	jPanel.add(jButtonFile1);
	jPanel.add(jLabelFile2);
	jPanel.add(jTextFieldFile2);
	jPanel.add(jButtonFile2);
	jPanel.add(jComboBoxFileType);
	jPanel.add(jButtonCompare);
	jPanel.add(jScrollPaneOutput);

	this.getContentPane().add(jPanel);

    }

    /**
     * Calls Comparison logic depending on file type selected.
     * @param file1 (String).
     * @param file2 (String).
     */
    private void doComparison(final String file1, final String file2) {
	StringBuffer differenceHTML = new StringBuffer("<html>");
	if (StringUtils.equalsIgnoreCase(fileType, "pdf")) {
	    differenceHTML.append(comparePDFs(file1, file2));
	} else {
	    System.out.println("txt selected");
	}
	differenceHTML.append("</html>");
	//System.out.println(differenceHTML);
	jTextPaneOutput.setText(differenceHTML
		.toString()
		.replaceAll("<del", "<strike")
		.replaceAll("</del>", "</strike>")
		.replaceAll("&para;", "")
		.replaceAll("<ins style=\"background:#e6ffe6;\">",
			"<span style=\"background:#e6ffe6;\"><u>")
		.replaceAll("</ins>", "</span></u>"));
    }

    /**
     * Reads PDF's using PDFBox API and calls Comparison logic.
     * @param file1 (String).
     * @param file2 (String).
     * @return difference String
     */
    private String comparePDFs(final String file1, final String file2) {
	PDFBoxReader pdfBoxReader = new PDFBoxReader();

	String file1Text = pdfBoxReader.readFile(file1);
	String file2Text = pdfBoxReader.readFile(file2);

	return getDifference(file1Text, file2Text);
    }

    /**
     * Compares 2 strings using google API.
     * @param file1Text (String).
     * @param file2Text (String).
     * @return converted difference string in HTML format
     */
    private String getDifference(String file1Text, String file2Text) {
	Diff_Match_Patch diff_match_patch = new Diff_Match_Patch();
	LinkedList<Diff> d = diff_match_patch.diff_main(file1Text, file2Text);
	diff_match_patch.diff_cleanupSemantic(d);
	return diff_match_patch.diff_prettyHtml(d);
    }
}
