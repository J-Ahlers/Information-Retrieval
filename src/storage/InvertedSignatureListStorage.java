package storage;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import search.SearchConfiguration;
import utils.SignatureGenerator;
import model.Document;

public class InvertedSignatureListStorage {

	private static InvertedSignatureListStorage mInstance;
	private HashMap<BitSet, List<Integer>> map;
	
	private static Boolean mStopwordsEliminated;
	private static Boolean mStemming;
	
	private InvertedSignatureListStorage(SearchConfiguration config) {
		mStopwordsEliminated = config.useStopwordElimination();
		mStemming = config.useStemming();
		map = new HashMap<>();
		List<Document> docs = StorageManager.load(config.useStopwordElimination(), config.useStemming());
		SignatureGenerator sg = new SignatureGenerator();
		
		List<BitSet> voidSignatures = sg.getSignatures(new String[] {"", " "});
		
		for(Document doc : docs) {
			List<BitSet> signatures = sg.getSignatures(doc.getContent().split(" "));
			for(BitSet signature : signatures) {
				if(voidSignatures.contains(signature))
					continue;
				
				List<Integer> docIds = map.get(signature);
				if(docIds != null && !docIds.contains(doc.getId())) {
					docIds.add(doc.getId());
					map.put(signature, docIds);
					Collections.sort(docIds);
				}
				else if (docIds == null) {
					docIds = new ArrayList<>();
					docIds.add(doc.getId());
					map.put(signature, docIds);
				}
			}
		}
	}
	
	public static InvertedSignatureListStorage getInstance(SearchConfiguration config) {
		boolean nullInstance = mInstance == null;
		boolean stopwordsChanged = mStopwordsEliminated != null && mStopwordsEliminated != config.useStopwordElimination();
		boolean stemmingChanged = mStemming != null && mStemming != config.useStemming();
		if(nullInstance || stopwordsChanged || stemmingChanged) {
			mInstance = new InvertedSignatureListStorage(config);
		}
		return mInstance;
	}
	
	public HashMap<BitSet, List<Integer>> getHashMap() {
		return map;
	}
}

