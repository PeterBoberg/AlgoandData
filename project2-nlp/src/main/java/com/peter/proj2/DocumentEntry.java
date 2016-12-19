package com.peter.proj2;

import se.kth.id1020.util.Document;

/**
 * Created by KungPeter on 2016-12-19.
 */
public class DocumentEntry {

    private Document document;
    private double relevance = 0;
    private int count = 1;

    public DocumentEntry(Document document) {
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }

    public double getRelevance() {
        return relevance;
    }

    public int getCount() {
        return count;
    }

    public void increaseCount() {
        count++;
    }

    public void setRelevance(double relevance) {
        this.relevance = relevance;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {

        return document.toString() + " relevance: " + relevance + " count: " + count;
    }
}
