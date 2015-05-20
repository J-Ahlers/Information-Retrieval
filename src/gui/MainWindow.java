/**
 * 
 */
package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import storage.WorkingDirectory;

/**
 * @author Sophie Baschinski
 * @since 15.05.2015 
 */
public class MainWindow extends JPanel {
	private static final long serialVersionUID = 13;

	private ManagerFrame frame;

	private JButton bChooseDirectory = new JButton();
	private JButton bClose = new JButton();
	private JPanel panel = new JPanel();

	private AlMainMenu alMainMenu = new AlMainMenu();

	/**
	 * bekommt einen frame und zeigt Hauptmenue an
	 */
	public MainWindow(ManagerFrame frame) {
		if (WorkingDirectory.load()) {
			this.frame = frame;
			this.frame.getContentPane().removeAll();		
			SearchWindow searchWindow = new SearchWindow(this.frame);
			this.frame.add(searchWindow, BorderLayout.CENTER);
			this.frame.validate();
			this.frame.repaint();
		} else {
			this.frame = frame;
			frame.setTitle("Main Menu");
			this.setBounds(this.frame.getBounds());
			this.setLayout(new GridBagLayout());
			this.add(panel);
	
			GridLayout gridLayout = new GridLayout(3, 1);
			gridLayout.setVgap(40);
			panel.setLayout(gridLayout);
	
			Dimension dimension = new Dimension(250, 60);
			bChooseDirectory.setPreferredSize(dimension);
			bClose.setPreferredSize(dimension);
	
			bChooseDirectory.setText("Choose Directory!");
			bClose.setText("Exit system");
	
			panel.add(bChooseDirectory);
			panel.add(bClose);
	
			bChooseDirectory.addActionListener(alMainMenu);
			bClose.addActionListener(alMainMenu);
		}
	}

	/**
	 * zeigt chooseDirectory-Fenster an
	 */
	private void chooseDirectory() {
		frame.directoryChooser();
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
			if (source.equals(bChooseDirectory)) {
				chooseDirectory();
			} else if (source.equals(bClose)) {
				closeSystem();
			}
		}
	}
}
