/**
 * 
 */
package utils;

import java.util.Arrays;
import java.util.List;

import storage.StorageManager;

/**
 * eliminates stopwords in given content string
 * 
 * @author Sophie Baschinski
 *
 */
public class StopWordEliminator {
	/**
	 * removes line breaks, punctuation and whitespace characters
	 * 
	 * @param content the content which should get cleaned
	 * @return the cleaned content string
	 */
	public String getCleanContent(String content) {
		String temp = removeLineBreaks(content);
		temp = removePunctuation(temp);
		// to ensure at least one whitespace character between words
		temp = " "+temp+" ";
		// to ensure exactly one whitespace character between words
		return temp.replace("\\s+", " ");
	}
	
	public String elimateStopwords(String str) {
		return removeStopWords(str);
	}
	
	/**
	 * exchange line breaks with a whitespace character
	 * 
	 * @param content the content to clean
	 * @return the cleaned content
	 */
	private String removeLineBreaks(String content) {
		String cleaned = content.replace("\r\n", " ");
		cleaned = cleaned.replace("\n", " ");
		return cleaned;
	}
	
	/**
	 * exchange punctuation and special chars with a whitespace character
	 * 
	 * @param content the content to clean
	 * @return the cleaned content
	 */
	private String removePunctuation(String content) {
		String temp = content.replaceAll("[,.;!?:\"\\[\\](){}><]", " ");
		// replace apostrophe only if a whitespace character follows afterwards 
		temp = temp.replaceAll("' ", " ");
		return temp;
	}
	
	/**
	 * exchange stopwords with a whitespace character
	 * 
	 * @param content the content to clean
	 * @return the cleaned content
	 */
	private String removeStopWords(String content) {
		String temp = content;
		for (String stopWord : StopwordList.getInstance().getStopwords())
			temp = temp.replace(" " + stopWord + " ", " ");
		return temp;
	}
	
	/**
	 * check if a word is a stopword
	 * 
	 * @param word the word to check
	 * @return true if word is a stopword
	 */
	public static boolean isStopword(String word) {
		return StopwordList.getInstance().getStopwords().contains(word);
	}
	
	/**
	 * class that creates a list with all the stopwords
	 * 
	 * @author Jonas Ahlers
	 *
	 */
	private static class StopwordList {
		
		private static StopwordList mInstance;
		private List<String> stopWords;
		
		/**
		 * creates a list with stopwords from a file
		 */
		private StopwordList() {
			String string = StorageManager.readFile("res/englishST.txt");
			stopWords =	Arrays.asList(string.split("\r\n"));
		}
		
		/**
		 * creates an instance
		 * @return a StopwordList that contains a list of stopwords
		 */
		public static StopwordList getInstance() {
			if(mInstance == null)
				mInstance = new StopwordList();
			
			return mInstance;				
		}
		
		/**
		 * returns the stopwords as a list of strings
		 * @return a list of strings that contains all stopwords
		 */
		public List<String> getStopwords() {
			return stopWords;
		}
	}
}
