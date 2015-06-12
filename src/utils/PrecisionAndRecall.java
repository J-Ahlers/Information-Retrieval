package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import model.Document;
import search.SearchConfiguration;
import search.bool.InvertedList;
import storage.StorageManager;

public class PrecisionAndRecall {
	
	private List<Document> searchResult;
	private List<Document> trueResult;
	
	public PrecisionAndRecall(SearchConfiguration config, List<Document> result) {
		this.searchResult = result;
		
		HashMap<String, List<Integer>> map = new HashMap<>();
		
		String theTruth = StorageManager.readFile("/res/ground_truth.txt");
		theTruth = theTruth.replaceAll(" ,", ",");
		
		String[] parts = theTruth.split("\r\n");
		for(String line : parts) {
			if(line.startsWith("#") || !line.contains("-"))
				continue;
			
			String[] subparts = line.split(" - ");
			String word = subparts[0];
			subparts = subparts[1].split(", ");
			
			List<Integer> ids = new ArrayList<>();
			for(String id : subparts)
				ids.add(Integer.valueOf(id));
			
			map.put(word, ids);
		}
		this.trueResult = InvertedList.getDocumentMatches(map, config, false, false);
	}
	
	public double getPrecision() {
		int truePositives = 0;
		for(Document doc : searchResult) {
			if(trueResult.contains(doc))
				truePositives++;
		}
		return ((double) truePositives / (double) searchResult.size());
	}
	
	public double getRecall() {
		int truePositives = 0;
		for(Document doc : searchResult) {
			if(trueResult.contains(doc))
				truePositives++;
		}
		return ((double) truePositives / (double) trueResult.size());
	}

}
