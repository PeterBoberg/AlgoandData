package com.peter.driver;

import java.util.Arrays;

/**
 * Created by KungPeter on 2016-11-03.
 */
public class Driver {

    public static void main(String[] args) {


        Integer[] bajs = {1, 2, 6, 5, 3, 78, 9, 6, 4, 3, 5, 4, 7, 8, 5, 68, 4, 3,};
        sort(bajs);
        System.out.println(Arrays.toString(bajs));

    }

    static void sort(Comparable[] arr) {

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j > 0; j--) {
                if (arr[j].compareTo(arr[j - 1]) < 0) {
                    Comparable temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                }
            }
            System.out.println(Arrays.toString(arr));
        }
    }
}
