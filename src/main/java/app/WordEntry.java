package app;

public class WordEntry implements Comparable<WordEntry> {
  private final String word;
  private int popularityScore;

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
    this.popularityScore++;
    return this;
  }

  @Override
  public int compareTo(WordEntry o) {
    return Integer.compare(this.popularityScore, o.popularityScore);
  }

  public String getWord() {
    return this.word;
  }
}
