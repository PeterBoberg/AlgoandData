package com.peter;

import edu.princeton.cs.algs4.Stopwatch;

import java.util.Arrays;

/**
 * Hello world!
 */
public class SortDriver {

    public static void main(String[] args) {

        SortDriver sortDriver = new SortDriver();

        double selectionRes = sortDriver.getAvgTimeUsingDoubleArray(100000, 10, "Selection");
        double insertionRes = sortDriver.getAvgTimeUsingDoubleArray(100000, 10, "Insertion");

        System.out.println("Avg result for selectionsort: " + selectionRes);
        System.out.println("Avg result for Insertionsort: " + insertionRes);

        Arrays.sort(new int[]{1, 2, 3, 4, 5});

        int[] test = {6, 3, 7, 8, 2, 3, 5, 1, 5,};
        MergeSorter mergeSorter = new MergeSorter();
//        mergeSorter.mergeSort(test);
        System.out.println(Arrays.toString(test));


    }

    public double getAvgTimeUsingDoubleArray(int arraySize, int repeatingTimes, String algorithm) {

        Sorter sorter;
        if (algorithm.equals("Selection")) sorter = new SelectionSorter();
        else if (algorithm.equals("Insertion")) sorter = new InsertionSorter();
        else if (algorithm.equals("shell")) sorter = new ShellSorter();
        else return 0;

        double testTime = 0;

        for (int i = 0; i < repeatingTimes; i++) {

            Double[] testArr = createRandomArray(arraySize);
            Stopwatch sw = new Stopwatch();
            sorter.sort(testArr);
            testTime += sw.elapsedTime();
        }
        return testTime / repeatingTimes;
    }

    private Double[] createRandomArray(int arraySixe) {

        Double[] arr = new Double[arraySixe];

        for (int i = 0; i < arr.length; i++)
            arr[i] = Math.random();

        return arr;
    }
}
