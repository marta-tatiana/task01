#Overview
This project implements a simple prefix tree (`DictionaryTrie`). The tree consists of `CharacterNodes`, each holding a list of its children. The nodes hold caches of words that are reachable from them.
See the docstrings for details.

#Performance considerations
##Memory
Assuming this structure needs to hold ~175 000 of words of an average length of 5 characters, it will grow up to 875 000 of nodes.

A node holds a character and a cache of up to 20 words. 
Assuming that a String occupies 2 + 2*string.length() of bytes in a memory, we can estimate one node at 210 bytes.
875 000 * 210 ~= 184 000 000 bytes ~= 184 000 kB ~= 184 MB.

##Complexity
Adding a word and incrementing its hit has a complexity of O(n), where n is the length of the word.
Reading a list of autocompletions has a complexity of O(n), where n is the length of the prefix.

#API assumptions:
* it's not allowed to increment a word hit of a word that was not previously added
* the list of completions for a given prefix should not contain the prefix itself (even if it's not a word)
* autocompletions are not provided for an empty prefix
