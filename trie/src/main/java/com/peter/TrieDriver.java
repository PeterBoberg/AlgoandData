package com.peter;

/**
 * Hello world!
 */
public class TrieDriver {
    public static void main(String[] args) {

        Trie trie = new Trie();

        trie.put("tige");
        trie.put("tiger");
        trie.put("tiggare");
        trie.put("timglas");
        trie.put("apa");

        Trie.TrieIterator iterator = trie.iterator("a");

        while (iterator.hasNext())
            System.out.println(iterator.next());

        System.out.println();
        System.out.println("///////////////");
        System.out.println();
        Trie.TrieIterator iterator1 = trie.iterator("tim");
        while (iterator1.hasNext())
            System.out.println(iterator1.next());
    }
}
