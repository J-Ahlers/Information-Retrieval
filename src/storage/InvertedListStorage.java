package storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import search.SearchConfiguration;
import model.Document;

public class InvertedListStorage {

	private static InvertedListStorage mInstance;
	private HashMap<String, List<Integer>> map;
	
	private static Boolean mStopwordsEliminated;
	private static Boolean mStemming;
	
	private InvertedListStorage(SearchConfiguration config) {
		mStopwordsEliminated = config.useStopwordElimination();
		mStemming = config.useStemming();
		map = new HashMap<>();
		List<Document> docs = StorageManager.load(config.useStopwordElimination(), config.useStemming());
		for(Document doc : docs) {
			String[] words = doc.getContent().split(" ");
			for(String word : words) {
				if(word.equals("") || word.equals(" "))
					continue;
				
				List<Integer> docIds = map.get(word);
				if(docIds != null && !docIds.contains(doc.getId())) {
					docIds.add(doc.getId());
					map.put(word, docIds);
					Collections.sort(docIds);
				}
				else if (docIds == null) {
					docIds = new ArrayList<>();
					docIds.add(doc.getId());
					map.put(word, docIds);
				}
			}
		}
	}
	
	public static InvertedListStorage getInstance(SearchConfiguration config) {
		boolean nullInstance = mInstance == null;
		boolean stopwordsChanged = mStopwordsEliminated != null && mStopwordsEliminated != config.useStopwordElimination();
		boolean stemmingChanged = mStemming != null && mStemming != config.useStemming();
		if(nullInstance || stopwordsChanged || stemmingChanged) {
			mInstance = new InvertedListStorage(config);
		}
		return mInstance;
	}
	
	public HashMap<String, List<Integer>> getHashMap() {
		return map;
	}
}

