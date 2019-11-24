package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.ArrayList;

/**
 *
 * @author andrii
 */
public class PrefixMatches {

    private Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        for (String string: strings) {
            for (String word: string.split(" ")) {
                int wordLength = word.length();
                if (wordLength > 2) {
                    trie.add(new Tuple(word, wordLength));
                }
            }
        }
        return trie.size();
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        return trie.wordsWithPrefix(pref);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {

       Iterable<String> iterable = wordsWithPrefix(pref);
       ArrayList<String> words = new ArrayList<>();
       int lengthNumber = 0;
       int currentLength = 0;
       for (String word: iterable) {
           int wordLength = word.length();
           if (wordLength != lengthNumber) {
               if (currentLength == k) {
                   break;
               }
               currentLength++;
               lengthNumber = wordLength;
           }

           words.add(word);
       }

       return words;
    }

    public int size() {
        return trie.size();
    }
}
