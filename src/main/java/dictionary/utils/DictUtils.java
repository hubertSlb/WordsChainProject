package dictionary.utils;

import java.util.List;

public class DictUtils {
	
	public static boolean checkWordsLength(String w1, String w2) {
		if ((w1.length() != w2.length()) || (w1.length() < 3 || w2.length() < 3)) {
			System.err.println("Validation error\nDifferent length of words or word length less than 3 characters");
			return false;
		}		
		return true;
	}
	
	public static boolean checkDictForWord(String word, List<String> selectDict) {		
		if(selectDict != null && selectDict.size() > 0 && selectDict.contains(word))
			return true;
		else
			System.err.println("Validation error\nNo word in dictionary [ " + word + " ]");
		
		return false;
	}
	
	public static int oneCharDiff(String word1, String word2) {
		char[] w1 = word1.toCharArray();
		char[] w2 = word2.toCharArray();
		int len = word1.length();
		int count = 0;
		int index = -1;
		for (int i=0; i<len; i++) {
			if ((w1[i] != w2[i]) && (i != index)) {
				count++;
				if (count > 1)
					break;
				index = i;
			}
		}
		return count == 1 ? index : -1;
	}
	
	public static int checkCharInWordEnd(int idx, String word, String wordEnd) {
		char[] arrWordEnd = wordEnd.toCharArray();
		char c = word.charAt(idx);
		if (arrWordEnd[idx] == c)
			return 1;	
		return 0;
	}

}
