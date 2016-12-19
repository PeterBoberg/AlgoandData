package com.peter.proj2;

import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Document;
import se.kth.id1020.util.Sentence;
import se.kth.id1020.util.Word;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by KungPeter on 2016-12-16.
 */
public class OrderedHashTable {

    private HashMap<String, List<WordAttribute>> hashMap = new HashMap<>();
    private HashMap<Document, Integer> documentSizeMap = new HashMap<>();


    public void put(Sentence sentence, Attributes attributes) {

        Integer count = documentSizeMap.get(attributes.document);

        if (count != null) {
            count += sentence.getWords().size();
            documentSizeMap.put(attributes.document, count);
        }

        else {
            documentSizeMap.put(attributes.document, sentence.getWords().size());
        }

        for (Word word : sentence.getWords()) {
            this.put(new WordAttribute(word, attributes));
        }
    }

    private void put(WordAttribute wordAttribute) {

        String key = wordAttribute.getWord().word;

        List<WordAttribute> wordAttributes = hashMap.get(key);
        if (wordAttributes == null)
            wordAttributes = new ArrayList<>();

        wordAttributes.add(wordAttribute);
        hashMap.put(key, wordAttributes);

    }

    public List<WordAttribute> get(String key) {

        List<WordAttribute> wordAttributes = hashMap.get(key);
        if (wordAttributes != null)
            return wordAttributes;

        return new ArrayList<>();
    }

    public int getDocumentSize(Document document) {
        return documentSizeMap.get(document);
    }

    public int getNumOdDocuments(){
        return documentSizeMap.size();

    }




}
