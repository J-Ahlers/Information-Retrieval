/**
 * 
 */
package gui;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * @author SofI
 *
 */
public class MainWindow extends JPanel {
	private static final long serialVersionUID = 13;

	private ManagerFrame frame;

	private JButton bCreateCollection = new JButton();
	private JButton bLoadCollection = new JButton();
	private JButton bClose = new JButton();
	private JPanel panel = new JPanel();

	private AlMainMenu alMainMenu = new AlMainMenu();

	/**
	 * bekommt einen frame und zeigt Hauptmenue an
	 */
	public MainWindow(ManagerFrame frame) {
		this.frame = frame;
		frame.setTitle("Main Menu");
		this.setBounds(this.frame.getBounds());
		this.setLayout(new GridBagLayout());
		this.add(panel);

		GridLayout gridLayout = new GridLayout(3, 1);
		gridLayout.setVgap(40);
		panel.setLayout(gridLayout);

		Dimension dimension = new Dimension(250, 60);
		bCreateCollection.setPreferredSize(dimension);
		bLoadCollection.setPreferredSize(dimension);
		bClose.setPreferredSize(dimension);

		bCreateCollection.setText("Create new collection");
		bLoadCollection.setText("Load existing collection");
		bClose.setText("Exit system");

		panel.add(bCreateCollection);
		panel.add(bLoadCollection);
		panel.add(bClose);

		bCreateCollection.addActionListener(alMainMenu);
		bLoadCollection.addActionListener(alMainMenu);
		bClose.addActionListener(alMainMenu);
	}

	/**
	 * zeigt CreateCollection-Fenster an
	 */
	private void createCollection() {
		frame.showCreateCollection();
	}

	/**
	 * zeigt LoadCollection-Fenster an
	 */
	private void loadCollections() {
		frame.showLoadCollections();
	}

	/**
	 * schlie�t das Programm
	 */
	private void closeSystem() {
		System.exit(0);
	}

	/**
	 * ActionListener
	 */
	private class AlMainMenu implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			Object source = event.getSource();
			if (source.equals(bCreateCollection)) {
				createCollection();
			} else if (source.equals(bLoadCollection)) {
				loadCollections();
			} else if (source.equals(bClose)) {
				closeSystem();
			}
		}
	}
}
