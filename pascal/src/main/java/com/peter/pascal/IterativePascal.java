package com.peter.pascal;

/**
 * Created by KungPeter on 2016-11-02.
 */
public class IterativePascal extends ErrorPascal {


    public IterativePascal(int expectedCapacity) {

        super(expectedCapacity);
    }


    @Override
    public void printPascal(int n) throws InvalidBinomialException {

        if (upsideDown) {


            for (int i = n; i >= 0; i--) {
                for (int j = 0; j < i + 1; j++)

                    System.out.print(binomial(i, j) + "\t");
                System.out.println();
            }

        } else {

            for (int i = 0; i < n + 1; i++) {
                for (int j = 0; j < i + 1; j++)
                    System.out.print(binomial(i, j) + "\t");
                System.out.println();
            }

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

        pascalArray.put(0, 0, 1);
        pascalArray.put(1, 0, 1);

        if (k > n)
            k = n - k;

        for (int i = 2; i <= n; i++) {
            for (int j = 0; j <= i; j++) {

                if (j == 0 || j == i)
                    pascalArray.put(i, j, 1);

                else {

                    pascalArray.put(i, j, pascalArray.get(i - 1, j - 1) + pascalArray.get(i - 1, j));
                }

                if (i == n && j == k)
                    return pascalArray.get(i, j);
            }
        }

        return 1;
    }
}
