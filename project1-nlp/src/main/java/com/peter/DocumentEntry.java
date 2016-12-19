package com.peter;

import se.kth.id1020.util.Document;

/**
 * Created by KungPeter on 2016-11-30.
 */
public class DocumentEntry implements Comparable<DocumentEntry>{

    private Document document;
    private int count;

    public DocumentEntry(Document document, int count) {
        this.document = document;
        this.count = count;
    }

    public Document getDocument() {
        return document;
    }

    public int getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "DocumentEntry: " + document + " " + count;
    }

    @Override
    public int compareTo(DocumentEntry other) {
        if (this.count < other.count)
            return -1;
        if (this.count > other.count)
            return 1;
        return 0;
    }
}
