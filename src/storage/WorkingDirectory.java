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

	
	public static void save() {
		StorageManager.save("res/config.ini", RetrievalSystem.workingDirectory);
	}
	
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
