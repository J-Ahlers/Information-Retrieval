/**
 * 
 */
package search;

import java.io.File;
import java.util.List;

import search.linear.LinearSearch;

/**
 * @author Jonas Ahlers
 *
 */
public class SearchImpl implements Search {

	private Search search;
	
	public SearchImpl(int strategy) {
		switch(strategy) {
		case STRATEGY_LINEAR:
			this.search = new LinearSearch();
			break;
		default:
			this.search = new LinearSearch();		
		}
	}
	
	@Override
	public List<File> getDocumentMatches(List<String> terms) {
		return search.getDocumentMatches(terms);
	}

	@Override
	public boolean containsTerm(File file) {
		return search.containsTerm(file);
	}

}
