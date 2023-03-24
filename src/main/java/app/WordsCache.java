package app;

import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

class WordsCache {
  private final int maxSize;
  private final PriorityQueue<WordEntry> mostPopularReachableWords;

  public WordsCache(int maxSize) {
    this.maxSize = maxSize;
    this.mostPopularReachableWords = new PriorityQueue<>(maxSize);
  }

  public List<String> asList() {
    return mostPopularReachableWords
        .stream()
        .map(WordEntry::getWord)
        .collect(Collectors.toList());
  }

  public void updateWith(WordEntry incrementedWordEntry) {
    if (mostPopularReachableWords.size() < this.maxSize) {
      mostPopularReachableWords.add(incrementedWordEntry);
    } else {
      WordEntry currentLeastPopular = mostPopularReachableWords.peek();
      if (currentLeastPopular.compareTo(incrementedWordEntry) < 0) {
        mostPopularReachableWords.poll();
        mostPopularReachableWords.add(incrementedWordEntry);
      }
    }
  }
}
