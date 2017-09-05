package dictionary.utils;

import java.util.Scanner;

public class MainTest {
	
	private static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		
		WordsChain wc = new WordsChain();
		wc.loadDictFromFile();
		System.out.println("Give two words: ");
		String wordStart = sc.nextLine();
		String wordEnd = sc.nextLine();
		wc.getChainWords(wordStart, wordEnd);
		System.out.println(wc.getResultChain());
		

	}

}
