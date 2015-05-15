package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import core.RetrievalSystem;

/**
 * Zentrales Fenster der Image-Retrieval-Software
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
		
	}

	/**
	 * leert den aktuell angezeigten Bildschirm, zeigt das
	 * LoadCollections-Fenster an
	 */
	public void showLoadCollections() {
		//this.getContentPane().removeAll();
		//loadCollections = new LoadCollections(this);
		//this.add(loadCollections, BorderLayout.CENTER);
		//this.validate();
		//this.repaint();
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