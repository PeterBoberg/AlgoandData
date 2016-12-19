package com.peter;

import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Word;

/**
 * Created by KungPeter on 2016-11-29.
 */
public class WordAttribute implements Comparable<WordAttribute> {

    private Word word;
    private Attributes attributes;

    public WordAttribute(Word word, Attributes attributes) {
        this.word = word;
        this.attributes = attributes;
    }

    public Word getWord() {
        return word;
    }

    public void setWord(Word word) {
        this.word = word;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public int compareTo(WordAttribute other) {

        return this.word.word.compareToIgnoreCase(other.word.word);
    }

    @Override
    public String toString() {

        return "WordAttribute: " + word.word + ", " + attributes.document + ", " + attributes.occurrence;
    }
}
