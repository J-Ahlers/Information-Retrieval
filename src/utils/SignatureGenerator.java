package utils;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class SignatureGenerator {
	
	public static final int M = 12;
	public static final int F = 64;
	public static final int D = 4;
	private int startvalue = primFinder(F);
	
	public List<BitSet> getSignatures(String[] words) {
		return getSignatures(words, true);
	}
	
	public List<BitSet> getSignatures(String[] words, boolean combine) {
		List<BitSet> bitSetList = new ArrayList<BitSet>();
		for (String word : words) {
			int hash = 1;
			int setBits = 0;
			int pi;
			int position;
			BitSet bitSet = new BitSet(F);
			while (setBits < M && !word.equals("") && !word.equals(" ")) {
				pi = primFinder(hash);
				position = hashFunction(word, pi);
				if (!bitSet.get(position)) {
					bitSet.set(position);
					setBits++;
				}
				hash++;
				if(hash > 100)
					break;
			}
			bitSetList.add(bitSet);
		}
		
		if(combine)
			return combineBlocks(bitSetList);
		else
			return bitSetList;
	}
	
	private List<BitSet> combineBlocks(List<BitSet> words) {
		List<BitSet> result = new ArrayList<BitSet>();
		
		BitSet tmp = new BitSet(F);		
		for(int i = 0; i < words.size(); i++) {
			if(i % D == 0) {
				tmp.or(words.get(i));
				result.add(tmp);
				tmp = new BitSet(F);
			} else {
				tmp.or(words.get(i));
			}
		}
		
		return result;
	}
	
	private int hashFunction(String word, int pi) {
		char[] l = word.toCharArray();
		long hash_i = startvalue;
		long hash_i_tmp = 0;
		for(char wk : l) {
			hash_i = (hash_i + wk) * pi;
			if(hash_i < 0)
				hash_i = hash_i * 1;
			hash_i_tmp = hash_i;
		}
		
		hash_i = hash_i % F;
		hash_i_tmp = hash_i_tmp * 1;
		return (int) Math.abs(hash_i);
	}
	
	private int primFinder(int maxPrim) {
		int primCount = 0;
		int prim = 2;
		int i = 2;
		while(primCount < maxPrim) {
			int n = 2;
			
			while (i % n != 0 && n <= i / 2) {
				n = n + 1;
			}

			if (n >= i / 2 + 1 && i != 1) {
				primCount++;
				prim = i;
			}
			
			i++;
		}
		return prim;
	}
}
