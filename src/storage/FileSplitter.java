/**
 * 
 */
package storage;

import model.Document;

/**
 * @author Jonas Ahlers
 *
 */
public class FileSplitter {
	
	private static final String SPLITTERM = "The Cock and the Pearl\r\n";
	
	public void split(String pathToFile) {
		String filecontent = StorageManager.readFile(pathToFile);
		String[] parts = filecontent.split(SPLITTERM);
		if(parts.length != 2)
			return;
		
		String[] fables = (SPLITTERM+parts[1]).split("\r\n\r\n\r\n\r\n");
		for(int i = 0; i < fables.length; i++) {
			String[] fableparts = fables[i].split("\r\n\r\n\r\n");
			if(fableparts.length != 2)
				continue;
			Document fable = new Document(getTitle(fableparts), getContent(fableparts));
			fable.save();
		}
	}
	
	private String getContent(String[] fableparts) {
		return fableparts[1];
	}
	
	private String getTitle(String[] fableparts) {
		return fableparts[0];
	}
}
