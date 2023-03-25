package app;

import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Data structure representing a prefix tree holding words and their popularity scores.
 * Supported opperations are adding a word, incrementing it's hit (popularity score),
 * and getting a list of autocompletions for a prefix. The returned list of autocompletions
 * consists of most popular words, but is limited to at most `perNodeCacheSize` which can be up to 20.
 *
 * The structure caches most popular autocompletions. The state of the cache is ensured at writes
 * (i.e. addition of words or incrementing their hits) to support read-intensive usages.
 */
public class DictionaryTrie {

  private final static char ROOT = '0';
  private final static int MAX_PER_NODE_CACHE_SIZE = 20;
  private final CharacterNode root;
  private final CharacterNode.Factory nodeFactory;
  private final Pattern pattern = Pattern.compile("[a-zA-Z]+");

  public DictionaryTrie(int perNodeCacheSize) {
    Preconditions.checkArgument(perNodeCacheSize <= MAX_PER_NODE_CACHE_SIZE,
        "Maximum supported per node cache size is " + MAX_PER_NODE_CACHE_SIZE);

    this.nodeFactory = new CharacterNode.Factory(perNodeCacheSize);
    this.root = nodeFactory.getNode(ROOT);
  }

  public void addWord(String newWord) {
    Preconditions.checkArgument(pattern.matcher(newWord).matches(),
        "The dictionary accepts non empty strings containing letters a-z A-Z only");

    newWord = newWord.toLowerCase();

    char[] chars = newWord.toCharArray();
    CharacterNode current = root;
    List<CharacterNode> visitedNodes = new ArrayList<>();

    for (char aChar : chars) {
      CharacterNode next = current.getChild(aChar);
      if (next == null) {
        CharacterNode newNode = nodeFactory.getNode(aChar);
        current.addChild(newNode);
        current = newNode;
        visitedNodes.add(newNode);
      } else {
        current = next;
        visitedNodes.add(next);
      }
    }

    WordEntry newEntry = current.markWordEntry(newWord);
    visitedNodes.remove(current);
    for (CharacterNode visitedNode: visitedNodes) {
      visitedNode.updateReachableWordsIfNeeded(newEntry);
    };
  }

  public void incrementWordHit(String word) {
    char[] chars = word.toCharArray();
    List<CharacterNode> visitedNodes = new ArrayList<>();

    CharacterNode current = root;
    for (char aChar : chars) {
      CharacterNode next = current.getChild(aChar);
      if (next == null) {
        throw new IllegalArgumentException("The word " + word + " does not exist in the dictionary," +
            " and it's hit cannot be incremented");
      } else {
        current = next;
        visitedNodes.add(next);
      }
    }
    final WordEntry incrementedWordEntry = current.incrementWordEntry();
    visitedNodes.remove(current);

    for (CharacterNode visitedNode: visitedNodes) {
      visitedNode.updateReachableWordsIfNeeded(incrementedWordEntry);
    };
  }

  public List<String> getCompletions(String prefix) {
    CharacterNode current = root;
    for (char aChar : prefix.toLowerCase().toCharArray()) {
      CharacterNode next = current.getChild(aChar);
      if (next == null) {
        // there is no word prefixed with our prefix in the dictionary
        return Collections.emptyList();
      } else {
        current = next;
      }
    }
    return current.getReachableWords();
  }

}
