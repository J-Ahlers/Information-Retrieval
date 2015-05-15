/**
 * 
 */
package model;

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
		this.content = content;
	}
	
}
