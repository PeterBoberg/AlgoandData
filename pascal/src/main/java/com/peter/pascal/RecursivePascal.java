package com.peter.pascal;

/**
 * Created by KungPeter on 2016-11-02.
 */
public class RecursivePascal extends ErrorPascal {


    public RecursivePascal(int expectedCapacity) {

        super(expectedCapacity);
    }


    @Override
    public void printPascal(int n) throws InvalidBinomialException {

        if (n <= 0) {
            System.out.println(binomial(0, 0));

        } else {

            if (!upsideDown)
                printPascal(n - 1);

            for (int k = 0; k <= n; k++)
                System.out.print(binomial(n, k) + "\t");

            System.out.println();

            if (upsideDown)
                printPascal(n - 1);
        }
    }

    @Override
    public int binomial(int n, int k) throws InvalidBinomialException {

        if (!isValidInput(n, k))
            throw new InvalidBinomialException("Invalid parameters: n = " + n + " k = " + k);

        int binomial;
        if ((binomial = pascalArray.savedBinomial(n, k)) != 0)
            return binomial;

        // This part of the method only executes if no saved value can be retrieved from
        // the PascalArray datastructure

        return binomial(n - 1, k - 1) + binomial(n - 1, k);
    }

}



