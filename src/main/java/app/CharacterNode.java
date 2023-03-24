package app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class CharacterNode {
  private final static int WORDS_CACHE_SIZE = 20;

  private final char letter;
  private final WordsCache mostPopularReachableWords;
  private WordEntry reachedWord;
  private Map<Character, CharacterNode> children;

  public CharacterNode(char letter) {
    this.letter = letter;

    this.reachedWord = null;
    this.mostPopularReachableWords = new WordsCache(WORDS_CACHE_SIZE);
    this.children = new HashMap<>();
  }

  public CharacterNode getChild(char aChar) {
    return children.get(aChar);
  }

  public void addChild(CharacterNode newNode) {
    children.put(newNode.letter, newNode);
  }

  public void markWordEntry(String newWord) {
    WordEntry wordEntry = new WordEntry(newWord);
    //TODO: do we want to do something if we already have an entry?
    this.reachedWord = wordEntry;
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
}
