package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Menueleiste am oberen rechten Rand
 * 
 * @author Sophie Baschinski
 * @since 09.12.2014
 */
public class MenuBar extends JMenuBar {
	private static final long serialVersionUID = 13;

	private ManagerFrame frame;

	private JMenu mFile = new JMenu();
	private JMenu mManage = new JMenu();
	private JMenuItem miMainMenu = new JMenuItem();
	private JMenuItem miEnd = new JMenuItem();
	private JMenuItem miCreateCollection = new JMenuItem();
	private JMenuItem miLoadCollection = new JMenuItem();

	private ActionListener alMenuBar = new AlMenu();

	/**
	 * bekommt einen frame und zeigt MenuBar an
	 */
	public MenuBar(ManagerFrame frame) {
		this.frame = frame;

		mFile.setText("File");
		mManage.setText("Manage");
		miMainMenu.setText("Main Menu");
		miEnd.setText("Exit");
		miCreateCollection.setText("Create Collection");
		miLoadCollection.setText("Load Collection");

		this.add(mFile);
		this.add(mManage);
		mFile.add(miMainMenu);
		mFile.addSeparator();
		mFile.add(miEnd);
		mManage.add(miCreateCollection);
		mManage.add(miLoadCollection);

		mFile.addActionListener(alMenuBar);
		mManage.addActionListener(alMenuBar);
		miMainMenu.addActionListener(alMenuBar);
		miEnd.addActionListener(alMenuBar);
		miCreateCollection.addActionListener(alMenuBar);
		miLoadCollection.addActionListener(alMenuBar);
	}

	private void mainMenu() {
		frame.showMainWindow();
	}

	private void closeSystem() {
		System.exit(0);
	}

	private void createCollection() {
		frame.showCreateCollection();
	}

	private void loadCollections() {
		frame.showLoadCollections();
	}

	private class AlMenu implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			Object source = event.getSource();
			if (source.equals(miMainMenu)) {
				mainMenu();
			} else if (source.equals(miEnd)) {
				closeSystem();
			} else if (source.equals(miCreateCollection)) {
				createCollection();
			} else if (source.equals(miLoadCollection)) {
				loadCollections();
			}
		}
	}
}