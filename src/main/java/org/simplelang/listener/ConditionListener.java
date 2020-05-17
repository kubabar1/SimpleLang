package org.simplelang.listener;

import org.simplelang.SimpleLangParser;
import org.simplelang.listener.container.VariablesContainer;
import org.simplelang.listener.container.base.VariableType;
import org.simplelang.llvm.comparison.LLVMGeneratorComparison;
import org.simplelang.llvm.comparison.operators.ComparisonOperatorsDouble;
import org.simplelang.llvm.comparison.operators.ComparisonOperatorsInteger;

import java.util.Objects;

import static org.simplelang.listener.predicates.IfStatementPredicates.*;

public class ConditionListener extends org.simplelang.SimpleLangBaseListener {

    @Override
    public void exitCondition(SimpleLangParser.ConditionContext ctx) {
        if (Objects.nonNull(ctx.BooleanLiteral())) { // if(x) where x = boolean
            boolean booleanVal = Boolean.parseBoolean(ctx.BooleanLiteral().getText());
            if (booleanVal) {
                // TODO: Add boolean type
            } else {
                // TODO: Add boolean type
            }
        } else if (Objects.nonNull(ctx.variable())) { // if(boolean)
            // TODO: Add boolean type
        } else { // if(expression)
            SimpleLangParser.ComparisonStatementContext leftStatement = ctx.leftStatement().comparisonStatement();
            SimpleLangParser.ComparisonStatementContext rightStatement = ctx.rightStatement().comparisonStatement();
            String comparisonOperator = ctx.comparisonOperators().getText();

            if (numbersComparison.test(leftStatement, rightStatement)) {
                if (isIntegerNumber.test(leftStatement) && isIntegerNumber.test(rightStatement)) {
                    LLVMGeneratorComparison.compareIntegerNumbers(leftStatement.getText(), ComparisonOperatorsInteger.findByKey(comparisonOperator), rightStatement.getText());
                } else if (isFloatingPointNumber.test(leftStatement) && isFloatingPointNumber.test(rightStatement)) {
                    LLVMGeneratorComparison.compareDoubleNumbers(leftStatement.getText(), ComparisonOperatorsDouble.findByKey(comparisonOperator), rightStatement.getText());
                } else {
                    System.err.println("Line " + ctx.getStart().getLine() + ", incompatible variable types");
                }
            } else if (variablesComparison.test(leftStatement, rightStatement)) {
                VariableType variableType1 = VariablesContainer.getInstance().getVariableType(leftStatement.getText());
                VariableType variableType2 = VariablesContainer.getInstance().getVariableType(rightStatement.getText());

                VariablesContainer vc = VariablesContainer.getInstance();

                if (!VariablesContainer.getInstance().variableExists(leftStatement.getText())) {
                    System.err.println("Line " + ctx.getStart().getLine() + ", variable does not exists: "
                            + leftStatement.getText());
                } else if (!VariablesContainer.getInstance().variableExists(rightStatement.getText())) {
                    System.err.println("Line " + ctx.getStart().getLine() + ", variable does not exists: "
                            + rightStatement.getText());
                } else if (!variableType1.equals(variableType2)) {
                    System.err.println("Line " + ctx.getStart().getLine() + ", incompatible variable types: "
                            + variableType1.toString() + ", " + variableType1.toString());
                }

                if (VariableType.INT.equals(variableType1)) {
                    LLVMGeneratorComparison.compareIntegerVariables(leftStatement.getText(), ComparisonOperatorsInteger.findByKey(comparisonOperator), rightStatement.getText());
                } else if (VariableType.FLOAT.equals(variableType1)) {
                    LLVMGeneratorComparison.compareDoubleVariables(leftStatement.getText(), ComparisonOperatorsDouble.findByKey(comparisonOperator), rightStatement.getText());
                }
            } else if (variableAndNumberComparison.test(leftStatement, rightStatement)) {
                if (isNumber.test(leftStatement)) {
                    VariableType variableType = VariablesContainer.getInstance().getVariableType(rightStatement.getText());

                    if (isIntegerNumber.test(leftStatement) && VariableType.INT.equals(variableType)) {
                        LLVMGeneratorComparison.compareIntegerNumberAndVariable(leftStatement.getText(), ComparisonOperatorsInteger.findByKey(comparisonOperator), rightStatement.getText());
                    } else if (isFloatingPointNumber.test(leftStatement) && VariableType.FLOAT.equals(variableType)) {
                        LLVMGeneratorComparison.compareDoubleNumberAndVariable(leftStatement.getText(), ComparisonOperatorsDouble.findByKey(comparisonOperator), rightStatement.getText());
                    } else {
                        System.err.println("Line " + ctx.getStart().getLine() + ", incompatible variable types");
                    }
                } else if (isVariable.test(leftStatement)) {
                    VariableType variableType = VariablesContainer.getInstance().getVariableType(leftStatement.getText());

                    if (isIntegerNumber.test(rightStatement) && VariableType.INT.equals(variableType)) {
                        LLVMGeneratorComparison.compareIntegerVariableAndNumber(leftStatement.getText(), ComparisonOperatorsInteger.findByKey(comparisonOperator), rightStatement.getText());
                    } else if (isFloatingPointNumber.test(rightStatement) && VariableType.FLOAT.equals(variableType)) {
                        LLVMGeneratorComparison.compareDoubleVariableAndNumber(leftStatement.getText(), ComparisonOperatorsDouble.findByKey(comparisonOperator), rightStatement.getText());
                    } else {
                        System.err.println("Line " + ctx.getStart().getLine() + ", incompatible variable types");
                    }
                }
            } else if (variableAndBooleanComparison.test(leftStatement, rightStatement)) {
                // TODO: Add boolean type
            }
        }
    }

}
