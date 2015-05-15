/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Document;

/**
 * @author Sophie Baschinski
 * @since 15.05.2015
 *
 */
public class ResultWindow extends JPanel {
	private static final long serialVersionUID = 13;

	private ManagerFrame frame;
	private List<Document> result;
	private JButton bConfirm = new JButton();
	
	private AlResultWindow alResultWindow = new AlResultWindow();
	
	public ResultWindow(ManagerFrame frame, List<Document> result) {
		this.frame = frame;
		this.result = result;
		frame.setTitle("Result Window");
		this.setLayout(new BorderLayout());
		
		JLabel label = new JLabel();

		JPanel panelNorth = new JPanel();
		JPanel panelSouth = new JPanel();
		
		bConfirm.setPreferredSize(new Dimension(150, 40));

		label.setText("Please Enter Your Result Word: ");
		bConfirm.setText("Result");

		this.add(panelNorth, BorderLayout.PAGE_START);
		//this.add(panelCenter, BorderLayout.CENTER);
		this.add(panelSouth, BorderLayout.PAGE_END);

		panelNorth.add(label);
		panelSouth.add(bConfirm);
		
		JPanel jplPanel = new JPanel();
		jplPanel.setLayout(new BoxLayout(jplPanel, BoxLayout.Y_AXIS));
		for(Document res : result) {
			JTextField tfSearchWord = new JTextField(20);
			tfSearchWord.setText(res.getTitle());
			jplPanel.add(tfSearchWord);
			
		}
		
		panelNorth.add(jplPanel);
		
		bConfirm.addActionListener(alResultWindow);
	}
	
	private void confirm() {
		System.out.println("YAY");
		System.out.println(result.toString());
	}
	
	private class AlResultWindow implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			Object source = event.getSource();
			if (source.equals(bConfirm))
				confirm();
		}
	}
}
