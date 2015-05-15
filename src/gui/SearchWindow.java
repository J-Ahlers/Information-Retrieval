/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author SofI
 *
 */
public class SearchWindow extends JPanel {
	private static final long serialVersionUID = 13;

	private ManagerFrame frame;
	private File directory;

	private JButton bConfirm = new JButton();
	private JLabel label = new JLabel();
	private JTextField tfSearchWord = new JTextField(30);
	private JCheckBox cbSWE = new JCheckBox("Use StopWord Eliminator");

	private JPanel panelNorth = new JPanel();
	private JPanel panelSouth = new JPanel();

	private AlSearchWindow alSearchWindow = new AlSearchWindow();
	
	public SearchWindow(ManagerFrame frame, File file) {
		this.frame = frame;
		this.directory = file;
		frame.setTitle("Search Window");
		this.setLayout(new BorderLayout());

		bConfirm.setPreferredSize(new Dimension(150, 40));

		label.setText("Please Enter Your Search Word: ");
		bConfirm.setText("Search");

		this.add(panelNorth, BorderLayout.PAGE_START);
		//this.add(panelCenter, BorderLayout.CENTER);
		this.add(panelSouth, BorderLayout.PAGE_END);

		panelNorth.add(label);
		panelNorth.add(tfSearchWord);
		panelSouth.add(bConfirm);
		panelSouth.add(cbSWE);

		bConfirm.addActionListener(alSearchWindow);
	}
	
	private void confirm() {
		System.out.println("YAY");
	}
	
	private class AlSearchWindow implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			Object source = event.getSource();
			if (source.equals(bConfirm))
				confirm();
		}
	}
}
