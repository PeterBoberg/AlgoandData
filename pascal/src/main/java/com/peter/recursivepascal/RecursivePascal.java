//package com.peter.recursivepascal;
//
///**
// * Created by KungPeter on 2016-11-02.
// */
//public class RecursivePascal {
//
//    private boolean upsideDown;
//    private int[][] memArr;
//
//    public RecursivePascal(boolean upsideDown, int capacity) {
//
//        this.upsideDown = upsideDown;
//        initMemArray(capacity);
//    }
//
//    private void initMemArray(int capacity) {
//
//        this.memArr = new int[capacity + 1][];
//
//        for (int i = 0; i < memArr.length; i++) {
//            this.memArr[i] = new int[(i / 2) + 1];
//        }
//    }
//
//
//
//    public void printPascal(int n) {
//
//        if (n <= 0) {
//            System.out.println(binomial(0, 0));
//
//        } else {
//
//            if (!upsideDown)
//                printPascal(n - 1);
//
//            for (int k = 0; k <= n; k++)
//                System.out.print(binomial(n, k) + "\t");
//
//            System.out.println();
//
//            if (upsideDown)
//                printPascal(n - 1);
//        }
//    }
//
//    public int binomial(int n, int k) {
//
//        int z = k;
//
//        if (k >= n / 2) {
//            if (k == n / 2) {
//                if (n % 2 == 0)
//                    z = k - 1;
//
//            } else {
//                k = n - k;
//                z = k;
//            }
//        }
//
//        if (n <= 0 || k <= 0 || n == k){
//
//            return memArr[n][0] = 1;
//        }
//
//        if (memArr[n - 1][k - 1] != 0 && memArr[n - 1][z] != 0)
//            return memArr[n][k] = memArr[n - 1][k - 1] + memArr[n - 1][z];
//
//        return binomial(n - 1, k - 1) + binomial(n - 1, k);
//    }
//
//    public static void main(String[] args) {
//
//        RecursivePascal recPas = new RecursivePascal(false,1000);
//        recPas.printPascal(1000);
//    }
//}
//
//
//
