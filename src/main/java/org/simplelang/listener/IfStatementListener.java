package org.simplelang.listener;

import org.simplelang.SimpleLangParser;
import org.simplelang.listener.container.VariablesContainer;
import org.simplelang.listener.container.base.VariableType;
import org.simplelang.llvm.if_stmt.LLVMGeneratorIf;
import org.simplelang.llvm.if_stmt.comparison.ComparisonOperatorsDouble;
import org.simplelang.llvm.if_stmt.comparison.ComparisonOperatorsInteger;

import java.util.Objects;

import static org.simplelang.listener.predicates.IfStatementPredicates.*;

public class IfStatementListener extends org.simplelang.SimpleLangBaseListener {

    @Override
    public void enterBlockif(SimpleLangParser.BlockifContext ctx) {
        int br = LLVMGeneratorIf.ifStart();
        VariablesContainer.getInstance().pushToBrStack(br);
    }

    @Override
    public void exitBlockif(SimpleLangParser.BlockifContext ctx) {
        int br = VariablesContainer.getInstance().popFromBrStack();
        LLVMGeneratorIf.ifEnd(br);
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
                    LLVMGeneratorIf.compareIntegerNumbers(leftStatement.getText(), ComparisonOperatorsInteger.findByKey(comparisonOperator), rightStatement.getText());
                } else if (isFloatingPointNumber.test(leftStatement) && isFloatingPointNumber.test(rightStatement)) {
                    LLVMGeneratorIf.compareDoubleNumbers(leftStatement.getText(), ComparisonOperatorsDouble.findByKey(comparisonOperator), rightStatement.getText());
                } else {
                    System.err.println("Line " + ctx.getStart().getLine() + ", incompatible variable types");
                }
            } else if (variablesComparison.test(leftStatement, rightStatement)) {
                VariableType variableType1 = VariablesContainer.getInstance().getVariableType(leftStatement.getText());
                VariableType variableType2 = VariablesContainer.getInstance().getVariableType(leftStatement.getText());

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

                if (variableType1.equals(VariableType.INT)) {
                    LLVMGeneratorIf.compareIntegerVariables(leftStatement.getText(), ComparisonOperatorsInteger.findByKey(comparisonOperator), rightStatement.getText());
                } else if (variableType1.equals(VariableType.FLOAT)) {
                    LLVMGeneratorIf.compareDoubleVariables(leftStatement.getText(), ComparisonOperatorsDouble.findByKey(comparisonOperator), rightStatement.getText());
                }
            } else if (variableAndNumberComparison.test(leftStatement, rightStatement)) {
                if (isNumber.test(leftStatement)) {
                    VariableType variableType = VariablesContainer.getInstance().getVariableType(rightStatement.getText());

                    if (isIntegerNumber.test(leftStatement) && variableType.equals(VariableType.INT)) {
                        LLVMGeneratorIf.compareIntegerNumberAndVariable(leftStatement.getText(), ComparisonOperatorsInteger.findByKey(comparisonOperator), rightStatement.getText());
                    } else if (isFloatingPointNumber.test(leftStatement) && variableType.equals(VariableType.FLOAT)) {
                        LLVMGeneratorIf.compareDoubleNumberAndVariable(leftStatement.getText(), ComparisonOperatorsDouble.findByKey(comparisonOperator), rightStatement.getText());
                    } else {
                        System.err.println("Line " + ctx.getStart().getLine() + ", incompatible variable types");
                    }
                } else if (isVariable.test(leftStatement)) {
                    VariableType variableType = VariablesContainer.getInstance().getVariableType(leftStatement.getText());

                    if (isIntegerNumber.test(rightStatement) && variableType.equals(VariableType.INT)) {
                        LLVMGeneratorIf.compareIntegerVariableAndNumber(leftStatement.getText(), ComparisonOperatorsInteger.findByKey(comparisonOperator), rightStatement.getText());
                    } else if (isFloatingPointNumber.test(rightStatement) && variableType.equals(VariableType.FLOAT)) {
                        LLVMGeneratorIf.compareDoubleVariableAndNumber(leftStatement.getText(), ComparisonOperatorsDouble.findByKey(comparisonOperator), rightStatement.getText());
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
