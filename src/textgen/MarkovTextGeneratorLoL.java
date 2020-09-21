package textgen;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * An implementation of the MTG interface that uses a list of lists.
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList;

	// The starting "word"
	private String starter;

	// The random number generator
	private Random rnGenerator;

	public MarkovTextGeneratorLoL(Random generator) {
		reset();
		rnGenerator = generator;
	}

	private void reset() {
		wordList = new LinkedList<ListNode>();
		starter = "";
	}

	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText) {
		// TODO: Implement this method
		List<String> words = parseWords(sourceText);
		if (starter.isEmpty()) {
			starter =  words.get(0);
		}
		
		ListNode previousWord = null;
		for (String word : words) {
			ListNode currentWord = saveCurrentWord(word);
			if (previousWord != null) {
				previousWord.addNextWord(currentWord.getWord());
			}
			
			previousWord = currentWord;
		}
	}
	
	private ListNode saveCurrentWord(String word) {
		ListNode currentWord = findWord(word);
		if (currentWord == null) {
			currentWord = new ListNode(word);
			wordList.add(currentWord);
		} 
		
		return currentWord;
	}

	protected List<String> parseWords(String sourceText) {
		String pattern = "[!?.]+|[a-zA-Z]+";
		ArrayList<String> tokens = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile(pattern);
		Matcher m = tokSplitter.matcher(sourceText);

		while (m.find()) {
			String result = m.group();
			if (isWord(result)) {
				tokens.add(result);
			}
		}

		return tokens;
	}

	/**
	 * Take a string that either contains only alphabetic characters, or only
	 * sentence-ending punctuation. Return true if the string contains only
	 * alphabetic characters, and false if it contains end of sentence punctuation.
	 * 
	 * @param tok The string to check
	 * @return true if tok is a word, false if it is punctuation.
	 */
	private boolean isWord(String tok) {
		// Note: This is a fast way of checking whether a string is a word
		// You probably don't want to change it.
		return !(tok.indexOf("!") >= 0 || tok.indexOf(".") >= 0 || tok.indexOf("?") >= 0);
	}

	private ListNode findWord(String word) {
		for (ListNode ln : wordList) {
			if (ln.getWord().equalsIgnoreCase(word)) {
				return ln;
			}
		}
		return null;
	}

	/**
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
		String toReturn = "";
		ListNode currentWord = findWord(starter);
		for (int i =0; i <  numWords; i++) {
			toReturn += " " + currentWord.getWord();
			String nextWord = currentWord.getRandomNextWord(rnGenerator);
			currentWord =  findWord(nextWord != null ? nextWord: starter);
		}
		
		return toReturn;
	}

	// Can be helpful for debugging
	@Override
	public String toString() {
		String toReturn = "";
		for (ListNode n : wordList) {
			toReturn += n.toString();
		}
		return toReturn;
	}

	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText) {
		reset();
		train(sourceText);
	}

	// TODO: Add any private helper methods you need here.

	/**
	 * This is a minimal set of tests. Note that it can be difficult to test
	 * methods/classes with randomized behavior.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, " + "You say stop, and I say go, go, go, "
				+ "Oh no. You say goodbye and I say hello, hello, hello, "
				+ "I don't know why you say goodbye, I say hello, hello, hello, "
				+ "I don't know why you say goodbye, I say hello. " + "I say high, you say low, "
				+ "You say why, and I say I don't know. " + "Oh no. "
				+ "You say goodbye and I say hello, hello, hello. "
				+ "I don't know why you say goodbye, I say hello, hello, hello, "
				+ "I don't know why you say goodbye, I say hello. " + "Why, why, why, why, why, why, "
				+ "Do you say goodbye. " + "Oh no. " + "You say goodbye and I say hello, hello, hello. "
				+ "I don't know why you say goodbye, I say hello, hello, hello, "
				+ "I don't know why you say goodbye, I say hello. " + "You say yes, I say no, "
				+ "You say stop and I say go, go, go. " + "Oh, oh no. "
				+ "You say goodbye and I say hello, hello, hello. "
				+ "I don't know why you say goodbye, I say hello, hello, hello, "
				+ "I don't know why you say goodbye, I say hello, hello, hello, "
				+ "I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/**
 * Links a word to the next words in the list You should use this class in your
 * implementation.
 */
class ListNode {
	// The word that is linking to the next words
	private String word;

	// The next words that could follow it
	private List<String> nextWords;

	ListNode(String word) {
		this.word = word;
		nextWords = new LinkedList<String>();
	}

	public String getWord() {
		return word;
	}

	public void addNextWord(String nextWord) {
		nextWords.add(nextWord);
	}

	public String getRandomNextWord(Random generator) {
		// TODO: Implement this method
		// The random number generator should be passed from
		// the MarkovTextGeneratorLoL class
		if (nextWords.size() == 0) {
			return null;
		}
		
		int randomNum = generator.nextInt(nextWords.size());
		return nextWords.get(randomNum);
	}

	public String toString() {
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}

}
