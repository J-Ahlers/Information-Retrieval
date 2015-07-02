package search.bool;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;

import model.Document;
import search.BooleanLogic;
import search.SearchConfiguration;
import search.SearchImpl;
import storage.InvertedSignatureListStorage;
import storage.StorageManager;
import utils.PrecisionAndRecall;

public class SignatureSearch extends SearchImpl {

	@Override
	public List<Document> getDocumentMatches(SearchConfiguration config) {
		HashMap<BitSet, List<Integer>> hm = InvertedSignatureListStorage.getInstance(config).getHashMap();
		return getDocumentMatches(hm, config);
	}
	
	public static List<Document> getDocumentMatches(HashMap<BitSet, List<Integer>> hm, SearchConfiguration config) {
		List<List<Integer>> resultIds = new ArrayList<>();
		
		for(String term : config.getTerms()) {			
			List<Integer> findings = hm.get(term);
			//StorageManager.saveDocument("hashmap", hm.toString(), 0);
			if(findings != null) {
				resultIds.add(findings);
			}
		}
		
		List<Integer> docIds = BooleanLogic.applyBooleanLogic(config, resultIds);
		List<Document> result = StorageManager.load(docIds, config.useStopwordElimination(), config.useStemming());
		
		LinearSearch ls = new LinearSearch();
		ls.getDocumentMatches(result, config);
		
		return result;
	}

	@Override
	public PrecisionAndRecall getPrecisionAndRecall(SearchConfiguration config, List<Document> result) {
		return new PrecisionAndRecall(config, result);
	}
}
