/**
 * 
 */
package storage;

import core.RetrievalSystem;

/**
 * @author Jonas Ahlers
 *
 */
public class WorkingDirectory {

	/**
	 * save the working directory in a file to recover it when restarting
	 */
	public static void save() {
		StorageManager.save("res/config.ini", RetrievalSystem.workingDirectory);
	}
	
	/**
	 * load the saved working directory after system starts
	 * @return true if working directory exists, else false
	 */
	public static boolean load() {
		String workingDirectory = StorageManager.readFile("res/config.ini");
		if(workingDirectory != null) {
			if(!workingDirectory.equals("")) {
				RetrievalSystem.workingDirectory = workingDirectory;
				return true;
			}
		}
				
		return false;
	}
}
