package core;

import gui.ManagerFrame;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class RetrievalSystem {

	private static ManagerFrame frame;

	/**
	 * Name der Software wird immer hinter dem Namen des aktuellen Fensters
	 * angezeigt
	 */
	public static final String PRODUCT_NAME = "InformationRetrieval";
	
	/**
	 * Does NOT end with a tailing slash!
	 */
	public static final String workingDirectory = "/res/WorkingDirectory";
	
	public static void main(String[] args) {
		
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// nichts tun
		}

		frame = new ManagerFrame();
		while (frame.isVisible()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// nichts tun
			}
		}
	}
}
