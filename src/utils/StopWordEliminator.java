/**
 * 
 */
package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import storage.StorageManager;

/**
 * @author Sophie Baschinski
 *
 */
public class StopWordEliminator {
	
	private List<String> stopWords;
	
	public StopWordEliminator() {
		loadStopWords();
	}
	
	public List<String> getCleanContent(String content) {
		List<String> cleanContent = new ArrayList<>();
		// replace unwanted stuff with an whitespace character
		String temp = removeLineBreaks(content);
		temp = removePunctuation(temp);
		temp = removeStopWords(temp);
		// splitting temp, ignoring multiple whitespace characters 
		cleanContent = Arrays.asList(temp.split("\\s+"));
		return cleanContent;
	}
	
	private String removeLineBreaks(String content) {
		return content.replace("\n", " ");
	}
	
	private String removePunctuation(String content) {
		String temp = content.replaceAll("[,.;!?:\"[](){}><]", " ");
		// replace apostrophe only if a whitespace character follows afterwards 
		temp = content.replaceAll("' ", " ");
		return temp;
	}
		
	private String removeStopWords(String content) {
		String temp = content;
		for (String stopWord : stopWords)
			temp = temp.replace(" " + stopWord + " ", " ");
		return temp;
	}
	
	private void loadStopWords() {
		String string = StorageManager.readFile("res/englishST.txt");
		stopWords =	Arrays.asList(string.split("\r\n"));
	}
}
