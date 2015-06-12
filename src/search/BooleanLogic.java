package search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BooleanLogic {
	
	
	/**
	 * The Integer Lists passed here must be sorted already!
	 * @param terms
	 * @param docIds
	 * @return
	 */
	public static List<Integer> applyBooleanLogic(SearchConfiguration config, List<List<Integer>> docIds) {
		List<Integer> result = new ArrayList<>();		

		Iterator<List<Integer>> i = docIds.iterator();
		if(!docIds.isEmpty()) {
			List<Integer> start = i.next();
			result = start;
			while(i.hasNext()) {
				result = combineResults(config.getType(), result, i.next());
			}
		}
		
		return result;
	}

	private static List<Integer> combineResults(int type, List<Integer> list1, List<Integer> list2) {		
		List<Integer> result = new ArrayList<>();
		
		int i = 0;
		int j = 0;
		while(i < list1.size() || j < list2.size()) {
			if(i < list1.size() && j < list2.size()) {
				if(list1.get(i) == list2.get(j)) {
					result.add(list1.get(i));
					i++;
					j++;
				}
				else if(list1.get(i) < list2.get(j)) {
					if((result.isEmpty() || result.get(result.size()-1) < list1.get(i)) && type == SearchConfiguration.OR)
						result.add(list1.get(i));
					i++;
						
				}
				else {
					if((result.isEmpty() || result.get(result.size()-1) < list2.get(j)) && type == SearchConfiguration.OR)
						result.add(list2.get(j));
					j++;
						
				}
			}
			else if(type == SearchConfiguration.AND) {
				break;
			}
			else if(i < list1.size()) {
				result.add(list1.get(i));
				i++;
			}
			else if(j < list2.size()) {
				result.add(list2.get(j));
				j++;
			}
		}
		return result;
	}
}
