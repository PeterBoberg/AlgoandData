package com.peter;

import java.util.Map;

/**
 * Created by KungPeter on 2016-11-25.
 */
public class Entry implements Map.Entry<String, Integer>, Comparable<Entry> {

    private String key;
    private int value;

    public Entry(String key, int value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return this.key;
    }

    public Integer getValue() {
        return this.value;
    }

    public Integer setValue(Integer value) {
        this.value = value;
        return value;
    }

    @Override
    public String toString() {

        return key + " = " + value;
    }

    public int compareTo(Entry o) {
        if (value > o.value)
            return 1;
        if (value < o.value)
            return -1;
        return 0;
    }
}
