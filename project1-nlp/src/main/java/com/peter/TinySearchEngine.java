package com.peter;

import se.kth.id1020.TinySearchEngineBase;
import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Document;
import se.kth.id1020.util.Word;

import java.util.*;

/**
 * Created by KungPeter on 2016-11-29.
 */
public class TinySearchEngine implements TinySearchEngineBase {


    private Ordered3DCube ordered3DCube = new Ordered3DCube();


    public void insert(Word word, Attributes attributes) {

        ordered3DCube.add(new WordAttribute(word, attributes));
    }


    public List<Document> search(String word) {


        String[] words = word.split(" ");

        int idxOfOrderBy = findIdxOfOrderBy(words);

        if (idxOfOrderBy == -1) {
            // No keyword exists
            return performStandardSearch(words);

        } else if (isValidTail(words, idxOfOrderBy)) {
            // Keyword exists and have valid tail
            return performSpecialSearch(words);

        }

        System.out.println("Invalid input. input is of the form [SEARCHED STRING] orderby [PROPERTY][DIRECTION]");
        return null;
    }


    // PRIVATE DOMAIN
    ///////////////////////////////////////////////////////////////////////////////////////////////////

    private List<Document> performStandardSearch(String[] words) {

        Set<Document> documentSet = new HashSet<Document>();

        for (String token : words) {
            List<WordAttribute> list = ordered3DCube.search(token);
            for (WordAttribute wordAttribute : list)
                documentSet.add(wordAttribute.getAttributes().document);
        }

        return new ArrayList<Document>(documentSet);
    }

    private List<Document> performSpecialSearch(String[] words) {

        List<WordAttribute> allWordsAttrList = new ArrayList<>();

        for (String word : words) {
            if (word.equalsIgnoreCase("orderby"))
                break;
            List<WordAttribute> attr = ordered3DCube.search(word);
            allWordsAttrList.addAll(attr);
        }


        String command = (words[words.length - 2] + words[words.length - 1]).toUpperCase();
        List<WordAttribute> cleanedList;
        List<Document> result;

        switch (command) {

            case "POPULARITYASC":
                cleanedList = removeDuplicatesPopularity(allWordsAttrList);
                return Util.bubbleSort(cleanedList, new PopularityComparator());
            case "POPULARITYDESC":
                cleanedList = removeDuplicatesPopularity(allWordsAttrList);
                result = Util.bubbleSort(cleanedList, new PopularityComparator());
                Collections.reverse(result);
                return result;
            case "OCCURRENCEASC":
                cleanedList = removeDuplicatesOccurrence(allWordsAttrList);
                return Util.bubbleSort(cleanedList, new OccurrenceComparator());
            case "OCCURRENCEDESC":
                cleanedList = removeDuplicatesOccurrence(allWordsAttrList);
                result = Util.bubbleSort(cleanedList, new OccurrenceComparator());
                Collections.reverse(result);
                return result;
            case "COUNTASC":
                List<DocumentEntry> entryList = countDocuments(allWordsAttrList);
                return Util.bubbleSortCount(entryList, new CountComparator());
            case "COUNTDESC":
                List<DocumentEntry> entryLis2 = countDocuments(allWordsAttrList);
                List<Document> resultList = Util.bubbleSortCount(entryLis2, new CountComparator());
                Collections.reverse(resultList);
                return resultList;

            default:
                break;
        }

        return null;
    }

    private List<DocumentEntry> countDocuments(List<WordAttribute> duplicatesList) {

        List<DocumentEntry> resultList = new ArrayList<>();

        for (WordAttribute attr : duplicatesList) {
            boolean isCounted = false;
            for (DocumentEntry docEntry : resultList) {
                if (attr.getAttributes().document.equals(docEntry.getDocument())) {
                    isCounted = true;
                    break;
                }
            }

            // If document is not already counted
            if (!isCounted) {
                int count = 0;
                for (WordAttribute secondAttr : duplicatesList) {
                    if (attr.getAttributes().document.equals(secondAttr.getAttributes().document))
                        count++;
                }
                resultList.add(new DocumentEntry(attr.getAttributes().document, count));
            }
        }
        return resultList;
    }

    private List<WordAttribute> removeDuplicatesOccurrence(List<WordAttribute> duplicatesList) {
        List<WordAttribute> resultList = new ArrayList<>();

        for (WordAttribute attr : duplicatesList) {
            boolean exists = false;
            for (WordAttribute secAttr : resultList) {
                // does the doc allready exists in result list?
                if (attr.getAttributes().document.equals(secAttr.getAttributes().document)) {
                    exists = true;
                    // Should we swap?
                    if (attr.getAttributes().occurrence < secAttr.getAttributes().occurrence) {
                        resultList.remove(secAttr);
                        resultList.add(attr);
                    }
                }
            }
            if (!exists)
                resultList.add(attr);
        }
        return resultList;
    }

    private List<WordAttribute> removeDuplicatesPopularity(List<WordAttribute> duplicatesList) {

        List<WordAttribute> resultList = new ArrayList<>();

        for (WordAttribute attr : duplicatesList) {
            boolean isFound = false;
            if (!resultList.isEmpty()) {
                for (WordAttribute secAttr : resultList)
                    if (attr.getAttributes().document.equals(secAttr.getAttributes().document)) {
                        isFound = true;
                        break;
                    }

                if (!isFound)
                    resultList.add(attr);

            } else
                resultList.add(attr);

        }

        return resultList;
    }

    private int findIdxOfOrderBy(String[] words) {
        int idxOfOrderby = -1;
        for (int i = 0; i < words.length; i++) {
            if (words[i].equalsIgnoreCase("orderby")) {
                idxOfOrderby = i;
                break;
            }

        }
        return idxOfOrderby;
    }

    private boolean isValidTail(String[] words, int idxOfOrderby) {

        if (words.length < 4)
            return false;

        if (idxOfOrderby > 0) {

            if ((idxOfOrderby + 3) != words.length)
                return false;
            if (!isProperty(words[words.length - 2]) || !isDirection(words[words.length - 1]))
                return false;

            return true;
        }
        return false;
    }

    private boolean isDirection(String word) {

        word = word.toUpperCase();
        switch (word) {

            case "ASC":
                return true;
            case "DESC":
                return true;
            default:
                return false;
        }
    }

    private boolean isProperty(String word) {

        word = word.toUpperCase();
        switch (word) {

            case "COUNT":
                return true;
            case "POPULARITY":
                return true;
            case "OCCURRENCE":
                return true;
            default:
                return false;
        }
    }
}


class OccurrenceComparator implements Comparator<WordAttribute> {


    @Override
    public int compare(WordAttribute o1, WordAttribute o2) {
        if (o1.getAttributes().occurrence < o2.getAttributes().occurrence)
            return -1;
        if (o1.getAttributes().occurrence > o2.getAttributes().occurrence)
            return 1;
        return 0;
    }
}


class PopularityComparator implements Comparator<WordAttribute> {


    @Override
    public int compare(WordAttribute o1, WordAttribute o2) {
        if (o1.getAttributes().document.popularity < o2.getAttributes().document.popularity)

            return -1;
        if (o1.getAttributes().document.popularity > o2.getAttributes().document.popularity)
            return 1;
        return 0;
    }
}

class CountComparator implements Comparator<DocumentEntry> {

    @Override
    public int compare(DocumentEntry o1, DocumentEntry o2) {

        if (o1.compareTo(o2) < 0)
            return -1;
        if (o1.compareTo(o2) > 0)
            return 1;
        return 0;

    }
}
