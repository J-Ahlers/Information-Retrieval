/**
 * 
 */
package utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * uses stemming on any content string
 * 
 * @author Sophie Baschinski
 *
 */
public class Stemmer {
	
	private List<String> contentWords;
	private List<String> stemmedWords;
	
	/**
	 * removes suffixes
	 * 
	 * @param content the content which should get stemmed
	 * @return the stemmed content string
	 */
	public String getStemmedContent(String content) {
		content = content.replace("'", "");
		contentWords = Arrays.asList(content.split(" "));
		stemmedWords = new ArrayList<String>();
		String stem;
		for (String word : contentWords) {
			if(word.equals(""))
				continue;
			System.out.println(word);
			stem = step1A(word);
			System.out.println(stem);
			stem = step1b(stem);
			System.out.println(stem);
			stem = step1c(stem);
			System.out.println(stem);
			stem = step2(stem);
			System.out.println(stem);
			stem = step3(stem);
			System.out.println(stem);
			stem = step4(stem);
			System.out.println(stem);
			stem = step5a(stem);
			System.out.println(stem);
			stem = step5b(stem);
			System.out.println(stem);
			stemmedWords.add(stem);
		}
		
		String stemmedWordsString = "";
		for (String s : stemmedWords)
		{
			stemmedWordsString += s + " ";
		}
		
		return stemmedWordsString;
	}

	private String step1A(String word) {
		if (word.endsWith("sses")) {
			word = word.substring(0, word.length() - 4) + "ss";
			return word;
		} 
		else if (word.endsWith("ies")) {
			word = word.substring(0, word.length() - 3) + "i";
			return word;
		} 
		else if (word.endsWith("ss")) {
			return word;
		} 
		else if (word.endsWith("s")) {
			word = word.substring(0, word.length() - 1);
			return word;
		} 
		else {
			return word;
		}
	}
	
	private String step1b(String word) {
		String stem;
		
		if (word.endsWith("eed")) {
			return checkM(word, "eed", "ee", 0);
		} 
		else if (word.endsWith("ed") || word.endsWith("ing")) {
			if (word.endsWith("ed"))
				stem = word.substring(0, word.length() - 2);
			else 
				stem = word.substring(0, word.length() - 3);
			
			if (containsVowel(stem)) {
				word = stem;
				word = step1bEnhanced(word);
				return word;
			} 
			else {
				return word;
			}
		} 
		else {
			return word;
		}
	}

	private String step1bEnhanced(String word) {
		if (word.endsWith("at") || word.endsWith("bl") || word.endsWith("iz")) {
			word = word + "e";
			return word;
		} 
		else if (endsWithDouble(word) && !(word.endsWith("l") || word.endsWith("s") || word.endsWith("z"))) {
			word = word.substring(0, word.length() - 1);
			return word;
		} 
		else if (endsWithCVC(word)) {
			int m = calculateM(word);
			if (m == 1) {
				word = word + "e";
				return word;
			} 
			else {
				return word;
			}
		} 
		else {
			return word;
		}
	}

	private String step1c(String word) {
		if( word.length() - 2 == -1)
			System.out.println(word);
		String stem = word.substring(0, word.length() - 1);
		if (word.endsWith("y") && containsVowel(stem)) {
			word = stem + "i";
			return word;
		} 
		else {
			return word;
		} 
	}

