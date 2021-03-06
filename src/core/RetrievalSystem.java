package core;

import gui.ManagerFrame;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class RetrievalSystem {

	private static ManagerFrame frame;

	/**
	 * name of software
	 */
	public static final String PRODUCT_NAME = "InformationRetrieval";
	
	/**
	 * ends NOT with an slash!
	 */
	public static String workingDirectory;
	
	public static void main(String[] args) {
		
		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
			// do nothing
		}

		frame = new ManagerFrame();
		while (frame.isVisible()) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// do nothing
			}
		}
	}
}
