package core;

import search.Search;
import search.SearchImpl;

public class RetrievalSystem {

	public static String workingDirectory;
	
	public static void main(String[] args) {
		
		Search search = new SearchImpl(Search.STRATEGY_LINEAR);
		search.getDocumentMatches(null);
		
		System.out.println("test");
	}

}
