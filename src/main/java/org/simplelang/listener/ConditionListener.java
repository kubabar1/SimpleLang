package org.simplelang.listener;

import org.simplelang.SimpleLangParser;
import org.simplelang.error.ErrorMessages;
import org.simplelang.listener.containers.BaseContainer;
import org.simplelang.listener.containers.model.VariableType;
import org.simplelang.llvm.comparison.LLVMGeneratorComparison;
import org.simplelang.llvm.comparison.operators.ComparisonOperatorsDouble;
import org.simplelang.llvm.comparison.operators.ComparisonOperatorsInteger;

import java.util.Objects;

import static org.simplelang.listener.predicates.IfStatementPredicates.*;
import static org.simplelang.listener.utils.VariableUtils.getVariableType;

public class ConditionListener extends org.simplelang.SimpleLangBaseListener {

    private BaseContainer baseContainer;

    public ConditionListener() {
        baseContainer = BaseContainer.getInstance();
    }

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
                String leftStatementText = leftStatement.getText();
                String rightStatementText = rightStatement.getText();
                VariableType variableTypeLeft = getVariableType(leftStatementText);
                VariableType variableTypeRight = getVariableType(rightStatementText);

                if (Objects.isNull(variableTypeLeft)) {
                    ErrorMessages.error(ctx.getStart().getLine(), "variable does not exists: " + variableTypeLeft);
                }
                if (Objects.isNull(variableTypeRight)) {
                    ErrorMessages.error(ctx.getStart().getLine(), "variable does not exists: " + variableTypeRight);
                }
                if (!variableTypeLeft.equals(variableTypeRight)) {
                    ErrorMessages.error(ctx.getStart().getLine(), "incompatible variable type: " + variableTypeLeft.toString() + ", " + variableTypeRight.toString());
                }

                if (VariableType.INT.equals(variableTypeLeft)) {
                    LLVMGeneratorComparison.compareIntegerVariables(leftStatement.getText(), ComparisonOperatorsInteger.findByKey(comparisonOperator), rightStatement.getText());
                } else if (VariableType.DOUBLE.equals(variableTypeLeft)) {
                    LLVMGeneratorComparison.compareDoubleVariables(leftStatement.getText(), ComparisonOperatorsDouble.findByKey(comparisonOperator), rightStatement.getText());
                }
            } else if (variableAndNumberComparison.test(leftStatement, rightStatement)) {
                if (isNumber.test(leftStatement)) {
                    VariableType variableType = getVariableType(rightStatement.getText());

                    if (Objects.isNull(variableType)) {
                        ErrorMessages.error(ctx.getStart().getLine(), "variable does not exists: " + variableType);
                    }

                    if (isIntegerNumber.test(leftStatement) && VariableType.INT.equals(variableType)) {
                        LLVMGeneratorComparison.compareIntegerNumberAndVariable(leftStatement.getText(), ComparisonOperatorsInteger.findByKey(comparisonOperator), rightStatement.getText());
                    } else if (isFloatingPointNumber.test(leftStatement) && VariableType.DOUBLE.equals(variableType)) {
                        LLVMGeneratorComparison.compareDoubleNumberAndVariable(leftStatement.getText(), ComparisonOperatorsDouble.findByKey(comparisonOperator), rightStatement.getText());
                    } else {
                        ErrorMessages.error(ctx.getStart().getLine(), "incompatible variable types");
                    }
                } else if (isVariable.test(leftStatement)) {
                    VariableType variableType = getVariableType(leftStatement.getText());

                    if (Objects.isNull(variableType)) {
                        ErrorMessages.error(ctx.getStart().getLine(), "variable does not exists: " + variableType);
                    }

                    if (isIntegerNumber.test(rightStatement) && VariableType.INT.equals(variableType)) {
                        LLVMGeneratorComparison.compareIntegerVariableAndNumber(leftStatement.getText(), ComparisonOperatorsInteger.findByKey(comparisonOperator), rightStatement.getText());
                    } else if (isFloatingPointNumber.test(rightStatement) && VariableType.DOUBLE.equals(variableType)) {
                        LLVMGeneratorComparison.compareDoubleVariableAndNumber(leftStatement.getText(), ComparisonOperatorsDouble.findByKey(comparisonOperator), rightStatement.getText());
                    } else {
                        ErrorMessages.error(ctx.getStart().getLine(), "incompatible variable types");
                    }
                }
            } else if (variableAndBooleanComparison.test(leftStatement, rightStatement)) {
                // TODO: Add boolean type
            }
        }
    }

}
