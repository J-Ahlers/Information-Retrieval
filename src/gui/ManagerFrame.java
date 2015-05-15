package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import core.RetrievalSystem;

/**
 * Zentrales Fenster
 * 
 * @author Sophie Baschinski
 * @since 09.12.2014
 */
public class ManagerFrame extends JFrame {
	private static final long serialVersionUID = 13;

	private MainWindow MainWindow = new MainWindow(this);
	private MenuBar menuBar = new MenuBar(this);

	/**
	 * Erzeugt Frame, zeigt Hauptmenue an
	 */
	public ManagerFrame() {
		setLayout(new BorderLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = 900;
		int hight = 600;
		this.setBounds((screenSize.width - width) / 2,
				(screenSize.height - hight) / 2, width, hight);

		this.setMinimumSize(new Dimension(width, hight));
		this.setVisible(true);
		this.setResizable(true);
		this.setJMenuBar(menuBar);

		showMainWindow();
	}

	/**
	 * leert den aktuell angezeigten Bildschirm, zeigt das Hauptmenue an
	 */
	public void showMainWindow() {
		this.getContentPane().removeAll();
		this.add(MainWindow, BorderLayout.CENTER);
		this.validate();
		this.repaint();
	}

	/**
	 * leert den aktuell angezeigten Bildschirm, zeigt das
	 * CreateCollection-Fenster an
	 */
	public void showCreateCollection() {
	    JFileChooser chooser = new JFileChooser();
	    chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle("choosertitle");
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    chooser.setAcceptAllFileFilterUsed(false);

	    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	      System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
	      System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
	    } else {
	      System.out.println("No Selection ");
	    }
		showMainWindow();
	}
	
	/**
	 * Titel des Fensters setzen, bestehend aus Titel des aktuellen Fensters -
	 * Image Retrieval
	 */
	@Override
	public void setTitle(String title) {
		super.setTitle(title + " - " + RetrievalSystem.PRODUCT_NAME);
	}
}