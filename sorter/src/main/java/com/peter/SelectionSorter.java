package com.peter;

/**
 * Created by KungPeter on 2016-11-08.
 */
public class SelectionSorter extends Sorter {


    @Override
    public void sort(Comparable[] arr) {

        for (int i = 0; i < arr.length; i++) {
            int minIdx = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (less(arr[j], arr[minIdx])) {
                    minIdx = j;
                }
            }
            exchange(arr, i, minIdx);
        }
    }
}
