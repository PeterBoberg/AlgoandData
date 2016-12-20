package com.peter.proj2;

import se.kth.id1020.TinySearchEngineBase;
import se.kth.id1020.util.Attributes;
import se.kth.id1020.util.Document;
import se.kth.id1020.util.Sentence;

import java.util.*;

/**
 * Created by KungPeter on 2016-12-14.
 */
public class EnhancedTinySearchEngine implements TinySearchEngineBase {

    private final String INTERSECTION = "+";
    private final String UNION = "|";
    private final String DIFFERENCE = "-";

    private OrderedHashTable hashTable = new OrderedHashTable();
    private Cache cache = new Cache();
    private String infixString;

    @Override
    public void preInserts() {
        System.out.println("Hej din skit!");
    }

    @Override
    public void insert(Sentence sentence, Attributes attributes) {

        hashTable.put(sentence, attributes);
    }

    @Override
    public void postInserts() {
        System.out.println("Hej då din skit!");
    }

    @Override
    public List<Document> search(String s) {

        String[] words = s.split("\\s+");
        int idxOfOrderedBy = findIdxOfOrderedby(words);

        if (idxOfOrderedBy == -1) {

            System.out.println("STANDARD SEARCH OK!");
            List<DocumentEntry> queryResultList = simplePrefixQuery(words);
            return convertToDocumentList(queryResultList);

        } else {
            if (isValidKeywordQuery(idxOfOrderedBy, words)) {

                System.out.println("VALID KEYWORD SEARCH!");
                List<DocumentEntry> queryResultList = keywordPrefixQuery(words);
                return convertToDocumentList(queryResultList);
            }
        }

        System.out.println("NOT A VALID SEARCH!");
        return null;
    }

    @Override
    public String infix(String s) {

        return infixString;

    }

    // PRIVATE DOMAIN
    ////////////////////////////////////////////////////////////////////


    private List<DocumentEntry> keywordPrefixQuery(String[] words) {

        String[] queryArray = new String[words.length - 3];
        for (int i = 0; i < queryArray.length; i++)
            queryArray[i] = words[i];

        List<DocumentEntry> queryResultList = simplePrefixQuery(queryArray);

        String command1 = words[words.length - 2].toUpperCase();
        String command2 = words[words.length - 1].toUpperCase();


        switch (command1) {

            case "POPULARITY":

                switch (command2) {
                    case "ASC":
                        Collections.sort(queryResultList, new PopularityComparator());
                        return queryResultList;
                    case "DESC":
                        Collections.sort(queryResultList, new PopularityComparator().reversed());
                        return queryResultList;
                }

            case "RELEVANCE":

                switch (command2) {
                    case "ASC":
                        Collections.sort(queryResultList, new RelevanceComparator());
                        break;
                    case "DESC":
                        Collections.sort(queryResultList, new RelevanceComparator().reversed());
                        break;
                }

                return queryResultList;
        }

        return null;
    }

    // Assumes the orderby keyword has already been removed
    private List<DocumentEntry> simplePrefixQuery(String[] words) {

        Stack<List<DocumentEntry>> operandStack = new Stack<>();
        Stack<String> infixStack = new Stack<>();

        for (int i = words.length - 1; i >= 0; i--) {
            if (isOperand(words[i])) {
                operandStack.push(fetchDocuments(words[i]));
                infixStack.push(words[i]);

            } else {

                String operandString1 = infixStack.pop();
                String operandString2 = infixStack.pop();
                String resultString = "( " + operandString1 + " " + words[i] + " " + operandString2 + " )";
                infixStack.push(resultString);

                Operation operation = new Operation(operandString1, operandString2, words[i]);

                List<DocumentEntry> list1 = operandStack.pop();
                List<DocumentEntry> list2 = operandStack.pop();
                List<DocumentEntry> result = applyOperation(list1, list2, words[i], operation);
                operandStack.push(result);
                cache.put(operation, result);
            }
        }

        infixString = infixStack.pop();
        return operandStack.pop();
    }

