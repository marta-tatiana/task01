package app;

import java.util.ArrayList;
import java.util.List;

public class DictionaryTrie {

  private final static char ROOT = '0';
  private final CharacterNode root;

  public DictionaryTrie() {
    this.root = new CharacterNode(ROOT);
  }

  public void addWord(String newWord) {
    char[] chars = newWord.toCharArray();
    CharacterNode current = root;

    for (char aChar : chars) {
      CharacterNode next = current.getChild(aChar);
      if (next == null) {
        CharacterNode newNode = new CharacterNode(aChar);
        current.addChild(newNode);
        current = newNode;
      } else {
        current = next;
      }
    }
    current.markWordEntry(newWord);
  }

  public void incrementWordHit(String word) {
    // TODO assuming word must be added first to be incremented, otherwise we'll do nothing
    char[] chars = word.toCharArray();
    List<CharacterNode> visitedNodes = new ArrayList<>();

    CharacterNode current = root;
    for (char aChar : chars) {
      CharacterNode next = current.getChild(aChar);
      if (next == null) {
        // TODO
        return;
      } else {
        current = next;
        visitedNodes.add(next);
      }
      final WordEntry incrementedWordEntry = current.incrementWordEntry();

      for (CharacterNode visitedNode: visitedNodes) {
        visitedNode.updateReachableWordsIfNeeded(incrementedWordEntry);
      };
    }

  }

  public List<String> getCompletions(String prefix) {
    char[] chars = prefix.toCharArray();
    List<String> results = new ArrayList<>();

    CharacterNode current = root;
    for (int i = 0; i < chars.length; i++) {
      CharacterNode next = current.getChild(chars[i]);
      if (next == null) {
        return results;
      } else {
        current = next;
      }
    }
    return current.getReachableWords();
  }

}
