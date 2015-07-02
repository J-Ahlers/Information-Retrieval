package utils;

import java.util.BitSet;
import java.util.List;

public class SignatureGenerator {
	
	public List<BitSet> getSignatures(String[] words) {
		
		return null;
	}

	
	private int hashFunction(int i) {
		
		
		
		return 0;
	}
	
	private int primFinder(int maxPrim) {
		
		int primCount = 0;
		int prim = 2;
		int n;
		int i = 2;
		while(primCount < maxPrim) {
			n = 2;

			// Schleife ueber alle moeglichen Teiler n des Primzahlkandidaten i:
			while (i % n != 0 && n <= i / 2) {
				// Erhoehe n solange, wie i nicht nurch n teilbar ist und die
				// Obergrenze noch nicht erreicht ist
				n = n + 1;
			}

			// Falls die Schleife bis zur Obergrenze i/2 durchlaufen wurde:
			if (n >= i / 2 + 1 && i != 1) {
				System.out.println(i + " ist eine Primzahl.");
			}
			// andernfalls wurde die Schleife vorher abgebrochen, weil i durch n
			// teilbar war -> keine Primzahl
		}
	}
}
