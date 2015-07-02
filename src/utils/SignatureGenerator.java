package utils;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class SignatureGenerator {
	
	public static final int m = 12;
	public static final int F = 64;
	
	public List<BitSet> getSignatures(String[] words) {
		List<BitSet> bitSetList = new ArrayList<BitSet>();
		for (String word : words) {
			int hash = 1;
			int pi;
			int position;
			BitSet bitSet = new BitSet(F);
			while (hash <= m) {
				pi = primFinder(hash);
				position = hashFunction(word, pi);
				if (!bitSet.get(position)) {
					bitSet.set(position);
					hash++;
				}
			}
		}
		return bitSetList;
	}
	
	private int hashFunction(String word, int pi) {
		char[] l = word.toCharArray();
		int hash_i = 0;
		
		for(char wk : l)
			hash_i = (hash_i + wk) * pi;
		
		hash_i = hash_i % F;
		
		return hash_i;
	}
	
	private int primFinder(int maxPrim) {
		int primCount = 0;
		int prim = 2;
		int n = 2;
		int i = 2;
		while(primCount < maxPrim) {
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
