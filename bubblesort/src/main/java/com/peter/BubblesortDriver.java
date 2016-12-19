package com.peter;

import java.util.Random;

public class BubblesortDriver {
    public static void main(String[] args) {

        SingleLinkedList<Integer> list = createList(10);
        System.out.println("Generated list:");
        System.out.println(list);
        System.out.println("Num of inversions = " + InversionCounter.count(list));

        System.out.println();
        System.out.println("Sorting list...");
        Bubblesort.sort(list);
        System.out.println(list);
        System.out.println("Num of swappes = " + Bubblesort.getNoOfSwaps());


    }

    public static SingleLinkedList<Integer> createList(int size) {

        SingleLinkedList<Integer> list = new SingleLinkedList<Integer>();
        Random random = new Random();
        for (int i = 0; i < size; i++) {

            list.add(random.nextInt(size));
        }

        return list;
    }


}
