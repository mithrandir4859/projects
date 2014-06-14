package parsing;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Text {
	private static final String PATTERN_NONWORD_CHARS = "[\\W]+";
	private static final String PATTERN_NONWORD_CHARS_IN_THE_END = ".*" + PATTERN_NONWORD_CHARS;
	private static final String PATTERN_NONWORD_CHARS_AT_START = PATTERN_NONWORD_CHARS + ".*";
	private Scanner scanner;
	
	Text(String filename) throws FileNotFoundException{
		scanner = new Scanner(new File(filename));
	}
	

	/**
	 * Все слова текста рассортировать по возрастанию количества
	 * заданной буквы в слове. Слова с одинаковым количеством
	 * расположить в алфавитном порядке.
	 */
	public List<Word> getSortedWords(char letter){
		List<Word> wordList = new ArrayList<Word>(loadWordsFromFile());
		Collections.sort(wordList, new SpecificStringComparator(letter));
		return wordList;
	}
	
	private Set<Word> loadWordsFromFile(){
		Set<Word> wordSet = new HashSet<Word>();
		while (scanner.hasNext()){
			String word = scanner.next();
			if (word.matches(PATTERN_NONWORD_CHARS_IN_THE_END) || word.matches(PATTERN_NONWORD_CHARS_AT_START))
				word = word.replaceAll(PATTERN_NONWORD_CHARS, "");
			if (word.length() == 0)
				continue;
			wordSet.add(new Word(word.toLowerCase()));
		}
		return wordSet;
	}
	
	private static class SpecificStringComparator implements Comparator<Word>{
		
		private char letter;
		
		SpecificStringComparator(char letter){
			this.letter = letter;
		}
		
		@Override
		public int compare(Word word, Word other) {
			int difference = word.contains(letter) - other.contains(letter);
			return difference == 0 ? word.compareTo(other) : -difference;
		}
		
	}

}
