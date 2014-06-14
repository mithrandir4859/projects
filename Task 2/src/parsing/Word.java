package parsing;

import java.util.HashMap;
import java.util.Map;

public class Word implements Comparable<Word>{
	
	private String word;
	
	private Map<Character, Integer> charToCountMap = new HashMap<Character, Integer>();

	public Word(String word) {
		super();
		this.word = word;
		for (int i = 0; i < word.length(); i++){
			Character currentChar = word.charAt(i);
			Integer currentCounter = charToCountMap.get(currentChar);
			charToCountMap.put(currentChar, currentCounter == null ? 1 : ++currentCounter);
		}
	}
	
	public int contains(char c){
		Integer counter = charToCountMap.get(c);
		return counter == null ? 0 : counter;
	}

	@Override
	public int hashCode() {
		return word.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && obj.getClass() == getClass() && word.equals(((Word) obj).word);
	}

	@Override
	public String toString() {
		return word;
	}

	@Override
	public int compareTo(Word other) {
		return word.compareTo(other.word);
	}

}
