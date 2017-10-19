package view;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

/**
 * @author Team20
 */
public class ErrorInfoView extends JPanel {
	private JTextArea InfoTextArea;
	
	public ErrorInfoView() {
		this.showErrorPanel();
	}

	/**
	 * Show error information 
	 */
	public void showErrorPanel() {
		this.setBackground(Color.RED);
		this.setLayout(new FlowLayout());
		this.setBounds(1024, 468, 255, 300);
		InfoTextArea = new JTextArea();
		InfoTextArea.setRows(2);
		this.setBounds(1024, 468, 255, 300);
		InfoTextArea.setBounds(1024, 0, 255, 50);
		this.add(InfoTextArea);
		
		TitledBorder border = new TitledBorder("Error Panel");
	    border.setTitleJustification(TitledBorder.CENTER);
	    border.setTitlePosition(TitledBorder.TOP);
		this.setBorder(border);
		this.setVisible(true);
	}
	
	public void showErrorInfo(String error) {
		InfoTextArea.setText(error);
	}

}