	private String step2(String word) {
		char penultimateLetter;
		if (word.length() > 1)
			penultimateLetter = word.charAt(word.length() - 2);
		else
			penultimateLetter = 'z';
			
		switch (penultimateLetter) {
		case 'a':
			if (word.endsWith("ational")) {
				return checkM(word, "ational", "ate", 0);
			} 
			else if (word.endsWith("tional")) {
				return checkM(word, "tional", "tion", 0);
			} 
			return word;
		case 'c':
			if (word.endsWith("enci")) {
				return checkM(word, "enci", "ence", 0);
			} 
			else if (word.endsWith("anci")) {
				return checkM(word, "anci", "ance", 0);
			} 
			return word;
		case 'e':
			if (word.endsWith("izer")) {
				return checkM(word, "izer", "ize", 0);
			}
			return word;
		case 'l':
			if (word.endsWith("abli")) {
				return checkM(word, "abli", "able", 0);
			} 
			else if (word.endsWith("alli")) {
				return checkM(word, "alli", "al", 0);
			} 
			else if (word.endsWith("entli")) {
				return checkM(word, "entli", "ent", 0);
			}
			else if (word.endsWith("eli")) {
				return checkM(word, "eli", "e", 0);
			} 
			else if (word.endsWith("ousli")) {
				return checkM(word, "ousli", "ous", 0);
			} 
			return word;
		case 'o':
			if (word.endsWith("ization")) {
				return checkM(word, "ization", "ize", 0);
			} 
			else if (word.endsWith("ation")) {
				return checkM(word, "ation", "ate", 0);
			} 
			else if (word.endsWith("ator")) {
				return checkM(word, "ator", "ate", 0);
			}
			return word;
		case 's':
			if (word.endsWith("alism")) {
				return checkM(word, "alism", "al", 0);
			} 
			else if (word.endsWith("iveness")) {
				return checkM(word, "iveness", "ive", 0);
			} 
			else if (word.endsWith("fulness")) {
				return checkM(word, "fulness", "ful", 0);
			}
			else if (word.endsWith("ousness")) {
				return checkM(word, "ousness", "ous", 0);
			}
			return word;
		case 't':
			if (word.endsWith("aliti")) {
				return checkM(word, "aliti", "al", 0);
			} 
			else if (word.endsWith("iviti")) {
				return checkM(word, "iviti", "ive", 0);
			} 
			else if (word.endsWith("biliti")) {
				return checkM(word, "biliti", "ble", 0);
			}
			return word;
		default:
			return word;
		}
	}
	
	private String step3(String word) {
		if (word.endsWith("icate")) {
			return checkM(word, "icate", "ic", 0);
		} 
		else if (word.endsWith("ative")) {
			return checkM(word, "ative", "", 0);
		} 
		else if (word.endsWith("alize")) {
			return checkM(word, "alize", "al", 0);
		}
		else if (word.endsWith("iciti")) {
			return checkM(word, "iciti", "ic", 0);
		} 
		else if (word.endsWith("ical")) {
			return checkM(word, "ical", "ic", 0);
		} 
		else if (word.endsWith("ful")) {
			return checkM(word, "ful", "", 0);
		} 
		else if (word.endsWith("ness")) {
			return checkM(word, "ness", "", 0);
		} 
		return word;
	}

	private String step4(String word) {
		if (word.endsWith("al")) {
			return checkM(word, "al", "", 1);
		} 
		else if (word.endsWith("ance")) {
			return checkM(word, "ance", "", 1);
		} 
		else if (word.endsWith("ence")) {
			return checkM(word, "ence", "", 1);
		}
		else if (word.endsWith("ar")) {
			return checkM(word, "ar", "", 1);
		} 
		else if (word.endsWith("ic")) {
			return checkM(word, "ic", "", 1);
		} 
		else if (word.endsWith("able")) {
			return checkM(word, "able", "", 1);
		} 
		else if (word.endsWith("ible")) {
			return checkM(word, "ible", "", 1);
		} 
		else if (word.endsWith("ant")) {
			return checkM(word, "ant", "", 1);
		} 
		else if (word.endsWith("ement")) {
			return checkM(word, "ement", "", 1);
		} 
		else if (word.endsWith("ment")) {
			return checkM(word, "ment", "", 1);
		}
		else if (word.endsWith("ent")) {
			return checkM(word, "ent", "", 1);
		} 
		else if (word.endsWith("ion")) {
			if (word.substring(0, word.length() - 3).endsWith("s") || word.substring(0, word.length() - 3).endsWith("t")) {
				return checkM(word, "ion", "", 1);
			}
			else {
				return word;
			}
		} 
		else if (word.endsWith("ou")) {
			return checkM(word, "ou", "", 1);
		} 
		else if (word.endsWith("ism")) {
			return checkM(word, "ism", "", 1);
		} 
		else if (word.endsWith("ate")) {
			return checkM(word, "ate", "", 1);
		}
		else if (word.endsWith("iti")) {
			return checkM(word, "iti", "", 1);
		} 
		else if (word.endsWith("ous")) {
			return checkM(word, "ous", "", 1);
		} 
		else if (word.endsWith("ive")) {
			return checkM(word, "ive", "", 1);
		} 
		else if (word.endsWith("ize")) {
			return checkM(word, "ize", "", 1);
		} 
		return word;
	}

