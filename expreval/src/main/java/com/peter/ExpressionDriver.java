package com.peter;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class ExpressionDriver
{
    public static void main( String[] args ) throws Exception {
        ExpressionEvaluater evl = new ExpressionEvaluater();

        double res = evl.evaluate("5 * ( 2 + 3 ) / 7");
        System.out.println(res);

    }
}
