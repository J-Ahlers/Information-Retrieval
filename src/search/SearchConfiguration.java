package search;

import java.util.ArrayList;
import java.util.List;

public class SearchConfiguration {
	
	public static final int AND = 0;
	public static final int OR = 1;

	private int config = AND;
	private List<String> terms;
	private boolean useStopwordElimination;
	private boolean useStemming;
	private boolean showPrecisionRecall;
	private int strategy;
	
	public SearchConfiguration(List<String> terms, int strategy, boolean useStopwordElimination, boolean useStemming, boolean showPrecisionRecall) {
		List<String> tmp = new ArrayList<String>();
		for(int i = 0; i < terms.size(); i++) {
			if(terms.size() == 3 && i == 1) {
				String term1 = terms.get(1);
				if(term1.equals("or") || term1.equals("OR")) {
					this.config = OR;
					continue;
				}
				else if(term1.equals("and") || term1.equals("AND")) {
					continue;
				}
			}
			tmp.add(terms.get(i));
		}
		this.terms = tmp;
		this.useStemming = useStemming;
		this.useStopwordElimination = useStopwordElimination;
		this.strategy = strategy;
		this.showPrecisionRecall = showPrecisionRecall;
	}
	
	public List<String> getTerms() {
		return terms;
	}
	
	public void setTerms(List<String> terms) {
		this.terms = terms;
	}
	
	public int getType() {
		return config;
	}
	
	public boolean useStemming() {
		return useStemming;
	}
	
	public boolean useStopwordElimination() {
		return useStopwordElimination;
	}

	public boolean isShowPrecisionRecall() {
		return showPrecisionRecall;
	}

	public void setShowPrecisionRecall(boolean showPrecisionRecall) {
		this.showPrecisionRecall = showPrecisionRecall;
	}

	public int getStrategy() {
		return strategy;
	}

	public void setStrategy(int strategy) {
		this.strategy = strategy;
	}
	
}
