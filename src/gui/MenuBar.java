package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Menubar
 * 
 * @author Sophie Baschinski
 * @since 09.12.2014
 */
public class MenuBar extends JMenuBar {
	private static final long serialVersionUID = 13;

	private ManagerFrame frame;

	private JMenu mFile = new JMenu();
	private JMenuItem miMainMenu = new JMenuItem();
	private JMenuItem miSearchMenu = new JMenuItem();
	private JMenuItem miEnd = new JMenuItem();
	
	private ActionListener alMenuBar = new AlMenu();

	/**
	 * gets a frame and shows menubar
	 */
	public MenuBar(ManagerFrame frame) {
		this.frame = frame;

		mFile.setText("File");
		miMainMenu.setText("Main Menu");
		miSearchMenu.setText("Search Menu");
		miEnd.setText("Exit");

		this.add(mFile);
		mFile.add(miMainMenu);
		mFile.add(miSearchMenu);
		mFile.addSeparator();
		mFile.add(miEnd);

		mFile.addActionListener(alMenuBar);
		miMainMenu.addActionListener(alMenuBar);
		miSearchMenu.addActionListener(alMenuBar);
		miEnd.addActionListener(alMenuBar);
	}

	private void mainMenu() {
		frame.showMainWindow();
	}

	private void searchMenu() {
		frame.showSearchWindow();
	}
	
	private void closeSystem() {
		System.exit(0);
	}

	@SuppressWarnings("unused")
	private void chooseDirectory() {
		frame.directoryChooser();
	}

	/**
	 * ActionListener
	 */
	private class AlMenu implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			Object source = event.getSource();
			if (source.equals(miMainMenu)) {
				mainMenu();
			} else if (source.equals(miSearchMenu)) {
				searchMenu();
			} else if (source.equals(miEnd)) {
				closeSystem();
			}
		}
	}
}