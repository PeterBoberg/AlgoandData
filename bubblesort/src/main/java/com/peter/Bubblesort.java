package com.peter;

/**
 * Created by KungPeter on 2016-11-21.
 */
public class Bubblesort {

    private static int noOfSwaps = 0;

    public static <Key extends Comparable<Key>> void sort(SingleLinkedList<Key> singleLinkedList) {

        Node head = sort(singleLinkedList.getHead(), singleLinkedList.getSize());
        singleLinkedList.setHead(head);
    }

    public static <Key extends Comparable<Key>> Node sort(Node<Key> head, int size) {

        if (head == null || head.nextNode == null)
            return head;

        noOfSwaps = 0;
        boolean swapped = true;
        int sortedWall = size;

        while (swapped) {
            int turn = 0;
            swapped = false;

            Node<Key> previousNode = null;
            Node<Key> currentNode = head;
            Node<Key> nextNode = head.nextNode;

            while (nextNode != null && turn < sortedWall) {

                // if element needs to be swapped
                if (currentNode.value.compareTo(nextNode.value) > 0) {

                    swapped = true;
                    noOfSwaps++;

                    // if in the begining of the list
                    if (previousNode == null)
                        head = nextNode;

                        // else were not in the beginning of list
                    else
                        previousNode.nextNode = nextNode;

                    Node temp = nextNode.nextNode;
                    nextNode.nextNode = currentNode;
                    currentNode.nextNode = temp;
                    previousNode = nextNode;
                    nextNode = temp;

                }

                // else element is in relative order, continue forward
                else {
                    previousNode = currentNode;
                    currentNode = nextNode;
                    nextNode = nextNode.nextNode;
                }
                turn++;
            }
            sortedWall--;
        }
        return head;
    }

    public static int getNoOfSwaps() {
        return noOfSwaps;
    }


}
