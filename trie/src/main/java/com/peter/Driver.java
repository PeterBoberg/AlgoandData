package com.peter;

import edu.princeton.cs.introcs.In;

import java.net.URL;

/**
 * Created by KungPeter on 2016-11-25.
 */
public class Driver {

    public static void main(String[] args) {
        URL url = ClassLoader.getSystemResource("kap1.txt");

        Trie trie = new Trie();

        if (url != null) {
            System.out.println("Reading from: " + url);
        } else {
            System.out.println("Couldn't find file: kap1.txt");
        }

        In input = new In(url);

        while (!input.isEmpty()) {
            String line = input.readLine().trim();
            String[] words = line.split("(\\. )|:|,|;|!|\\?|( - )|--|(\' )| ");
            String lastOfLine = words[words.length - 1];

            if (lastOfLine.endsWith(".")) {
                words[words.length - 1] = lastOfLine.substring(0, lastOfLine.length() - 1);
            }

            for (String word : words) {
                String word2 = word.replaceAll("\"|\\(|\\)", "");

                if (word2.isEmpty()) {
                    continue;
                }

                // Add the word to the trie
                trie.put(word2);
            }
        }

        // Perform analysis
        WordStatistics statistics = new WordStatistics(trie);
        System.out.println("Ten most common words: " + statistics.getTenMostCommonWords());
        System.out.println("Ten least common words: " + statistics.getTenLeastCommonWords());
        System.out.println("The prefix of length 2 that is most common is " + statistics.maxPrefixOfLengthTwo());
        System.out.println("The letter that most words start with is " + statistics.maxLetter());
    }
}
