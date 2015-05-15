/**
 * 
 */
package search.linear;

import java.util.ArrayList;
import java.util.List;

import model.Document;
import search.Search;
import storage.StorageManager;

/**
 * @author Sophie Baschinski
 *
 */
public class LinearSearch implements Search {	
	
	@Override
	public List<Document> getDocumentMatches(List<String> terms) {
		List<Document> docList = new ArrayList<>(StorageManager.load());
		List<Document> searchResult = new ArrayList<>();
		
		for(Document doc : docList) {
			if(containsTerm(doc, terms)) 
				searchResult.add(doc);
		}
		
		return searchResult;
	}

	@Override
	public boolean containsTerm(Document doc, List<String> terms) {
		for(String term : terms) {
			if ((doc.getTitle().contains(term.toLowerCase())) || (doc.getContent().contains(term.toLowerCase())))
				return true;
		}
		return false;
	}

}
