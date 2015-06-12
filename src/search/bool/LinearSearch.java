/**
 * 
 */
package search.bool;

import java.util.ArrayList;
import java.util.List;

import model.Document;
import search.SearchConfiguration;
import search.SearchImpl;
import storage.StorageManager;
import utils.PrecisionAndRecall;
import utils.StopWordEliminator;

/**
 * linear search
 * 
 * @author Sophie Baschinski
 */
public class LinearSearch extends SearchImpl {

	/**
	 * returns the documents, that contain all the search terms
	 */
	@Override
	public List<Document> getDocumentMatches(SearchConfiguration config) {
		List<Document> docList = new ArrayList<>(StorageManager.load(config.useStopwordElimination(), config.useStemming()));
		List<Document> searchResult = new ArrayList<>();
		
		for(Document doc : docList) {
			if(containsTerm(doc, config.getTerms())) 
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
			boolean stopword = StopWordEliminator.isStopword(term);
			if( stopword && ( doc.getType() == Document.TYPE_STOPWORDS_ELIMINATED || doc.getType() == Document.TYPE_BOTH )) {
				result = false;
				continue;
			}
			
			boolean inTitle = doc.getTitle().contains(" "+term.toLowerCase()+" ");
			boolean inContent = doc.getContent().contains(" "+term.toLowerCase()+" ");
			result = result && ( inTitle || inContent );
			
			if(!result)
				break;
			
		}
		return result && !terms.isEmpty();
	}


	@Override
	public PrecisionAndRecall getPrecisionAndRecall(SearchConfiguration config, List<Document> result) {
		return new PrecisionAndRecall(config, result);
	}
}
