package src.view;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

/**
 * This is the src.view that displays the error messages while loading the map.
 *
 * @author Team20
 */
public class ErrorInfoView extends JPanel {
    private JTextArea InfoTextArea;

    /**
     * Constructor of error info which shows panel
     */
    public ErrorInfoView() {
        this.showErrorPanel();
    }

    /**
     * Show error information
     */
    private void showErrorPanel() {
        this.setBackground(Color.RED);
        this.setLayout(new FlowLayout());
        this.setBounds(1024, 635, 255, 150);
        InfoTextArea = new JTextArea();
        InfoTextArea.setRows(2);
        InfoTextArea.setBounds(1024, 685, 200, 50);
        this.add(InfoTextArea);

        TitledBorder border = new TitledBorder("Error Panel");
        border.setTitleJustification(TitledBorder.CENTER);
        border.setTitlePosition(TitledBorder.TOP);
        this.setBorder(border);
        this.setVisible(true);
    }

    /**
     * @param error error to show in panel
     */
    public void showErrorInfo(String error) {
        InfoTextArea.setText(error);
    }

}
