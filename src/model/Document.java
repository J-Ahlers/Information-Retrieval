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

	public static final int TYPE_ORIGINAL = 0;
	public static final int TYPE_STOPWORDS_ELIMINATED = 1;
	public static final int TYPE_STEMMING = 2;
	public static final int TYPE_BOTH = 3;
	
	private String title;
	private String content;
	private int type;	
	
	public Document(String title, String content, int type) {
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
		this.content = tmp.toLowerCase();
	}
	/**
	 * Saves the document to the specified workingDirectory
	 */
	public void save() {
		StorageManager.saveDocument(getFilename(), content, type);
	}
	
	private String getFilename() {
		return title.toLowerCase().replace(" ", "_");
	}
	
	public void eliminateStopwords() {
		if(type == TYPE_STOPWORDS_ELIMINATED || type == TYPE_BOTH)
			return;
		
		StopWordEliminator se = new StopWordEliminator();
		setContent(se.elimateStopwords(getContent()));
		setType(TYPE_STOPWORDS_ELIMINATED);
	}
	
	public void useStemming() {
		// Not implemented yet
		if(type == TYPE_STEMMING || type == TYPE_BOTH)
			return;
		
		setType(TYPE_STEMMING);
	}
	
	public void loadOriginal() {
		setContent(StorageManager.readFile(getFilename()));
	}
	
	private void setType(int newType) {
		if(type == TYPE_ORIGINAL)
			type = newType;
		else if(type == TYPE_STOPWORDS_ELIMINATED && newType == TYPE_STEMMING)
			type = TYPE_BOTH;
		else if(type == TYPE_STEMMING && newType == TYPE_STOPWORDS_ELIMINATED)
			type = TYPE_BOTH;
	}
}
