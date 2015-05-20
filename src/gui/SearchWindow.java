/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Document;
import search.Search;
import search.SearchImpl;

/**
 * @author Sophie Baschinski
 * @since 15.05.2015
 */
public class SearchWindow extends JPanel {
	private static final long serialVersionUID = 13;

	private ManagerFrame frame;
	private List<String> searchTerms;
	private boolean sWE;
	private boolean stemming;
	private List<Document> result;

	private JButton bConfirm = new JButton();
	private JLabel label = new JLabel();
	private JTextField tfSearchWord = new JTextField(30);
	private JCheckBox cbSWE = new JCheckBox("Use StopWord Eliminator");
	private JCheckBox cbStemming = new JCheckBox("Use Stemming");

	private JPanel panelNorth = new JPanel();
	private JPanel panelSouth = new JPanel();

	private AlSearchWindow alSearchWindow = new AlSearchWindow();
	
	public SearchWindow(ManagerFrame frame) {
		this.frame = frame;
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
		panelSouth.add(cbStemming);
		
		bConfirm.addActionListener(alSearchWindow);
	}
	
	private void confirm() {
		searchTerms = Arrays.asList(tfSearchWord.getText().split(" "));
		sWE = cbSWE.isSelected();
		//stemming = cbStemming.isSelected();
		stemming = false;
		if (!searchTerms.isEmpty()) {
			Search search = new SearchImpl(Search.STRATEGY_LINEAR);
			result = search.getDocumentMatches(searchTerms, sWE, stemming);
			frame.getContentPane().removeAll();
			ResultWindow resultWindow = new ResultWindow(frame, result);
			frame.add(resultWindow, BorderLayout.CENTER);
			frame.validate();
			frame.repaint();
		}
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
