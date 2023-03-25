package app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

public class DictionaryTrieTest {

  @Test
  public void shouldReturnAddedWords() {
    DictionaryTrie trie = new DictionaryTrie(3);
    trie.addWord("test");
    trie.addWord("tennis");
    trie.addWord("tetris");

    assertThat(trie.getCompletions("t")).containsExactlyInAnyOrder("test", "tennis", "tetris");
    assertThat(trie.getCompletions("te")).containsExactlyInAnyOrder("test", "tennis", "tetris");
    assertThat(trie.getCompletions("tes")).containsExactlyInAnyOrder("test");
    assertThat(trie.getCompletions("test")).isEmpty();
    assertThat(trie.getCompletions("absent")).isEmpty();
  }

  @Test
  public void shouldReturnMostPopularCompletions() {
    DictionaryTrie trie = new DictionaryTrie(2);
    trie.addWord("test");
    trie.addWord("tennis");
    trie.addWord("tetris");

    assertThat(trie.getCompletions("te")).contains("tennis");

    trie.incrementWordHit("test");
    trie.incrementWordHit("tetris");

    assertThat(trie.getCompletions("te")).containsExactlyInAnyOrder("test", "tetris");
  }

  @Test
  public void shouldReturnCompletionsForWordsIncludingThemselves() {
    DictionaryTrie trie = new DictionaryTrie(20);
    trie.addWord("try");
    trie.addWord("trya");
    trie.addWord("tryab");
    trie.addWord("tryabc");

    assertThat(trie.getCompletions("try")).containsExactlyInAnyOrder("trya", "tryab", "tryabc");
    assertThat(trie.getCompletions("trya")).containsExactlyInAnyOrder("tryab", "tryabc");
    assertThat(trie.getCompletions("tryab")).containsExactlyInAnyOrder("tryabc");
  }

  @Test
  public void shouldBeCaseInsensitive() {
    DictionaryTrie trie = new DictionaryTrie(1);
    trie.addWord("TEst");
    assertThat(trie.getCompletions("te")).contains("test");
  }

  @Test
  public void shouldThrowOnAttemptToAddInvalidWord() {
    DictionaryTrie trie = new DictionaryTrie(1);
    assertThatThrownBy(() -> trie.addWord("I am not a single word"))
        .isInstanceOf(IllegalArgumentException.class);
    assertThatThrownBy(() -> trie.addWord(""))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void shouldReturnEmptyListForUnknownPrefix() {
    DictionaryTrie trie = new DictionaryTrie(1);
    assertThat(trie.getCompletions("some")).isEmpty();
  }

  @Test
  public void shouldThrowOnAttemptToIncrementUnknownWord() {
    DictionaryTrie trie = new DictionaryTrie(1);
    assertThatThrownBy(() -> trie.incrementWordHit("newword"))
        .isInstanceOf(IllegalArgumentException.class);
  }
}
