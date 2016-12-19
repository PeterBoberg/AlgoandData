package com.peter;

import se.kth.id1020.util.Document;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by KungPeter on 2016-11-29.
 */
public class Util {

    private Util() {
    }

    public static int binSearch(String searched, OrderedList<WordAttribute> list) {
        searched = searched.toUpperCase();
        int lo = 0;
        int hi = list.size() - 1;

        while (lo <= hi) {

            int mid = lo + (hi - lo) / 2;
            if (searched.compareTo(list.get(mid).getWord().word.toUpperCase()) < 0)
                hi = mid - 1;
            else if (searched.compareTo(list.get(mid).getWord().word.toUpperCase()) > 0)
                lo = mid + 1;
            else return mid;
        }

        return -1;

    }

    public static List<Document> bubbleSort(List<WordAttribute> wordAttributeList, Comparator<WordAttribute> comparator) {


        int sortedWall = wordAttributeList.size();
        boolean swapped;

        do {
            swapped = false;
            for (int i = 0; i < wordAttributeList.size() - 1; i++) {
                for (int j = 1; j < sortedWall; j++) {
                    if (isBigger(comparator, wordAttributeList, j - 1, j)) {
                        swap(wordAttributeList, j - 1, j);
                        swapped = true;
                    }
                }
                sortedWall--;
            }
        } while (swapped);


        List<Document> resultList = new ArrayList<>();
        for (WordAttribute attribute : wordAttributeList)
            resultList.add(attribute.getAttributes().document);

        return resultList;
    }


    public static List<Document> bubbleSortCount(List<DocumentEntry> entryList, Comparator<DocumentEntry> comparator) {

        int sortedWall = entryList.size();
        boolean swapped;

        do {
            swapped = false;
            for (int i = 0; i < entryList.size() - 1; i++) {
                for (int j = 1; j < sortedWall; j++) {
                    if (comparator.compare(entryList.get(j - 1), entryList.get(j)) > 0) {

                        DocumentEntry temp = entryList.get(j);
                        entryList.set(j, entryList.get(j - 1));
                        entryList.set(j - 1, temp);
                        swapped = true;
                    }
                }
                sortedWall--;
            }
        } while (swapped);

        for (DocumentEntry bajs : entryList)
            System.out.println(bajs);

        List<Document> resultList = new ArrayList<>();
        for (DocumentEntry entry : entryList)
            resultList.add(entry.getDocument());

        return resultList;
    }

    private static boolean isBigger(Comparator<WordAttribute> comparator, List<WordAttribute> originalList, int i, int j) {
        return comparator.compare(originalList.get(i), originalList.get(j)) > 0;
    }


    private static void swap(List<WordAttribute> originalList, int i, int j) {

        WordAttribute temp = originalList.get(j);
        originalList.set(j, originalList.get(i));
        originalList.set(i, temp);
    }
}
