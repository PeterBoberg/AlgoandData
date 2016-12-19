package com.peter;

/**
 * Created by KungPeter on 2016-11-08.
 */
public abstract class Sorter {

    protected boolean less(Comparable first, Comparable second) {
        return first.compareTo(second) < 0;
    }

    protected void exchange(Comparable[] arr, int firstIdx, int secIdx) {
        Comparable temp = arr[firstIdx];
        arr[firstIdx] = arr[secIdx];
        arr[secIdx] = temp;
    }

    protected void show(Comparable[] arr) {
        for (Comparable c : arr)
            System.out.print(c + " ");

        System.out.println();
    }

    public boolean isSorted(Comparable[] arr) {


        for (int i = 1; i < arr.length; i++)
            if (arr[i].compareTo(arr[i - 1]) < 0)
                return false;

        return true;
    }

    public abstract void sort(Comparable[] arr);
}
