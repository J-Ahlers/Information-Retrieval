package search.bool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Document;
import search.BooleanLogic;
import search.Search;
import storage.InvertedListStorage;
import storage.StorageManager;
import utils.PrecisionAndRecall;
import utils.StopWordEliminator;

public class InvertedList implements Search {
	
	@Override
	public List<Document> getDocumentMatches(List<String> terms,
			boolean eliminateStopwords, boolean useStemming) {
		HashMap<String, List<Integer>> hm = InvertedListStorage.getInstance(eliminateStopwords, useStemming).getHashMap();
		return getDocumentMatches(hm, terms, eliminateStopwords, useStemming);
	}
	
	public static List<Document> getDocumentMatches(HashMap<String, List<Integer>> hm, List<String> terms,
			boolean eliminateStopwords, boolean useStemming) {
		List<List<Integer>> resultIds = new ArrayList<>();
		
		for(String term : terms) {
			if(!term.equals("or") && StopWordEliminator.isStopword(term))
				continue;
			
			List<Integer> findings = hm.get(term);
			if(findings != null) {
				resultIds.add(findings);
			}
		}
		
		List<Integer> docIds = BooleanLogic.applyBooleanLogic(terms, resultIds);		
		return StorageManager.load(docIds, eliminateStopwords, useStemming);
	}

	@Override
	public PrecisionAndRecall getPrecisionAndRecall(List<String> terms,
			List<Document> result) {
		return new PrecisionAndRecall(terms, result);
	}

}
