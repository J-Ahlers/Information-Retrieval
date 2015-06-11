package search;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BooleanLogic {
	
	private static final int AND = 0;
	private static final int OR = 1;
	
	
	/**
	 * The Integer Lists passed here must be sorted already!
	 * @param terms
	 * @param docIds
	 * @return
	 */
	public static List<Integer> applyBooleanLogic(List<String> terms, List<List<Integer>> docIds) {
		List<Integer> result = new ArrayList<>();
		int type = terms.size() == 3 && terms.get(1).equals("or") ? OR : AND;
		

		Iterator<List<Integer>> i = docIds.iterator();
		List<Integer> start = i.next();
		while(i.hasNext()) {
			result = combineResults(type, start, i.next());
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
				else if(list1.get(i) < list1.get(j)) {
					if(result.get(result.size()) < list1.get(i) && type == OR)
						result.add(list1.get(i));
					i++;
						
				}
				else if(list2.get(j) < list1.get(i)) {
					if(result.get(result.size()) < list2.get(j) && type == OR)
						result.add(list2.get(j));
					j++;
						
				}
			}
			else if(type == AND) {
				break;
			}
			else if(i < list1.size()) {
				result.add(list1.get(i));
				i++;
			}
		}
		return result;
	}
}
