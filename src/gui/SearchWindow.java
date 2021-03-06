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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Document;
import search.Search;
import search.SearchConfiguration;
import search.SearchImpl;
import utils.PrecisionAndRecall;

/**
 * search window to get the search terms and
 * the search parameters
 * 
 * @author Sophie Baschinski
 * @since 15.05.2015
 */
public class SearchWindow extends JPanel {
	private static final long serialVersionUID = 13;

	private ManagerFrame frame;
	private List<String> searchTerms;
	private boolean sWE;
	private boolean stemming;
	private boolean precisionRecall;
	private int searchStrategyIndex;
	private List<Document> result;

	private JButton bConfirm = new JButton();
	private JLabel label = new JLabel();
	private JTextField tfSearchWord = new JTextField(30);
	private JCheckBox cbSWE = new JCheckBox("Use StopWord Eliminator");
	private JCheckBox cbStemming = new JCheckBox("Use Stemming");
	private JCheckBox cbPrecisionRecall = new JCheckBox("Show Precision and Recall");
	private JComboBox<?> searchStrategy;
    String[] searchMethods = { "Lineare Suche", "Invertierte Liste", "Signaturen"};

	private JPanel panelNorth = new JPanel();
	private JPanel panelCenter = new JPanel();
	private JPanel panelSouth = new JPanel();

	private AlSearchWindow alSearchWindow = new AlSearchWindow();

	/**
	 * gets a frame and shows search window
	 */
	public SearchWindow(ManagerFrame frame, SearchConfiguration config) {
		this.frame = frame;
		frame.setTitle("Search Window");
		this.setLayout(new BorderLayout());
		this.searchTerms = config.getTerms();
		this.sWE = config.useStopwordElimination();
		this.stemming = config.useStemming();
		this.precisionRecall = config.isShowPrecisionRecall(); 
		this.searchStrategyIndex = config.getStrategy();

		bConfirm.setPreferredSize(new Dimension(150, 40));

		label.setText("Please Enter Your Search Word: ");
		bConfirm.setText("Search");

		this.add(panelNorth, BorderLayout.PAGE_START);
		this.add(panelCenter, BorderLayout.CENTER);
		this.add(panelSouth, BorderLayout.PAGE_END);

		searchStrategy = new JComboBox<>(this.searchMethods);
		searchStrategy.setSelectedIndex(this.searchStrategyIndex);
		if (this.searchTerms.isEmpty()) {
			tfSearchWord.setText("");
		}
		else {
			String temp = "";
			for (String searchTerm : searchTerms) {
				temp = temp + searchTerm + " ";
			}
			tfSearchWord.setText(temp);
		}
		cbSWE.setSelected(this.sWE);
		cbStemming.setSelected(this.stemming);
		cbPrecisionRecall.setSelected(this.precisionRecall);
		
		panelNorth.add(label);
		panelNorth.add(tfSearchWord);
		panelCenter.add(cbSWE);
		panelCenter.add(cbStemming);
		panelCenter.add(cbPrecisionRecall);
		panelCenter.add(searchStrategy);
		panelSouth.add(bConfirm);
		
		bConfirm.addActionListener(alSearchWindow);
	}
	
	/**
	 * confirm-button to start the search;
	 * splits search terms and saves search parameters
	 */
	private void confirm() {
		searchTerms = Arrays.asList(tfSearchWord.getText().split(" "));
		sWE = cbSWE.isSelected();
		stemming = cbStemming.isSelected();
		precisionRecall = cbPrecisionRecall.isSelected();
		searchStrategyIndex = searchStrategy.getSelectedIndex();
		
		if (!searchTerms.isEmpty()) {
			Search search = new SearchImpl(searchStrategyIndex);
			SearchConfiguration config = new SearchConfiguration(searchTerms, searchStrategyIndex, sWE, stemming, precisionRecall);
			result = search.getDocumentMatches(config);
			PrecisionAndRecall pR;
			if (precisionRecall)
				pR = search.getPrecisionAndRecall();
			else
				pR = null;
			frame.getContentPane().removeAll();
			ResultWindow resultWindow = new ResultWindow(frame, result, pR, config);
			frame.add(resultWindow, BorderLayout.CENTER);
			frame.validate();
			frame.repaint();
		}
	}
	
	/**
	 * ActionListener
	 */
	private class AlSearchWindow implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			Object source = event.getSource();
			if (source.equals(bConfirm))
				confirm();
		}
	}
}
