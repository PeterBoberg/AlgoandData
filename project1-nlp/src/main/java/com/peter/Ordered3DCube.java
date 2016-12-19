package com.peter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KungPeter on 2016-11-29.
 */
public class Ordered3DCube {

    private final int MAX_SIZE = 100;
    private final OrderedList<WordAttribute>[][][] cube = new OrderedList[MAX_SIZE][MAX_SIZE][MAX_SIZE];
    private final OrderedList<WordAttribute>[][] plane = new OrderedList[MAX_SIZE][MAX_SIZE];
    private final OrderedList<WordAttribute>[] array = new OrderedList[MAX_SIZE];

    public void add(WordAttribute entry) {
        String word = entry.getWord().word.toUpperCase();

        Integer firstLetter = null;
        Integer secondLetter = null;
        Integer thirdLetter = null;

        if (word.length() > 2) {
            firstLetter = (int) word.charAt(0) - 32;
            secondLetter = (int) word.charAt(1) - 32;
            thirdLetter = (int) word.charAt(2) - 32;

            if (!isListInitialized(firstLetter, secondLetter, thirdLetter))
                cube[firstLetter][secondLetter][thirdLetter] = new OrderedList<WordAttribute>();

            cube[firstLetter][secondLetter][thirdLetter].add(entry);

        } else if (word.length() > 1) {
            firstLetter = (int) word.charAt(0) - 32;
            secondLetter = (int) word.charAt(1) - 32;
            if (!isListInitialized(firstLetter, secondLetter, null))
                plane[firstLetter][secondLetter] = new OrderedList<WordAttribute>();

            plane[firstLetter][secondLetter].add(entry);

        } else {

            firstLetter = (int) word.charAt(0) - 32;
            if (!isListInitialized(firstLetter, null, null))
                array[firstLetter] = new OrderedList<WordAttribute>();

            array[firstLetter].add(entry);
        }
    }

    public List<WordAttribute> search(String word) {

//        word = word.toUpperCase();
        OrderedList<WordAttribute> orderedList = this.getFullList(word);
        List<WordAttribute> resultList = new ArrayList<WordAttribute>();

        if (orderedList != null) {

            int pointerIdx = Util.binSearch(word, orderedList);

            if (pointerIdx > -1) {

                while (pointerNotBeforeBeginningOfSet(word, orderedList, pointerIdx))
                    pointerIdx--;

                pointerIdx++;

                while (hasMoreElements(word, orderedList, pointerIdx)) {
                    resultList.add(orderedList.get(pointerIdx));
                    pointerIdx++;
                }
            }
        }

        return resultList;
    }

    // PRIVATE DOMAIN
    ////////////////////////////////////////////////////////////////////////////////////


    private OrderedList<WordAttribute> getFullList(String word) {

        word = word.toUpperCase();
        Integer firstLetter = null;
        Integer secondLetter = null;
        Integer thirdLetter = null;

        if (word.length() > 2) {

            firstLetter = (int) word.charAt(0) - 32;
            secondLetter = (int) word.charAt(1) - 32;
            thirdLetter = (int) word.charAt(2) - 32;
            return cube[firstLetter][secondLetter][thirdLetter];
        } else if (word.length() > 1) {
            firstLetter = (int) word.charAt(0) - 32;
            secondLetter = (int) word.charAt(1) - 32;
            return plane[firstLetter][secondLetter];
        } else {
            firstLetter = (int) word.charAt(0) - 32;
            return array[firstLetter];
        }
    }

    private boolean hasMoreElements(String word, OrderedList<WordAttribute> orderedList, int pointerIdx) {
        return pointerIdx < orderedList.size() && orderedList.get(pointerIdx).getWord().word.equalsIgnoreCase(word);
    }

    private boolean pointerNotBeforeBeginningOfSet(String word, OrderedList<WordAttribute> orderedList, int pointerIdx) {
        return pointerIdx >= 0 && orderedList.get(pointerIdx).getWord().word.equalsIgnoreCase(word);
    }

    private boolean isListInitialized(Integer first, Integer second, Integer third) {

        if (second == null && third == null)
            return array[first] != null;
        if (third == null)
            return plane[first][second] != null;

        return cube[first][second][third] != null;
    }
}
