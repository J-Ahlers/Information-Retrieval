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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import search.SearchConfiguration;
import utils.PrecisionAndRecall;
import model.Document;

/**
 * Result window, shows the result of the search
 * 
 * @author Sophie Baschinski
 * @since 15.05.2015
 *
 */
public class ResultWindow extends JPanel {
	private static final long serialVersionUID = 13;

	private ManagerFrame frame;
	private JButton bBack = new JButton();
	
	private SearchConfiguration config;
	
	private AlResultWindow alResultWindow = new AlResultWindow();
	
	/**
	 * gets a frame and shows the result window
	 */
	public ResultWindow(ManagerFrame frame, List<Document> result, PrecisionAndRecall pr, SearchConfiguration config) {
		this.frame = frame;
		frame.setTitle("Result Window");
		this.setLayout(new BorderLayout());
		this.config = config;

		JPanel panelNorth = new JPanel();
		JPanel panelCenter = new JPanel();
		JPanel panelSouth = new JPanel();
		JLabel label = new JLabel();
		
		if (pr != null) {
			label.setText("Precision: " + pr.getPrecision() + ", Recall: " + pr.getRecall());
			panelNorth.add(label);
		}
		
		bBack.setPreferredSize(new Dimension(150, 40));
		bBack.setText("Back");

		this.add(panelNorth, BorderLayout.PAGE_START);
		this.add(panelCenter, BorderLayout.CENTER);
		this.add(panelSouth, BorderLayout.PAGE_END);

		
		panelSouth.add(bBack);
		
		JPanel jplPanel = new JPanel();
		jplPanel.setLayout(new BoxLayout(jplPanel, BoxLayout.Y_AXIS));
		
		// fixed size, because I don't know how to make it flexible xD
		JTextArea textArea = new JTextArea(29, 60);
		JScrollPane scrollPane = new JScrollPane(textArea); 
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		textArea.setEditable(false);
		
		for(Document res : result) {
			textArea.setText(textArea.getText() + res.getId()+" | "+res.getTitle() + "\n");
		}
		
		jplPanel.add(scrollPane);
		
		panelCenter.add(jplPanel);
		
		bBack.addActionListener(alResultWindow);
	}
	
	/**
	 * under construction!
	 */
	private void back() {
		frame.getContentPane().removeAll();		
		SearchWindow searchWindow = new SearchWindow(frame, config);
		frame.add(searchWindow, BorderLayout.CENTER);
		frame.validate();
		frame.repaint();
	}
	
	/**
	 * ActionListener
	 */
	private class AlResultWindow implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			Object source = event.getSource();
			if (source.equals(bBack))
				back();
		}
	}
}
