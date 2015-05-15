/**
 * 
 */
package utils;

import java.util.Arrays;
import java.util.List;

import storage.StorageManager;

/**
 * @author Sophie Baschinski
 *
 */
public class StopWordEliminator {
	
	public String getCleanContent(String content) {
		// replace unwanted stuff with an whitespace character
		String temp = removeLineBreaks(content);
		temp = removePunctuation(temp);
		temp = " "+temp+" ";
		
		return temp.replace("\\s+", " ");
	}
	
	public String elimateStopwords(String str) {
		return removeStopWords(str);
	}
	
	private String removeLineBreaks(String content) {
		return content.replace("\n", " ");
	}
	
	private String removePunctuation(String content) {
		//String temp = content.replaceAll("[,.;!?:\"[](){}><]", " ");
		String temp = content.replaceAll("[,.;!?:\"\\[\\](){}><]", " ");
		// replace apostrophe only if a whitespace character follows afterwards 
		temp = temp.replaceAll("' ", " ");
		return temp;
	}
		
	private String removeStopWords(String content) {
		String temp = content;
		for (String stopWord : StopwordList.getInstance().getStopwords())
			temp = temp.replace(" " + stopWord + " ", " ");
		return temp;
	}
	
	public static boolean isStopword(String word) {
		return StopwordList.getInstance().getStopwords().contains(word);
	}
	
	private static class StopwordList {
		
		private static StopwordList mInstance;
		private List<String> stopWords;
		
		private StopwordList() {
			String string = StorageManager.readFile("res/englishST.txt");
			stopWords =	Arrays.asList(string.split("\r\n"));
		}
		
		public static StopwordList getInstance() {
			if(mInstance == null)
				mInstance = new StopwordList();
			
			return mInstance;				
		}
		
		public List<String> getStopwords() {
			return stopWords;
		}
	}
}
