package com.peter;

import java.util.*;

/**
 * Created by KungPeter on 2016-11-24.
 */
public class Trie implements Iterable<Entry>, Cloneable {

    private TrieNode root = new TrieNode();


    public Trie() {
        root.isVisited = true;
    }

    public void put(String key) {
        key = key.toLowerCase();

        TrieNode current = root;
        for (char letter : key.toCharArray()) {

            if (current.children.containsKey(letter)) {
                current = current.children.get(letter);
            } else {

                TrieNode newNode = new TrieNode();
                newNode.parent = current;
                newNode.letter = letter;
                current.children.put(letter, newNode);
                current = newNode;
            }
        }

        current.numOfOcurrances++;
    }

    public int get(String key) {
        key = key.toLowerCase();

        TrieNode current = moveToPosition(key);

        if (current == null)
            return 0;
        return current.numOfOcurrances;
    }

    public int count(String prefix) {
        prefix = prefix.toLowerCase();

        int numOfoccurances = 0;

        TrieNode current = moveToPosition(prefix);
        if (current == null)
            return 0;

        numOfoccurances += count(current);

        return numOfoccurances;
    }

    private int count(TrieNode node) {
        int numOfOccurances = node.numOfOcurrances;

        if (node.children.isEmpty())
            return numOfOccurances;

        for (char c : node.children.keySet())
            numOfOccurances += count(node.children.get(c));

        return numOfOccurances;
    }

    public int distinct(String prefix) {

        prefix = prefix.toLowerCase();
        TrieNode current = moveToPosition(prefix);

        if (current == null)
            return 0;

        return distinct(current);
    }

    private int distinct(TrieNode node) {

        int numOfKeys = 0;

        for (char c : node.children.keySet())
            numOfKeys += distinct(node.children.get(c));

        if (node.numOfOcurrances > 0)
            numOfKeys++;

        return numOfKeys;
    }


    private TrieNode moveToPosition(String word) {
        word = word.toLowerCase();

        TrieNode current = root;
        for (char letter : word.toCharArray()) {

            if (current.children.containsKey(letter))
                current = current.children.get(letter);
            else
                return null;
        }
        return current;
    }

    public Trie copy() {

        Trie trie = new Trie();
        trie.root = this.root.copy();
        return trie;
    }


    public TrieIterator iterator() {

        return new TrieIterator();
    }

    public TrieIterator iterator(String prefix) {
        return new TrieIterator(prefix);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    private class TrieNode {

        Integer numOfOcurrances = 0;
        Character letter = '0';
        boolean isVisited = false;
        TrieNode parent;
        Map<Character, TrieNode> children = new TreeMap<Character, TrieNode>();


        TrieNode copy() {

            TrieNode node = new TrieNode();
            node.numOfOcurrances = this.numOfOcurrances;
            node.children = new TreeMap<Character, TrieNode>(this.children);
            return node;
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////

    public class TrieIterator implements Iterator<Entry> {


        private TrieNode iteratorRoot;
        private TrieNode current;
        private Stack<Character> stack;
        private String currentWord = "";

        // for prefix based iterators;
        private String prefix;
        private Entry prefixEntry;
        private boolean prefixExists;


        private TrieIterator() {
            iteratorRoot = root;
            current = iteratorRoot;
            stack = new Stack<Character>();
            currentWord = "";
            prefix = "";
            prefixEntry = null;
            prefixExists = true;
            resetVisited(iteratorRoot);
            findNextWord();
        }

        private TrieIterator(String prefix) {
            prefix = prefix.toLowerCase();

            stack = new Stack<Character>();
            this.prefix = prefix;
            prefixExists = true;
            iteratorRoot = moveToPosition(prefix);
            current = iteratorRoot;
            resetVisited(iteratorRoot);

            if (current != null) {
                if (current.numOfOcurrances > 0) {
                    prefixEntry = new Entry(prefix, current.numOfOcurrances);
                }
                findNextWord();
            } else prefixExists = false;

        }

        private TrieNode resetVisited(TrieNode node) {

            if (node.children.isEmpty()) {
                node.isVisited = false;
                return node;
            }

            for (char c : node.children.keySet()) {
                resetVisited(node.children.get(c));
                node.isVisited = false;
            }
            return node;
        }

        public boolean hasNext() {

            if (!prefixExists)
                return false;
            if (current.equals(iteratorRoot) && allChildrenVisited())
                return false;

            return true;
        }

        public Entry next() {

            if (!hasNext())
                throw new NoSuchElementException();

            if (prefixEntry != null) {
                Entry copy = prefixEntry;
                prefixEntry = null;
                return copy;
            }

            Entry entry = new Entry(currentWord, current.numOfOcurrances);
            findNextWord();
            return entry;

        }


        // PRIVATE DOMAIN
        //////////////////////////////////////////////////////////////////

        private void findNextWord() {

//            while ((current.numOfOcurrances == 0 || newRun) && hasNext())
//                findNextChar();

            do {
                findNextChar();
            } while (current.numOfOcurrances == 0 && hasNext());

            StringBuilder sb = new StringBuilder();

            for (char c : stack)
                sb.append(c);

            currentWord = prefix + sb.toString();
        }

        private void findNextChar() {


            if (foundNewChar()) {
                stack.push(current.letter);
                return;
            }

            while (hasNext()) {

                current = current.parent;
                stack.pop();
                if (foundNewChar()) {
                    stack.push(current.letter);
                    return;
                }
            }
        }

        private boolean foundNewChar() {

            for (char c : current.children.keySet()) {
                TrieNode node = current.children.get(c);
                if (!node.isVisited) {
                    current = node;
                    current.isVisited = true;
                    return true;
                }
            }
            return false;
        }

        private boolean allChildrenVisited() {

            for (char c : current.children.keySet())
                if (!current.children.get(c).isVisited)
                    return false;

            return true;

        }
    }
}
