package com.peter.proj2;


import java.util.HashMap;
import java.util.List;

/**
 * Created by KungPeter on 2016-12-19.
 */
public class Cache {

    private final HashMap<Operation, List<DocumentEntry>> cash = new HashMap<>();

    public void put(Operation operation, List<DocumentEntry> list) {
        cash.put(operation, list);
    }

    public List<DocumentEntry> get(Operation operation) {

        List<DocumentEntry> returnedList = cash.get(operation);

        if (returnedList == null)
            returnedList = cash.get(operation.reverse());
        return returnedList;

    }

    public boolean hasResult(Operation operation) {

        if (!operation.operator.equals("-")) {

            if (cash.containsKey(operation))
                return true;
            if (cash.containsKey(operation.reverse()))
                return true;
        }

        else {
            if (cash.containsKey(operation))
                return true;
        }
        return false;
    }


}