	private String step5a(String word) {
		if (word.endsWith("e")) {
			String stem = word.substring(0, word.length() - 1);
			int m = calculateM(stem);
			if (m > 1) {
				return stem;
			} 
			else if (m == 1 && !endsWithCVC(stem)) {
				return stem;
			}
			else {
				return word;
			}
		}
		else {
				return word;
		}
	}

	private String step5b(String word) {
		if (word.length() > 1 && endsWithDouble(word) && word.endsWith("l")) {
			int m = calculateM(word);
			if (m > 1) {
				word = word.substring(0, word.length() - 1);
				return word;
			}
		} 
		return word;
	}

	private String checkM(String word, String oldSuffix, String newSuffix, int num) {
		String stem = word.substring(0, word.length() - oldSuffix.length());
		int m = calculateM(stem);
		if (m > num) {
			word = stem + newSuffix;
			return word;
		} 
		else {
			return word;
		}
	}
	
	//[C]VC[V]
	private int calculateM(String stem) {
		int m = 0;
		String restStem;
		
		// erase all first consonants
		int i = 0;
		while (!containsVowel(String.valueOf(stem.charAt(i)))) {
			i++;
		}
		restStem = stem.substring(i, stem.length());
		
		// erase all last vowels
		i = restStem.length() - 1;
			
		while (i != 0 && containsVowel(String.valueOf(restStem.charAt(i)))) {
			i--;
		}
		restStem = stem.substring(0, i + 1);
		
		if (i > 0) {
			m = containsVCviaPattern(restStem);
			return m;
		}
		else { 
			return 0;
		}
	}
	
	private int containsVCviaPattern(String restStem) {
		int m = 0;
		String pattern = "([^aeiou]y|[aeiou]+)[^aeiou]+";
		// Alternative: ([aeiou]+|([^aeiou]y))+[^aeiou]+
		Matcher matcher = Pattern.compile(pattern).matcher(restStem);
		while (matcher.find()) {
			m++;
		}
		return m;
	}	
	
//	private int containsVC(String restStem) {
//		int i = 0;
//		int m = 0;
//		while (!restStem.isEmpty()) {
//			while(containsVowel(String.valueOf(restStem.charAt(i)))) {
//				i++;
//			}
//			restStem = restStem.substring(i, restStem.length());
//			i = 0;
//			
//			while (!containsVowel(String.valueOf(restStem.charAt(i)))) {
//				i++;
//				if (i == restStem.length())
//					break;
//			}
//			if (i == restStem.length()) {
//				restStem = "";
//			}
//			else {
//				restStem = restStem.substring(i, restStem.length());
//			}
//			m++;
//		}
//		return m;
//	}

	private boolean containsVowel(String stem) {
		String pattern = ".*[aeiou]|[^aeiou]y.*";
		Matcher matcher = Pattern.compile(pattern).matcher(stem);
		return matcher.find();
	}
	
	private boolean endsWithDouble(String word) {
		return (word.charAt(word.length() - 1) == word.charAt(word.length() - 2));
	}
	
	private boolean endsWithCVC(String word) {
		String pattern = ".*[^aeiou][aeiouy][^aeiouwxy]";
		Matcher matcher = Pattern.compile(pattern).matcher(word);
		return matcher.find();
	}
}
