/**
 * 
 */
package search.bool;

import java.util.ArrayList;
import java.util.List;

import model.Document;
import search.BooleanLogic;
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
		List<List<Integer>> searchResult = new ArrayList<>();
		
		List<String> terms = config.getTerms();
		for(String term : terms) {
			List<Integer> docIDs = new ArrayList<>();
			for(Document doc : docList) {
				if(containsTerm(doc, term))
					docIDs.add(doc.getId());
			}
			searchResult.add(docIDs);
		}
		
		List<Integer> docIds = BooleanLogic.applyBooleanLogic(config, searchResult);		
		return StorageManager.load(docIds, config.useStopwordElimination(), config.useStemming());
	}

	
	/**
	 * returns true if all search terms are in the docs content or title
	 */
	private boolean containsTerm(Document doc, String term) {
		boolean stopword = StopWordEliminator.isStopword(term);
		if( term.equals("") || (stopword && ( doc.getType() == Document.TYPE_STOPWORDS_ELIMINATED || doc.getType() == Document.TYPE_BOTH ))) {
			return false;
		}
		
		boolean inTitle = doc.getTitle().contains(" "+term.toLowerCase()+" ");
		boolean inContent = doc.getContent().contains(" "+term.toLowerCase()+" ");
		return inTitle || inContent;
	}


	@Override
	public PrecisionAndRecall getPrecisionAndRecall(SearchConfiguration config, List<Document> result) {
		return new PrecisionAndRecall(config, result);
	}
}
