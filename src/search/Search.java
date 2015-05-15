/**
 * 
 */
package search;

import java.util.List;

import model.Document;

/**
 * @author Jonas Ahlers
 *
 */
public interface Search {

	public static final int STRATEGY_LINEAR = 0;
	
	public static final int TYPE_UNMODIFIED = 0;
	public static final int TYPE_STOPWORDS_ELIMINATED = 1;
	
	public List<Document> getDocumentMatches(List<String> terms);
	
	public boolean containsTerm(Document doc, List<String> terms);
	
}
