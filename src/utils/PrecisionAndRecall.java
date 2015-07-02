package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import model.Document;
import search.SearchConfiguration;
import search.bool.InvertedListSearch;
import storage.StorageManager;

public class PrecisionAndRecall {
	
	private List<Integer> searchResult;
	private List<Integer> trueResult;
	
	public PrecisionAndRecall(SearchConfiguration config, List<Document> result) {
		this.searchResult = Document.convert(result);
		
		HashMap<String, List<Integer>> truthMap = new HashMap<>();
		
		String theTruth = StorageManager.readFile("res/ground_truth.txt");
		theTruth = theTruth.replaceAll(" ,", ",");
		theTruth = theTruth.replaceAll(",  ", ", ");
		
		Scanner scanner = new Scanner(theTruth);
		Stemmer st = new Stemmer();
		while (scanner.hasNextLine()) {
		  String line = scanner.nextLine();
		  if(line.startsWith("#") || !line.contains("-"))
				continue;
			
			String[] subparts = line.split(" - ");
			String word = subparts[0];
			if(config.useStemming())
				word = st.getStemmedContent(word);
			if(config.useStopwordElimination() && StopWordEliminator.isStopword(word))
				continue;
				

			subparts = subparts[1].split(", ");
			
			List<Integer> ids = new ArrayList<>();
			for(String id : subparts)
				ids.add(Integer.valueOf(id));
			
			truthMap.put(word, ids);
		}
		scanner.close();
		
		boolean validQuery = true;
		for(String term : config.getTerms()) {
			if(truthMap.get(term) == null) {
				validQuery = false;
				break;
			}
		}
		
		if(validQuery)
			this.trueResult = Document.convert(InvertedListSearch.getDocumentMatches(truthMap, config));
	}
	
	public double getPrecision() {
		if(this.trueResult == null)
			return -1;
		
		int truePositives = 0;
		for(Integer doc : searchResult) {
			if(trueResult.contains(doc))
				truePositives++;
		}
		if(searchResult.size() == 0 && truePositives > 0)
			return 0;
		
		return ((double) truePositives / (double) searchResult.size());
	}
	
	public double getRecall() {
		if(this.trueResult == null)
			return -1;
		
		int truePositives = 0;
		for(Integer doc : searchResult) {
			if(trueResult.contains(doc))
				truePositives++;
		}
		if(trueResult.size() == 0)
			return 1;
		
		return ((double) truePositives / (double) trueResult.size());
	}

}