    private List<DocumentEntry> applyOperation(List<DocumentEntry> list1, List<DocumentEntry> list2, String operator, Operation operation) {

        if (cache.hasResult(operation)){
            System.out.println("Cash Hit " + operation);
            return cache.get(operation);
        }

        List<DocumentEntry> resultList = new ArrayList<>();

        switch (operator) {
            case INTERSECTION:
                for (DocumentEntry entry1 : list1) {
                    for (DocumentEntry entry2 : list2) {
                        if (entry1.getDocument().equals(entry2.getDocument())) {
                            DocumentEntry intersectEntry = createSummedDocEntry(entry1, entry2);
                            resultList.add(intersectEntry);
                        }
                    }
                }
                break;

            case UNION:
                resultList.addAll(list1);
                for (int i = 0; i < list2.size(); i++) {
                    boolean found = false;
                    for (int j = 0; j < resultList.size(); j++) {
                        if (list2.get(i).getDocument().equals(resultList.get(j).getDocument())) {
                            found = true;
                            DocumentEntry unionEntry = createSummedDocEntry(list2.get(i), resultList.get(j));
                            resultList.remove(j);
                            resultList.add(unionEntry);
                            break;
                        }
                    }

                    if (!found)
                        resultList.add(list2.get(i));
                }
                break;

            case DIFFERENCE:
                resultList.addAll(list1);
                for (int i = 0; i < list2.size(); i++) {
                    for (int j = 0; j < resultList.size(); j++) {
                        if (list2.get(i).getDocument().equals(resultList.get(j).getDocument())) {
                            resultList.remove(j);
                            break;
                        }
                    }
                }
                break;
        }
        return resultList;

    }

    private List<DocumentEntry> fetchDocuments(String key) {

        List<DocumentEntry> resultList = new ArrayList<>();
        List<WordAttribute> queryResultList = hashTable.get(key);

        for (WordAttribute wordAttribute : queryResultList) {
            boolean found = false;
            for (DocumentEntry entry : resultList) {
                if (entry.getDocument().equals(wordAttribute.getAttributes().document)) {
                    entry.increaseCount();
                    found = true;
                    break;
                }
            }
            if (!found) {
                resultList.add(new DocumentEntry(wordAttribute.getAttributes().document));
            }
        }
        resultList = calculateRelevance(resultList);

        return resultList;
    }


    private List<DocumentEntry> calculateRelevance(List<DocumentEntry> resultList) {

        for (DocumentEntry entry : resultList) {
            int noOfAppeanceInDoc = entry.getCount();
            int noOfWordInDoc = hashTable.getDocumentSize(entry.getDocument());
            int noOfDocuments = hashTable.getNumOdDocuments();
            int noOfDocumentsContainingWord = resultList.size();


            double relevance = ((double) noOfAppeanceInDoc / noOfWordInDoc) * Math.log10((double) noOfDocuments / noOfDocumentsContainingWord);
            entry.setRelevance(relevance);
        }

        return resultList;
    }

    private DocumentEntry createSummedDocEntry(DocumentEntry entry1, DocumentEntry entry2) {
        DocumentEntry summedEntry = new DocumentEntry(entry1.getDocument());
        double totalRelevance = entry1.getRelevance() + entry2.getRelevance();
        int totalOccurances = entry1.getCount() + entry2.getCount();
        summedEntry.setRelevance(totalRelevance);
        summedEntry.setCount(totalOccurances);
        return summedEntry;
    }

    private List<Document> convertToDocumentList(List<DocumentEntry> queryResultList) {
        List<Document> resultList = new ArrayList<>();
        for (DocumentEntry entry : queryResultList) {
            resultList.add(entry.getDocument());
        }

        return resultList;
    }

    private int findIdxOfOrderedby(String[] words) {
        int result = -1;
        for (int i = 0; i < words.length; i++) {
            if (words[i].equalsIgnoreCase("orderby"))
                return i;
        }
        return result;
    }

    private boolean isValidKeywordQuery(int idxOfOrderedBy, String[] words) {

        return (words.length > 3) && (words.length == idxOfOrderedBy + 3)
                && (isProperty(words[words.length - 2]) && (isDirection(words[words.length - 1])));
    }

    private boolean isProperty(String s) {
        s = s.toUpperCase();
        switch (s) {
            case "RELEVANCE":
                return true;
            case "POPULARITY":
                return true;
        }
        return false;
    }

    private boolean isDirection(String s) {

        s = s.toUpperCase();
        switch (s) {
            case "ASC":
                return true;
            case "DESC":
                return true;
        }
        return false;
    }

    private boolean isOperand(String token) {
        return !(token.equalsIgnoreCase(INTERSECTION) || token.equalsIgnoreCase(UNION) || token.equalsIgnoreCase(DIFFERENCE));
    }


}


class PopularityComparator implements Comparator<DocumentEntry> {

    @Override
    public int compare(DocumentEntry o1, DocumentEntry o2) {
        if (o1.getDocument().popularity > o2.getDocument().popularity)
            return 1;
        if (o1.getDocument().popularity < o2.getDocument().popularity)
            return -1;
        return 0;
    }
}


class RelevanceComparator implements Comparator<DocumentEntry> {

    @Override
    public int compare(DocumentEntry o1, DocumentEntry o2) {
        if (o1.getRelevance() > o2.getRelevance())
            return 1;
        if (o1.getRelevance() < o2.getRelevance())
            return -1;

        return 0;
    }
}