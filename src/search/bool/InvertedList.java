package search.bool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Document;
import search.BooleanLogic;
import search.SearchConfiguration;
import search.SearchImpl;
import storage.InvertedListStorage;
import storage.StorageManager;
import utils.PrecisionAndRecall;

public class InvertedList extends SearchImpl {
	
	@Override
	public List<Document> getDocumentMatches(SearchConfiguration config,
			boolean eliminateStopwords, boolean useStemming) {
		HashMap<String, List<Integer>> hm = InvertedListStorage.getInstance(eliminateStopwords, useStemming).getHashMap();
		return getDocumentMatches(hm, config, eliminateStopwords, useStemming);
	}
	
	public static List<Document> getDocumentMatches(HashMap<String, List<Integer>> hm, SearchConfiguration config,
			boolean eliminateStopwords, boolean useStemming) {
		List<List<Integer>> resultIds = new ArrayList<>();
		
		for(String term : config.getTerms()) {			
			List<Integer> findings = hm.get(term);
			StorageManager.saveDocument("hashmap", hm.toString(), 0);
			if(findings != null) {
				resultIds.add(findings);
			}
		}
		
		List<Integer> docIds = BooleanLogic.applyBooleanLogic(config, resultIds);		
		return StorageManager.load(docIds, eliminateStopwords, useStemming);
	}

	@Override
	public PrecisionAndRecall getPrecisionAndRecall(SearchConfiguration config,
			List<Document> result) {
		return new PrecisionAndRecall(config, result);
	}

}
