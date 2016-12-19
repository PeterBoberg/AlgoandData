package com.peter;

import java.util.Scanner;
import java.util.Stack;

/**
 * Created by KungPeter on 2016-11-08.
 */
public class ExpressionEvaluater {

    Stack<Double> valueStack = new Stack<Double>();
    Stack<String> operatorStack = new Stack<String>();

    public double evaluate(String expr) throws Exception {

        Scanner input = new Scanner(expr);
        input.useDelimiter(" ");


        while (input.hasNext()) {

            String token = input.next();

            if (isNumber(token))
                valueStack.push(Double.parseDouble(token));

            else if (isOperator(token)) {

                while (!operatorStack.isEmpty() && nextTokenHasgreaterOrEqualPrecedence(token, operatorStack.peek()))
                    applyOperation();

                operatorStack.push(token);
            }

            else if (isLeftParanthesis(token))
                operatorStack.push(token);

            else if (isRightParanthesis(token)) {

                while (!operatorStack.peek().equals("("))
                    applyOperation();

                operatorStack.pop();

            }

            else throw new Exception("Invalid charachter: " + token);
        }

        while (!operatorStack.isEmpty()) {
            applyOperation();
        }

        return valueStack.pop();
    }

    private void applyOperation() {
        String operator = operatorStack.pop();
        double operand1 = valueStack.pop();
        double operand2 = valueStack.pop();

        if (operator.equals("+")) valueStack.push(operand2 + operand1);
        else if (operator.equals("-")) valueStack.push(operand2 - operand1);
        else if (operator.equals("*")) valueStack.push(operand2 * operand1);
        else if (operator.equals("/")) valueStack.push(operand2 / operand1);
    }

    private boolean isNumber(String token) {
        return token.charAt(0) >= '0' && token.charAt(0) <= '9';
    }

    private boolean isOperator(String token) {

        return (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/"));
    }

    private boolean isLeftParanthesis(String token) {
        return token.equals("(");
    }

    private boolean isRightParanthesis(String token) {
        return token.equals(")");
    }

    private boolean nextTokenHasgreaterOrEqualPrecedence(String token, String nextToken) {

        if (nextToken.equals("(") || nextToken.equals(")"))
            return false;

        if ((nextToken.equals("*") || nextToken.equals("/")) && (token.equals("+") || token.equals("-")))
            return true;

        return false;

    }


}
