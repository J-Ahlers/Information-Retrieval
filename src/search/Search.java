/**
 * 
 */
package search;

import java.util.List;

import utils.PrecisionAndRecall;
import model.Document;

/**
 * interface
 * 
 * @author Jonas Ahlers
 *
 */
public interface Search {

	public static final int STRATEGY_LINEAR = 0;
	public static final int STRATEGY_INVERTED_LIST = 1;
	public static final int STRATEGY_SIGNATURE = 2;
	
	public static final int TYPE_UNMODIFIED = 0;
	public static final int TYPE_STOPWORDS_ELIMINATED = 1;
	
	public List<Document> getDocumentMatches(List<String> terms, boolean eliminateStopwords, boolean useStemming);
	
	public PrecisionAndRecall getPrecisionAndRecall(List<String> terms, List<Document> result);
}
