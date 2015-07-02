package search.bool;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import model.Document;
import search.BooleanLogic;
import search.SearchConfiguration;
import search.SearchImpl;
import storage.InvertedSignatureListStorage;
import storage.StorageManager;
import utils.PrecisionAndRecall;
import utils.SignatureGenerator;

public class SignatureSearch extends SearchImpl {

	@Override
	public List<Document> getDocumentMatches(SearchConfiguration config) {
		Map<BitSet, List<Integer>> hm = InvertedSignatureListStorage.getInstance(config).getHashMap();
		return getDocumentMatches(hm, config);
	}
	
	public static List<Document> getDocumentMatches(Map<BitSet, List<Integer>> hm, SearchConfiguration config) {
		List<List<Integer>> resultIds = new ArrayList<>();
		SignatureGenerator sg = new SignatureGenerator();
		
		String[] words = new String[config.getTerms().size()];
		words = config.getTerms().toArray(words);
		
		List<BitSet> terms = sg.getSignatures(words, false);
		for(BitSet term : terms) {
			List<Integer> findings = new ArrayList<>();
			
			Map<BitSet, List<Integer>> finalMap = Collections.unmodifiableMap(hm);

			Iterator<Entry<BitSet, List<Integer>>> it = finalMap.entrySet().iterator();
		    while (it.hasNext()) {
		        Entry<BitSet, List<Integer>> pair = it.next();
		        BitSet set = pair.getKey();
		        BitSet tmp = (BitSet) set.clone();
		        tmp.and(term);
		        
		        if(tmp.equals(term)) {
		        	for(Integer docID : pair.getValue()) {
		        		if(!findings.contains(docID)) {
		        			findings.add(docID);
		        		}
		        	}
		        }
		        //set.isEmpty();
		    }
			//StorageManager.saveDocument("hashmap", hm.toString(), 0);
			if(!findings.isEmpty()) {
				Collections.sort(findings);
				resultIds.add(findings);
			}
		}
		
		List<Integer> docIds = BooleanLogic.applyBooleanLogic(config, resultIds);
		List<Document> result = StorageManager.load(docIds, config.useStopwordElimination(), config.useStemming());
		
		LinearSearch ls = new LinearSearch();
		return ls.getDocumentMatches(result, config);
	}

	@Override
	public PrecisionAndRecall getPrecisionAndRecall(SearchConfiguration config, List<Document> result) {
		return new PrecisionAndRecall(config, result);
	}
}
