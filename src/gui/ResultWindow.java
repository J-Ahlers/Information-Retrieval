/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Document;

/**
 * @author Jonas Ahlers
 *
 */
public class ResultWindow extends JPanel {
	private static final long serialVersionUID = 13;

	private ManagerFrame frame;
	private List<Document> result;

	private JLabel label = new JLabel();

	private JPanel panelNorth = new JPanel();
	private JPanel panelSouth = new JPanel();

	private AlSearchWindow alSearchWindow = new AlSearchWindow();
	
	public ResultWindow(ManagerFrame frame, List<Document> result) {
		this.frame = frame;
		this.result = result;
		frame.setTitle("Search results");
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
