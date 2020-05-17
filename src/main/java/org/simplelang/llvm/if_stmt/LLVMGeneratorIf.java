package org.simplelang.llvm.if_stmt;

import org.simplelang.llvm.if_stmt.comparison.ComparisonOperatorsDouble;
import org.simplelang.llvm.if_stmt.comparison.ComparisonOperatorsInteger;

import static org.simplelang.llvm.LLVMConstantsBase.loadInteger;
import static org.simplelang.llvm.LLVMConstantsBase.loadDouble;
import static org.simplelang.llvm.LLVMGeneratorBase.br;
import static org.simplelang.llvm.LLVMGeneratorBase.reg;
import static org.simplelang.llvm.LLVMGeneratorBase.mainText;
import static org.simplelang.llvm.if_stmt.LLVMConstantsIf.*;

public class LLVMGeneratorIf {

    public static void compareIntegerNumberAndVariable(String value, ComparisonOperatorsInteger comparisonOperatorsInteger, String variableName) {
        mainText += loadInteger.apply(reg, variableName);
        reg++;
        mainText += compareIntegerNumberAndIntegerVariable.apply(reg, value, comparisonOperatorsInteger, reg - 1);
        reg++;
    }

    public static void compareDoubleNumberAndVariable(String value, ComparisonOperatorsDouble comparisonOperatorsDouble, String variableName) {
        mainText += loadDouble.apply(reg, variableName);
        reg++;
        mainText += compareDoubleNumberAndDoubleVariable.apply(reg, value, comparisonOperatorsDouble, reg - 1);
        reg++;
    }

    public static void compareIntegerVariableAndNumber(String variableName, ComparisonOperatorsInteger comparisonOperatorsInteger, String value) {
        mainText += loadInteger.apply(reg, variableName);
        reg++;
        mainText += compareIntegerVariableAndIntegerNumber.apply(reg, reg - 1, comparisonOperatorsInteger, value);
        reg++;
    }

    public static void compareDoubleVariableAndNumber(String variableName, ComparisonOperatorsDouble comparisonOperatorsDouble, String value) {
        mainText += loadDouble.apply(reg, variableName);
        reg++;
        mainText += compareDoubleVariableAndDoubleNumber.apply(reg, reg - 1, comparisonOperatorsDouble, value);
        reg++;
    }

    public static void compareIntegerNumbers(String value1, ComparisonOperatorsInteger comparisonOperatorsInteger, String value2) {
        mainText += compareIntegerNumbers.apply(reg, value1, comparisonOperatorsInteger, value2);
        reg++;
    }

    public static void compareDoubleNumbers(String value1, ComparisonOperatorsDouble comparisonOperatorsDouble, String value2) {
        mainText += compareDoubleNumbers.apply(reg, value1, comparisonOperatorsDouble, value2);
        reg++;
    }

    public static void compareIntegerVariables(String variableName1, ComparisonOperatorsInteger comparisonOperatorsInteger, String variableName2) {
        mainText += loadInteger.apply(reg, variableName1);
        reg++;
        mainText += loadInteger.apply(reg, variableName2);
        reg++;
        mainText += compareIntegerVariables.apply(reg, reg - 2, comparisonOperatorsInteger, reg - 1);
        reg++;
    }

    public static void compareDoubleVariables(String variableName1, ComparisonOperatorsDouble comparisonOperatorsDouble, String variableName2) {
        mainText += loadDouble.apply(reg, variableName1);
        reg++;
        mainText += loadDouble.apply(reg, variableName2);
        reg++;
        mainText += compareDoubleVariables.apply(reg, reg - 2, comparisonOperatorsDouble, reg - 1);
        reg++;
    }

    public static int ifStart() {
        br++;
        mainText += ifStart.apply(reg-1, br);
        return br;
    }

    public static void ifEnd(int breakIndex) {
        mainText += ifEnd.apply(breakIndex);
    }

}