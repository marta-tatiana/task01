package app;

import java.util.Objects;

/**
 * Represents a word with its popularity score.
 *
 * Note that the Natural Ordering of this class depends on the popularity score, while the equality
 * depends on the word contained.
 */
class WordEntry implements Comparable<WordEntry> {
  private final String word;
  private final int popularityScore;

  public WordEntry(String word) {
    this.word = word;
    this.popularityScore = 0;
  }

  // VisibleForTests
  WordEntry(String word, int popularityScore) {
    this.word = word;
    this.popularityScore = popularityScore;
  }

  public WordEntry incrementPopularity() {
    int newPopularity = this.popularityScore + 1;
    return new WordEntry(this.word, newPopularity);
  }

  public String getWord() {
    return this.word;
  }

  @Override
  public int compareTo(WordEntry o) {
    return Integer.compare(this.popularityScore, o.popularityScore);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    WordEntry wordEntry = (WordEntry) o;
    return Objects.equals(word, wordEntry.word);
  }

  @Override
  public int hashCode() {
    return Objects.hash(word);
  }
}
