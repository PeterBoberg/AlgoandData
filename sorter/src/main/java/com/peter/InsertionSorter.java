package com.peter;

/**
 * Created by KungPeter on 2016-11-08.
 */
public class InsertionSorter extends Sorter {


    @Override
    public void sort(Comparable[] arr) {

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j > 0 && less(arr[j], arr[j - 1]); j--) {
                exchange(arr, j, j - 1);
            }
        }
    }
}
