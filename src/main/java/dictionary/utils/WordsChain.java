package dictionary.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordsChain {
	
	private static final String FILENAME = "dictionary.txt";
//	private static final String FILENAME = "empty.txt";
//	private static final String FILENAME = "none.txt";
	

	
	private Map<Integer, List<String>> dictionaryMap;
	private List<String> dictionaryList;
	private List<String> resultChain;

	public void loadDictFromFile() {
		dictionaryMap = new HashMap<>();
		List<String> fileListWords = new LinkedList<>();
		
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		
		File f = null;
		try {
			f = new File(classLoader.getResource(FILENAME).getFile());
		} catch (NullPointerException e) {
			System.err.println("There is no such file");
		}

		try (Stream<String> stream = Files.lines(f.toPath(), StandardCharsets.UTF_8)) {
			if (Files.size(f.toPath()) > 0) {
				fileListWords = stream.map(String::toLowerCase).filter(w -> (!w.contains("\'"))).collect(Collectors.toList());
				fileListWords.stream().forEach(
						w -> dictionaryMap.computeIfAbsent(w.length(), k -> new LinkedList<>()).add(w));
			} else {
				System.err.println("No content in dictionary file");
				return;
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	public void getChainWords(String start, String end) {
		Queue<String> bucket1 = new LinkedList<>();
		Queue<String> bucket2 = new LinkedList<>();
		Queue<String> dictionary = new LinkedList<>();
		resultChain = new LinkedList<>();
		boolean flag = true;
		int charDiff = -1;
		if (validationAndSetup(start, end)) {
			dictionary.addAll(getDictionaryList());
			resultChain.add(start);
			while (flag) {
				String dictWordToCheck = "";
				
				while (!dictionary.isEmpty()) {				
					
					
					dictWordToCheck = dictionary.poll();
				
					if(dictWordToCheck.equals(start)) {
						if (!dictionary.isEmpty())
							dictWordToCheck = dictionary.poll();
					}				
					
					if (!dictionary.isEmpty())
						charDiff = DictUtils.oneCharDiff(start, dictWordToCheck);
					
					if (charDiff != -1) {
						int idxDiff = DictUtils.checkCharInWordEnd(charDiff, dictWordToCheck, end);
						if (idxDiff == 1) {
							resultChain.add(dictWordToCheck);
							start = dictWordToCheck;
							reloadDict(dictionary, bucket1, bucket2);
							break;
						} else {
							bucket1.offer(dictWordToCheck);
						}
					} else {
						if (!dictWordToCheck.equals(start))
							bucket2.offer(dictWordToCheck);
					}
					
					
					if (dictionary.isEmpty()) {
						if (!bucket1.isEmpty()) {
							dictWordToCheck = bucket1.poll();
							resultChain.add(dictWordToCheck);
							start = dictWordToCheck;
							reloadDict(dictionary, bucket1, bucket2);
						} else {
							flag = false;
							resultChain.clear();
							reloadDict(dictionary, bucket1, bucket2);
							break;
						}
					}					
				}
				
				if (dictWordToCheck.equals(end)) {
					break;
				}
			}
			
		}
	}

	private boolean validationAndSetup(String word1, String word2) {
		boolean flag = false;
		dictionaryList = new LinkedList<>();
		if (DictUtils.checkWordsLength(word1, word2)) {
			if (!getDictionaryMap().isEmpty()) {
				dictionaryList.addAll(dictionaryMap.get(word1.length()));
				if (DictUtils.checkDictForWord(word1, dictionaryList) && DictUtils.checkDictForWord(word2, dictionaryList))
					flag = true;
			}
		}
		return flag;
	}

	@SuppressWarnings("unchecked")
	private void reloadDict(Queue<String> dict, Queue<String>... dicts) {
		for (Queue<String> s : dicts) {
			dict.addAll(s);
			s.clear();
		}
	}
	
	public List<String> getDictionaryList() {
		return this.dictionaryList;
	}
	
	public Map<Integer, List<String>> getDictionaryMap() {
		return this.dictionaryMap;
	}
	
	public List<String> getResultChain() {
		return this.resultChain;
	}

}
