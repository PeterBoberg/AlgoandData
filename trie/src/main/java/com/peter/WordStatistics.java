package com.peter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by KungPeter on 2016-11-27.
 */
public class WordStatistics {

    private Trie trie;
    private List<Entry> tenMostCommonWords = new ArrayList<Entry>();
    private List<Entry> tenLeasCommonWords = new ArrayList<Entry>();
    private final int MAX_NUMER = 10;
    private final String[] ALPHABET = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l",
            "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

    public WordStatistics(Trie trie) {
        this.trie = trie;

        findTenMostCommonWords();
        findTenLeastCommonWords();
    }

    public List<Entry> getTenMostCommonWords() {
        return tenMostCommonWords;
    }

    public List<Entry> getTenLeastCommonWords() {
        return tenLeasCommonWords;
    }

    public Entry maxPrefixOfLengthTwo() {

        Entry maxEntry = new Entry("", 0);

        Trie.TrieIterator iterator = trie.iterator();
        while (iterator.hasNext()) {
            String word = iterator.next().getKey();
            if (word.length() > 1) {
                int count = trie.count(word.substring(0, 2));
                if (count > maxEntry.getValue())
                    maxEntry = new Entry(word.substring(0, 2), count);
            }
        }
        return maxEntry;
    }

    public Entry maxLetter() {

        Entry maxEntry = new Entry("", 0);
        for (int i = 0; i < ALPHABET.length; i++) {
            int distinct = trie.distinct(ALPHABET[i]);
            if (distinct > maxEntry.getValue())
                maxEntry = new Entry(ALPHABET[i], distinct);
        }
        return maxEntry;
    }

    // PRIVATE DOMAIN
    ///////////////////////////////////////////////////////////////////////

    private void findTenMostCommonWords() {
        Trie.TrieIterator iterator = trie.iterator();

        while (iterator.hasNext()) {
            Entry entry = iterator.next();
            if (tenMostCommonWords.size() < MAX_NUMER) {
                tenMostCommonWords.add(entry);

            } else {
                if (tenMostCommonWords.get(0).compareTo(entry) < 0)
                    tenMostCommonWords.set(0, entry);
            }
            Collections.sort(tenMostCommonWords);
        }
    }

    private void findTenLeastCommonWords() {

        Trie.TrieIterator iterator = trie.iterator();
        while (iterator.hasNext()) {
            Entry entry = iterator.next();
            if (tenLeasCommonWords.size() < MAX_NUMER)
                tenLeasCommonWords.add(entry);

            else {
                if (tenLeasCommonWords.get(tenLeasCommonWords.size() - 1).compareTo(entry) > 0)
                    tenLeasCommonWords.set(tenLeasCommonWords.size() - 1, entry);
            }
            Collections.sort(tenLeasCommonWords);
        }
    }
}
