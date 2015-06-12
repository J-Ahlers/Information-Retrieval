/**
 * 
 */
package model;

import java.util.ArrayList;
import java.util.List;

import storage.StorageManager;
import utils.Stemmer;
import utils.StopWordEliminator;

/**
 * Document class
 * 
 * @author Jonas Ahlers
 *
 */
public class Document {

	// document-type (is the doc already manipulated or not)
	public static final int TYPE_ORIGINAL = 0;
	public static final int TYPE_STOPWORDS_ELIMINATED = 1;
	public static final int TYPE_STEMMING = 2;
	public static final int TYPE_BOTH = 3;
	
	private int id;
	private String title;
	private String content;
	private int type;	
	
	public Document(int id, String title, String content, int type) {
		setId(id);
		setTitle(title);
		setContent(content);
		setType(type);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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
		String prefix = id < 10 ? "0" : "";
		return prefix+id+"#"+title.toLowerCase().replace(" ", "_");
	}
	
	/**
	 * eliminates stopwords if not already happened
	 */
	public void eliminateStopwords() {
		if(type == TYPE_STOPWORDS_ELIMINATED || type == TYPE_BOTH)
			return;
		
		StopWordEliminator se = new StopWordEliminator();
		setContent(se.elimateStopwords(getContent()));
		setType(TYPE_STOPWORDS_ELIMINATED);
	}
	
	/**
	 * uses stemming if not already done
	 */
	public void useStemming() {
		// Not implemented yet
		if(type == TYPE_STEMMING || type == TYPE_BOTH)
			return;
		
		Stemmer st = new Stemmer();
		setContent(st.getStemmedContent(getContent()));
		setType(TYPE_STEMMING);
	}
	
	/**
	 * load original document
	 */
	public void loadOriginal() {
		setContent(StorageManager.readFile(getFilename()));
	}
	
	/**
	 * @param newType the type to set
	 */
	private void setType(int newType) {
		if(type == TYPE_ORIGINAL)
			type = newType;
		else if(type == TYPE_STOPWORDS_ELIMINATED && newType == TYPE_STEMMING)
			type = TYPE_BOTH;
		else if(type == TYPE_STEMMING && newType == TYPE_STOPWORDS_ELIMINATED)
			type = TYPE_BOTH;
	}
	
	public int getType() {
		return this.type;
	}
	
	public static List<Integer> convert(List<Document> list) {
		List<Integer> ids = new ArrayList<>();
		for(Document doc : list)
			ids.add(doc.getId());
		
		return ids;
	}
}
