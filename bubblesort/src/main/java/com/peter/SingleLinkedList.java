package com.peter;

/**
 * Created by KungPeter on 2016-11-21.
 */
public class SingleLinkedList<Key extends Comparable<Key>> {

    private Node<Key> first = null;
    private int size = 0;

    public void add(Key value) {

        Node<Key> newNode = new Node<Key>(value);
        if (first == null) {
            first = newNode;

        } else {
            Node<Key> current = first;
            while (current.nextNode != null)
                current = current.nextNode;

            current.nextNode = newNode;
        }
        size++;
    }

    public Key get(int idx) {
        if (idx > size - 1 || idx < 0)
            return null;

        Node<Key> current = first;

        for (int i = 0; i < idx; i++) {

            current = current.nextNode;
        }
        return current.value;
    }

    public Node<Key> getHead() {
        return first;
    }

    public void setHead(Node<Key> head) {
        this.first = head;
    }

    public int getSize() {
        return size;
    }

    @Override
    public SingleLinkedList<Key> clone() {

        SingleLinkedList<Key> copy = new SingleLinkedList<Key>();
        Node<Key> current = first;

        while (current != null) {
            copy.add(current.value);
            current = current.nextNode;
        }

        return copy;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder("[");

        Node<Key> current = first;
        while (current != null) {
            if (current.nextNode == null)
                sb.append(current.toString() + "]");
            else {
                sb.append(current.toString() + ", ");
            }
            current = current.nextNode;
        }

        return sb.toString();
    }
}
