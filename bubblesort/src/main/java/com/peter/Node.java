package com.peter;

/**
 * Created by KungPeter on 2016-11-21.
 */
public class Node<Key extends Comparable<Key>> {

    Key value;
    Node<Key> nextNode;

    public Node(){}

    public Node(Key value) {
        this.value = value;
        this.nextNode = null;
    }

    public Key getValue() {
        return value;
    }

    @Override
    public String toString() {
       return value.toString();
    }
}
