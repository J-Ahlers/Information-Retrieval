/**
 * 
 */
package storage;

import model.Document;

/**
 * splits the documents with the fables in many documents
 * (each fable in one txt-file)
 * 
 * @author Jonas Ahlers
 */
public class FileSplitter {
	// first fable, needed to ignore introduction in original file
	private static final String SPLITTERM = "The Cock and the Pearl\r\n";
	
	/**
	 * splits the document in two parts: introduction + fables
	 * splits the second part in the different fables and saves them
	 */
	public void createDocumentCollection() {
		// split in introduction and fables
		String filecontent = StorageManager.readFile("res/aesopa10.txt");
		String[] parts = filecontent.split(SPLITTERM);
		if(parts.length != 2)
			return;
		
		// splits all the fables
		String[] fables = (SPLITTERM+parts[1]).split("\r\n\r\n\r\n\r\n");
		
		// splits each fable into title and content
		for(int i = 0; i < fables.length; i++) {
			String[] fableparts = fables[i].split("\r\n\r\n\r\n");
			if(fableparts.length != 2)
				continue;
			Document fable = new Document(i+1, getTitle(fableparts), getContent(fableparts), Document.TYPE_ORIGINAL);
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
