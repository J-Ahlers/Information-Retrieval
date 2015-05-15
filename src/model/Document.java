/**
 * 
 */
package model;

import storage.StorageManager;
import utils.StopWordEliminator;

/**
 * @author Jonas Ahlers
 *
 */
public class Document {

	private String title;
	private String content;
	
	public Document(String title, String content) {
		setTitle(title);
		setContent(content);
	}
	
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		while(title.startsWith(" "))
			title = title.replaceFirst(" ", "");
		
		this.title = title;
	}
	
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		StopWordEliminator se = new StopWordEliminator();
		String tmp = se.getCleanContent(content);
		this.content = tmp;
	}
	/**
	 * Saves the document to the specified workingDirectory
	 */
	public void save() {
		StorageManager.save(getFilename(), content);
	}
	
	private String getFilename() {
		return title.toLowerCase().replace(" ", "_");
	}
	
	public void eliminateStopwords() {
		StopWordEliminator se = new StopWordEliminator();
		setContent(se.elimateStopwords(getContent()));
	}
	
	public void useStemming() {
		// Not implemented yet
	}
	
	public void loadOriginal() {
		setContent(StorageManager.readFile(getFilename()));
	}
}
