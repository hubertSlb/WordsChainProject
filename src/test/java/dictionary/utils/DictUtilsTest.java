package dictionary.utils;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;

import org.junit.Before;
import org.junit.Test;

public class DictUtilsTest {
	
	private List<String> listString;
	private String word1;
	private String word2;
	
	@Before
	public void setUp() {
		listString = Arrays.asList("aaaa", "bbbb", "word1", "cccc", "word2");
		word1 = "word1";
		word2 = "word2";
	}

	@Test
	public void testCheckWordsLength() {
		assertThat(word1.length() == word2.length(), is(true));
		assertThat(word1.length() > 2, is(true));
		assertThat(word2.length() > 2, is(true));
	}

	@Test
	public void testCheckDictForWord() {
		assertThat(listString.contains(word1), is(true));
		assertThat(!listString.isEmpty(), is(true));
	}

}
