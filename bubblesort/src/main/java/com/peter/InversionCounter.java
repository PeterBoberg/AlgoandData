package com.peter;

/**
 * Created by KungPeter on 2016-11-21.
 */

public class InversionCounter {

    private static int noOfInversions = 0;

    public static <Key extends Comparable<Key>> int count(SingleLinkedList<Key> singleLinkedList) {

        noOfInversions = 0;
        mergeSort(singleLinkedList.clone().getHead());
        return noOfInversions;
    }

    // PRIVATE DOMAIN
    /////////////////////////////////////////////////////////////////////////////


    private static <Key extends Comparable<Key>> Node<Key> mergeSort(Node<Key> head) {

        if (head == null || head.nextNode == null)
            return head;

        Node<Key> middle = findMiddleNode(head);
        Node<Key> upperHalf = middle.nextNode;
        middle.nextNode = null;

        return merge(mergeSort(head), mergeSort(upperHalf));
    }

    private static <Key extends Comparable<Key>> Node<Key> merge(Node<Key> lowerPointer, Node<Key> upperPointer) {

        Node<Key> ghostNode = new Node<Key>();
        Node<Key> mainPointer = ghostNode;

        while (lowerPointer != null && upperPointer != null) {

            if (lowerPointer.value.compareTo(upperPointer.value) <= 0) {
                mainPointer.nextNode = lowerPointer;
                lowerPointer = lowerPointer.nextNode;

            } else {

                countInversions(lowerPointer);
                mainPointer.nextNode = upperPointer;
                upperPointer = upperPointer.nextNode;
            }
            mainPointer = mainPointer.nextNode;

        }
        mainPointer.nextNode = (lowerPointer == null) ? upperPointer : lowerPointer;

        return ghostNode.nextNode;
    }

    private static <Key extends Comparable<Key>> Node<Key> findMiddleNode(Node<Key> head) {

        Node<Key> slow = head;
        Node<Key> fast = head;

        while (fast.nextNode != null && fast.nextNode.nextNode != null) {
            slow = slow.nextNode;
            fast = fast.nextNode.nextNode;

        }
        return slow;
    }

    private static <Key extends Comparable<Key>> void countInversions(Node<Key> lowerHalfNode) {

        Node<Key> current = lowerHalfNode;
        while (current != null) {
            noOfInversions++;
            current = current.nextNode;
        }

    }
}
