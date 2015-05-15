/**
 * 
 */
package search;

import java.io.File;
import java.util.List;

/**
 * @author Jonas Ahlers
 *
 */
public interface Search {

	public static final int STRATEGY_LINEAR = 0;
	
	public static final int TYPE_UNMODIFIED = 0;
	public static final int TYPE_STOPWORDS_ELIMINATED = 1;
	
	public List<File> getDocumentMatches(List<String> terms);
	
	public boolean containsTerm(File file);
	
}
