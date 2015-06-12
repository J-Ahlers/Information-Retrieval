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
	private SearchConfiguration config;
	
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
	public List<Document> getDocumentMatches(SearchConfiguration config) {
		if(config.useStopwordElimination()) {
			List<String> tmpTerms = new ArrayList<>();
			for(String term : config.getTerms()) {
				if(StopWordEliminator.isStopword(term))
					continue;
				else
					tmpTerms.add(term);
			}
			config.setTerms(tmpTerms);
		}
		
		if(config.getTerms().isEmpty() || config.getTerms().get(0).equals(""))
			return new ArrayList<Document>();
		
		if(config.useStemming()) {
			List<String> tmpTerms = new ArrayList<>();
			Stemmer st = new Stemmer();
			for(String term : config.getTerms()) {
				tmpTerms.add(st.getStemmedContent(term));
			}
			config.setTerms(tmpTerms);
		}
		
		this.result = search.getDocumentMatches(config);
		this.config = config;
		return result;
	}

	/**
	 * returns the precision and recall for the specified result
	 */
	public PrecisionAndRecall getPrecisionAndRecall(SearchConfiguration config, List<Document> result) {
		return search.getPrecisionAndRecall(config, result);
	}

	/**
	 * returns precision and recall for the last search
	 * @return
	 */
	public PrecisionAndRecall getPrecisionAndRecall() {
		return search.getPrecisionAndRecall(config, result);
	}
}
