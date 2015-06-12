/**
 * 
 */
package search;

import java.util.ArrayList;
import java.util.List;

import model.Document;
import search.bool.InvertedList;
import search.bool.LinearSearch;
import utils.PrecisionAndRecall;
import utils.Stemmer;
import utils.StopWordEliminator;

/**
 * @author Jonas Ahlers
 *
 */
public class SearchImpl implements Search {

	private Search search;
	private List<Document> result;
	private List<String> terms;
	
	/**
	 * starts at the moment just the linear search
	 * 
	 * @param strategy the strategy for the search
	 */
	public SearchImpl(int strategy) {
		switch(strategy) {
		case STRATEGY_LINEAR:
			this.search = new LinearSearch();
			break;
		case STRATEGY_INVERTED_LIST:
			this.search = new InvertedList();
			break;
		default:
			this.search = new LinearSearch();		
		}
	}
	
	protected SearchImpl() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public List<Document> getDocumentMatches(List<String> terms, boolean eliminateStopwords, boolean useStemming) {
		if(eliminateStopwords) {
			List<String> tmpTerms = new ArrayList<>();
			for(String term : terms) {
				if(StopWordEliminator.isStopword(term))
					continue;
				else
					tmpTerms.add(term);
			}
			terms = tmpTerms;
		}
		
		if(terms.isEmpty() || terms.get(0).equals(""))
			return new ArrayList<Document>();
		
		if(useStemming) {
			List<String> tmpTerms = new ArrayList<>();
			Stemmer st = new Stemmer();
			for(String term : terms) {
				tmpTerms.add(st.getStemmedContent(term));
			}
			terms = tmpTerms;
		}
		
		this.result = search.getDocumentMatches(terms, eliminateStopwords, useStemming);
		this.terms = terms;
		return result;
	}

	/**
	 * returns the precision and recall for the specified result
	 */
	public PrecisionAndRecall getPrecisionAndRecall(List<String> terms, List<Document> result) {
		return search.getPrecisionAndRecall(terms, result);
	}

	/**
	 * returns precision and recall for the last search
	 * @return
	 */
	public PrecisionAndRecall getPrecisionAndRecall() {
		return search.getPrecisionAndRecall(terms, result);
	}
}
