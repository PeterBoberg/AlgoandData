package com.peter.pascal;

/**
 * Created by KungPeter on 2016-11-02.
 */
public interface Pascal {

    void printPascal(int n) throws InvalidBinomialException;

    int binomial(int n, int k) throws InvalidBinomialException;
}
