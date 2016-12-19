package com.peter;

import java.util.*;
import java.util.function.UnaryOperator;

/**
 * Created by KungPeter on 2016-11-29.
 */
public class OrderedList<T extends Comparable<T>> implements List<T>, Iterable<T> {

    private List<T> list = new ArrayList<T>();

    public boolean add(T value) {

        if (list.size() == 0) {
            return list.add(value);
        }

        list.add(value);
        int startIdx = list.size() - 1;

        while (startIdx > 0 && less(list.get(startIdx), list.get(startIdx - 1))) {
            swap(startIdx, startIdx - 1);
            startIdx--;
        }
        return true;
    }

    public boolean remove(Object o) {
        return list.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    public boolean addAll(Collection<? extends T> c) {
        return list.addAll(c);
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        return list.addAll(index, c);
    }

    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    public void replaceAll(UnaryOperator<T> operator) {
        list.replaceAll(operator);
    }

    public void sort(Comparator<? super T> c) {
        list.sort(c);
    }

    public void clear() {
        list.clear();
    }

    @Override
    public boolean equals(Object o) {
        return list.equals(o);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }

    public T get(int index) {
        return list.get(index);
    }

    public T set(int index, T element) {
        return list.set(index, element);
    }

    public void add(int index, T element) {
        list.add(index, element);
    }

    public T remove(int index) {
        return list.remove(index);
    }

    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    public ListIterator<T> listIterator() {
        return list.listIterator();
    }

    public ListIterator<T> listIterator(int index) {
        return list.listIterator(index);
    }

    public List<T> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    private boolean less(T t, T t1) {

        return t.compareTo(t1) < 0;
    }

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }


    public boolean contains(Object o) {
        return list.contains(o);
    }

    public Iterator<T> iterator() {
        return list.iterator();
    }

    public Object[] toArray() {
        return list.toArray();
    }


    public <T1> T1[] toArray(T1[] a) {
        return list.toArray(a);
    }

    @Override
    public String toString() {


        StringBuilder sb = new StringBuilder("com.peter.OrderdList\n");
        for (T value : list)
            sb.append(value.toString() + "\n");

        return sb.toString();
    }

    private void swap(int second, int first) {
        T temp = list.get(second);
        list.set(second, list.get(first));
        list.set(first, temp);
    }

}
