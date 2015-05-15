/**
 * 
 */
package search.linear;

import java.util.ArrayList;
import java.util.List;

import model.Document;
import search.Search;
import storage.StorageManager;
import utils.StopWordEliminator;

/**
 * @author Sophie Baschinski
 *
 */
public class LinearSearch implements Search {	
	
	@Override
	public List<Document> getDocumentMatches(List<String> terms, boolean eliminateStopwords, boolean useStemming) {
		List<Document> docList = new ArrayList<>(StorageManager.load(eliminateStopwords, useStemming));
		List<Document> searchResult = new ArrayList<>();
		
		for(Document doc : docList) {
			if(containsTerm(doc, terms)) 
				searchResult.add(doc);
		}
		
		return searchResult;
	}

	@Override
	public boolean containsTerm(Document doc, List<String> terms) {
		boolean result = true;
		for(String term : terms) {
			if(StopWordEliminator.isStopword(term))
				continue;
			
			boolean inTitle = doc.getTitle().contains(" "+term.toLowerCase()+" ");
			boolean inContent = doc.getContent().contains(" "+term.toLowerCase()+" ");
			result = result && ( inTitle || inContent );
			if(result)
				result = true;
		}
		return result;
	}

}
