/**
 * 
 */
package search;

import java.util.List;

import model.Document;
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
	public List<Document> getDocumentMatches(List<String> terms) {
		return search.getDocumentMatches(terms);
	}

	@Override
	public boolean containsTerm(Document doc, List<String> terms) {
		return search.containsTerm(doc, terms);
	}

}
