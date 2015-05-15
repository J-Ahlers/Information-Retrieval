package core;

import search.Search;
import search.SearchImpl;
import storage.FileSplitter;

public class RetrievalSystem {

	public static String workingDirectory;
	
	public static void main(String[] args) {
		
		workingDirectory = args[1];
		Search search = new SearchImpl(Search.STRATEGY_LINEAR);
		search.getDocumentMatches(null);
		
		FileSplitter fs = new FileSplitter();
		fs.split(args[0]);
	}

}
