package org.simplelang.llvm.comparison;

import org.simplelang.llvm.comparison.operators.ComparisonOperatorsDouble;
import org.simplelang.llvm.comparison.operators.ComparisonOperatorsInteger;

import static org.simplelang.llvm.LLVMConstantsBase.loadDouble;
import static org.simplelang.llvm.LLVMConstantsBase.loadInteger;
import static org.simplelang.llvm.LLVMGeneratorBase.buffer;
import static org.simplelang.llvm.LLVMGeneratorBase.tmp;
import static org.simplelang.llvm.comparison.LLVMConstantsComparison.compareDoubleNumberAndDoubleVariable;
import static org.simplelang.llvm.comparison.LLVMConstantsComparison.compareIntegerNumberAndIntegerVariable;
import static org.simplelang.llvm.comparison.LLVMConstantsComparison.compareIntegerVariableAndIntegerNumber;
import static org.simplelang.llvm.comparison.LLVMConstantsComparison.compareDoubleVariableAndDoubleNumber;
import static org.simplelang.llvm.comparison.LLVMConstantsComparison.compareIntegerNumbers;
import static org.simplelang.llvm.comparison.LLVMConstantsComparison.compareDoubleNumbers;
import static org.simplelang.llvm.comparison.LLVMConstantsComparison.compareIntegerVariables;
import static org.simplelang.llvm.comparison.LLVMConstantsComparison.compareDoubleVariables;

public class LLVMGeneratorComparison {

    public static void compareIntegerNumberAndVariable(String value, ComparisonOperatorsInteger comparisonOperatorsInteger, String variableName) {
        // buffer += loadInteger.apply(tmp, variableName);
        // tmp++;
        buffer += compareIntegerNumberAndIntegerVariable.apply(tmp, value, comparisonOperatorsInteger, tmp - 1);
        tmp++;
    }

    public static void compareDoubleNumberAndVariable(String value, ComparisonOperatorsDouble comparisonOperatorsDouble, String variableName) {
        // buffer += loadDouble.apply(tmp, variableName);
        // tmp++;
        buffer += compareDoubleNumberAndDoubleVariable.apply(tmp, value, comparisonOperatorsDouble, tmp - 1);
        tmp++;
    }

    public static void compareIntegerVariableAndNumber(String variableName, ComparisonOperatorsInteger comparisonOperatorsInteger, String value) {
        // buffer += loadInteger.apply(tmp, variableName);
        // tmp++;
        buffer += compareIntegerVariableAndIntegerNumber.apply(tmp, tmp - 1, comparisonOperatorsInteger, value);
        tmp++;
    }

    public static void compareDoubleVariableAndNumber(String variableName, ComparisonOperatorsDouble comparisonOperatorsDouble, String value) {
        // buffer += loadDouble.apply(tmp, variableName);
        // tmp++;
        buffer += compareDoubleVariableAndDoubleNumber.apply(tmp, tmp - 1, comparisonOperatorsDouble, value);
        tmp++;
    }

    public static void compareIntegerNumbers(String value1, ComparisonOperatorsInteger comparisonOperatorsInteger, String value2) {
        buffer += compareIntegerNumbers.apply(tmp, value1, comparisonOperatorsInteger, value2);
        tmp++;
    }

    public static void compareDoubleNumbers(String value1, ComparisonOperatorsDouble comparisonOperatorsDouble, String value2) {
        buffer += compareDoubleNumbers.apply(tmp, value1, comparisonOperatorsDouble, value2);
        tmp++;
    }

    public static void compareIntegerVariables(String variableName1, ComparisonOperatorsInteger comparisonOperatorsInteger, String variableName2) {
        // buffer += loadInteger.apply(tmp, variableName1);
        // tmp++;
        // buffer += loadInteger.apply(tmp, variableName2);
        // tmp++;
        buffer += compareIntegerVariables.apply(tmp, tmp - 2, comparisonOperatorsInteger, tmp - 1);
        tmp++;
    }

    public static void compareDoubleVariables(String variableName1, ComparisonOperatorsDouble comparisonOperatorsDouble, String variableName2) {
        // buffer += loadDouble.apply(tmp, variableName1);
        // tmp++;
        // buffer += loadDouble.apply(tmp, variableName2);
        // tmp++;
        buffer += compareDoubleVariables.apply(tmp, tmp - 2, comparisonOperatorsDouble, tmp - 1);
        tmp++;
    }
    
}
