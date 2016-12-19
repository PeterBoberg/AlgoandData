package com.peter.pascal;

/**
 * Created by KungPeter on 2016-11-03.
 */
public abstract class ErrorPascal implements Pascal {

    protected boolean upsideDown = false;
    protected PascalArray pascalArray;

    public ErrorPascal(int expectedCapacity) {
        pascalArray = new PascalArray(expectedCapacity);
    }

    protected boolean isValidInput(int n, int k) {

        return n >= 0 && k >= 0 && n >= k;
    }

    public void setUpsideDown(boolean upsideDown) {
        this.upsideDown = upsideDown;
    }
}
