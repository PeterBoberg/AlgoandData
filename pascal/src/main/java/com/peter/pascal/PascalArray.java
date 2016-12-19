package com.peter.pascal;

/**
 * Created by KungPeter on 2016-11-04.
 */
class PascalArray {

    private int[][] memArr;

    PascalArray(int n) {
        init(n);
    }

    private int[][] init(int n) {

        memArr = new int[n + 1][];

        for (int i = 0; i < memArr.length; i++)
            memArr[i] = new int[(i / 2) + 1];

        return memArr;
    }

    private boolean hasEnoughMemory(int n) {
        return n < memArr.length;
    }


    private int[][] doubleArraySize(int n) {

        int[][] newArr = new int[n * 2][];

        for (int i = 0; i < newArr.length; i++) {

            if (i < memArr.length)
                newArr[i] = memArr[i];

            else
                newArr[i] = new int[(i / 2) + 1];
        }
        return newArr;
    }


    int savedBinomial(int n, int k) {

        if (!hasEnoughMemory(n))
            memArr = doubleArraySize(n);

        int z = k;

        if (k >= n / 2) {
            if (k == n / 2) {
                if (n % 2 == 0)
                    z = k - 1;

            } else {
                k = n - k;
                z = k;
            }
        }

        if (k == 0 || n == 0 || n == k)
            return memArr[n][0] = 1;

        if (memArr[n - 1][k - 1] != 0 && memArr[n - 1][z] != 0)
            return memArr[n][k] = memArr[n - 1][k - 1] + memArr[n - 1][z];

        return 0;
    }


    int put(int n, int k, int value) {

        k = convertK(n, k);

        memArr[n][k] = value;
        return value;
    }

    int get(int n, int k) {

        k = convertK(n, k);

        return memArr[n][k];
    }

    private int convertK(int n, int k) {

        if (k > n / 2)
            k = n - k;

        return k;
    }
}
