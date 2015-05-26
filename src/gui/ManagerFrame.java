package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import storage.FileSplitter;
import storage.WorkingDirectory;
import core.RetrievalSystem;

/**
 * Manager Frame
 * 
 * @author Sophie Baschinski
 * @since 09.12.2014
 */
public class ManagerFrame extends JFrame {
	private static final long serialVersionUID = 13;

	private MainWindow MainWindow = new MainWindow(this);
	private MenuBar menuBar = new MenuBar(this);

	/**
	 * creates frame
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
	 * empties the screen and shows the main window
	 */
	public void showMainWindow() {
		this.getContentPane().removeAll();
		this.add(MainWindow, BorderLayout.CENTER);
		this.validate();
		this.repaint();
	}

	/**
	 * empties the screen and shows the searching window
	 */
	public void showSearchWindow() {
	    this.getContentPane().removeAll();		
		SearchWindow searchWindow = new SearchWindow(this);
		this.add(searchWindow, BorderLayout.CENTER);
		this.validate();
		this.repaint();
	}
	
	/**
	 * shows window to choose working directory
	 */
	public void directoryChooser() {
	    JFileChooser chooser = new JFileChooser();
	    chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle("choosertitle");
	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	    chooser.setAcceptAllFileFilterUsed(false);

	    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		    RetrievalSystem.workingDirectory = chooser.getSelectedFile().getAbsolutePath();
		    WorkingDirectory.save();
		    FileSplitter fs = new FileSplitter();
			fs.createDocumentCollection();
		    // opens search-window
			showSearchWindow();
	    } else {
	    	System.out.println("No Selection of Working Directory!");
	    }
	}
	
	/**
	 * titel of the window
	 */
	@Override
	public void setTitle(String title) {
		super.setTitle(title + " - " + RetrievalSystem.PRODUCT_NAME);
	}
}