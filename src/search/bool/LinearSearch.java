/**
 * 
 */
package search.bool;

import java.util.ArrayList;
import java.util.List;

import model.Document;
import search.Search;
import storage.StorageManager;
import utils.PrecisionAndRecall;
import utils.StopWordEliminator;

/**
 * linear search
 * 
 * @author Sophie Baschinski
 */
public class LinearSearch implements Search {	
	
	/**
	 * returns the documents, that contain all the search terms
	 */
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

	
	/**
	 * returns true if all search terms are in the docs content or title
	 */
	private boolean containsTerm(Document doc, List<String> terms) {
		boolean result = true;
		for(String term : terms) {
			// if one search term is a stopword, ignore it
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


	@Override
	public PrecisionAndRecall getPrecisionAndRecall(List<String> terms, List<Document> result) {
		return new PrecisionAndRecall(terms, result);
	}
}
