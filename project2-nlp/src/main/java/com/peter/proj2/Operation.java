package com.peter.proj2;

/**
 * Created by KungPeter on 2016-12-20.
 */
public class Operation {

    public final String operand1;
    public final String operand2;
    public final String operator;
    public final String infixExpression;


    public Operation(String operand1, String operand2, String operator) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operator = operator;
        this.infixExpression = "( " + operand1 + " " + operator + " " + operand2 + " )";
    }

    public Operation reverse(){
        return new Operation(operand2, operand1, operator);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Operation operation = (Operation) o;

        if (operand1 != null ? !operand1.equals(operation.operand1) : operation.operand1 != null) return false;
        if (operand2 != null ? !operand2.equals(operation.operand2) : operation.operand2 != null) return false;
        if (operator != null ? !operator.equals(operation.operator) : operation.operator != null) return false;
        return infixExpression != null ? infixExpression.equals(operation.infixExpression) : operation.infixExpression == null;
    }

    @Override
    public int hashCode() {
        int result = operand1 != null ? operand1.hashCode() : 0;
        result = 31 * result + (operand2 != null ? operand2.hashCode() : 0);
        result = 31 * result + (operator != null ? operator.hashCode() : 0);
        result = 31 * result + (infixExpression != null ? infixExpression.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {

        return infixExpression;
    }
}