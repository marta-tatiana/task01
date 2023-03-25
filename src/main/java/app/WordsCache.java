package app;

import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * A cache of most popular words.
 */
class WordsCache {
  private final int maxSize;
  private final PriorityQueue<WordEntry> mostPopularWords;

  public WordsCache(int maxSize) {
    this.maxSize = maxSize;
    this.mostPopularWords = new PriorityQueue<>(maxSize);
  }

  public List<String> asList() {
    return mostPopularWords
        .stream()
        .map(WordEntry::getWord)
        .collect(Collectors.toList());
  }

  public void updateWith(WordEntry incrementedWordEntry) {
    if (mostPopularWords.contains(incrementedWordEntry)) {
      mostPopularWords.remove(incrementedWordEntry);
      mostPopularWords.add(incrementedWordEntry);
      return;
    }

    if (mostPopularWords.size() < this.maxSize) {
      mostPopularWords.add(incrementedWordEntry);
      return;
    }

    WordEntry currentLeastPopular = mostPopularWords.peek();
    if (incrementedWordEntry.compareTo(currentLeastPopular) > 0) {
      mostPopularWords.poll();
      mostPopularWords.add(incrementedWordEntry);
    }
  }
}
