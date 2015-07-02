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

public class InvertedListSearch extends SearchImpl {
	
	@Override
	public List<Document> getDocumentMatches(SearchConfiguration config) {
		HashMap<String, List<Integer>> hm = InvertedListStorage.getInstance(config).getHashMap();
		return getDocumentMatches(hm, config);
	}
	
	public static List<Document> getDocumentMatches(HashMap<String, List<Integer>> hm, SearchConfiguration config) {
		List<List<Integer>> resultIds = new ArrayList<>();
		
		for(String term : config.getTerms()) {			
			List<Integer> findings = hm.get(term);
			//StorageManager.saveDocument("hashmap", hm.toString(), 0);
			if(findings != null) {
				resultIds.add(findings);
			}
		}
		
		List<Integer> docIds = BooleanLogic.applyBooleanLogic(config, resultIds);		
		return StorageManager.load(docIds, config.useStopwordElimination(), config.useStemming());
	}

	@Override
	public PrecisionAndRecall getPrecisionAndRecall(SearchConfiguration config, List<Document> result) {
		return new PrecisionAndRecall(config, result);
	}

}
