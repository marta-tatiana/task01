package app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A node in a prefix tree representing a letter.
 * If a path from the root of the tree to the node forms a word, it is marked with a `reachedWord` entry,
 * which holds the word and its popularity score.
 *
 * Additionally, each node holds a cache of at most `wordsCacheSize` words that are reachable from it.
 * The cache is being populated/invalidated on writes (i.e. when a word is added to the dictionary
 * or when its popularity score is incremented).
 */
class CharacterNode {
  private final char letter;
  private final WordsCache mostPopularReachableWords;
  private final Map<Character, CharacterNode> children;
  private WordEntry reachedWord;

  private CharacterNode(char letter, int wordsCacheSize) {
    this.letter = letter;
    this.reachedWord = null;
    this.mostPopularReachableWords = new WordsCache(wordsCacheSize);

    // assuming 26 letter in English alphabet so at most 26 children per node
    this.children = HashMap.newHashMap(26);
  }

  public CharacterNode getChild(char aChar) {
    return children.get(aChar);
  }

  public void addChild(CharacterNode newNode) {
    children.put(newNode.letter, newNode);
  }

  public WordEntry markWordEntry(String newWord) {
    this.reachedWord = new WordEntry(newWord);
    return this.reachedWord;
  }

  public WordEntry incrementWordEntry() {
    if (this.reachedWord == null) {
      return null;
    }
    return this.reachedWord.incrementPopularity();
  }

  public void updateReachableWordsIfNeeded(WordEntry incrementedWordEntry) {
    mostPopularReachableWords.updateWith(incrementedWordEntry);
  }

  public List<String> getReachableWords() {
    return mostPopularReachableWords.asList();
  }

  public static class Factory {
    private final int perNodeCacheSize;

    public Factory(int perNodeCacheSize) {
      this.perNodeCacheSize = perNodeCacheSize;
    }

    public CharacterNode getNode(char aChar) {
      return new CharacterNode(aChar, perNodeCacheSize);
    }
  }
}
