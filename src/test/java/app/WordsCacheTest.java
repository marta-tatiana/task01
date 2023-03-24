package app;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

public class WordsCacheTest {

  @Test
  public void shouldAddWords() {
    WordsCache wordsCache = new WordsCache(3);
    wordsCache.updateWith(new WordEntry("a"));
    wordsCache.updateWith(new WordEntry("b"));
    wordsCache.updateWith(new WordEntry("c"));
    List<String> words = wordsCache.asList();
    assertThat(words).containsExactlyInAnyOrder("a", "b", "c");
  }

  @Test
  public void shouldReplaceLessPopularWords() {
    WordsCache wordsCache = new WordsCache(2);
    wordsCache.updateWith(new WordEntry("a", 10));
    wordsCache.updateWith(new WordEntry("b", 12));

    wordsCache.updateWith(new WordEntry("c", 11));

    assertThat(wordsCache.asList()).containsExactlyInAnyOrder("b", "c");
  }

  @Test
  public void shouldNotReplaceMorePopularWords() {
    WordsCache wordsCache = new WordsCache(2);
    wordsCache.updateWith(new WordEntry("a", 10));
    wordsCache.updateWith(new WordEntry("b", 12));

    wordsCache.updateWith(new WordEntry("c", 8));

    assertThat(wordsCache.asList()).containsExactlyInAnyOrder("a", "b");
  }
}
