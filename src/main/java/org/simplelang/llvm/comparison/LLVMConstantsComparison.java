package org.simplelang.llvm.comparison;

import org.simplelang.llvm.comparison.operators.ComparisonOperatorsDouble;
import org.simplelang.llvm.comparison.operators.ComparisonOperatorsInteger;
import org.simplelang.utils.FunctionalInterfaceHelper;

abstract class LLVMConstantsComparison {

    static final FunctionalInterfaceHelper.FourFunction<Integer, Integer, ComparisonOperatorsInteger, Integer, String> compareIntegerVariables = (reg, var1, comparisonOperator, var2) ->
            "\t%" + reg + " = icmp " + comparisonOperator.getName() + " i32 %" + var1 + ", %" + var2 + "\n";

    static final FunctionalInterfaceHelper.FourFunction<Integer, Integer, ComparisonOperatorsDouble, Integer, String> compareDoubleVariables = (reg, var1, comparisonOperator, var2) ->
            "\t%" + reg + " = fcmp " + comparisonOperator.getName() + " double %" + var1 + ", %" + var2 + "\n";

    static final FunctionalInterfaceHelper.FourFunction<Integer, String, ComparisonOperatorsInteger, String, String> compareIntegerNumbers = (reg, nb1, comparisonOperator, nb2) ->
            "\t%" + reg + " = icmp " + comparisonOperator.getName() + " i32 " + nb1 + ", " + nb2 + "\n";

    static final FunctionalInterfaceHelper.FourFunction<Integer, String, ComparisonOperatorsDouble, String, String> compareDoubleNumbers = (reg, nb1, comparisonOperator, nb2) ->
            "\t%" + reg + " = fcmp " + comparisonOperator.getName() + " double " + nb1 + ", " + nb2 + "\n";

    static final FunctionalInterfaceHelper.FourFunction<Integer, String, ComparisonOperatorsInteger, Integer, String> compareIntegerNumberAndIntegerVariable = (reg, nb, comparisonOperator, var) ->
            "\t%" + reg + " = icmp " + comparisonOperator.getName() + " i32 " + nb + ", %" + var + "\n";

    static final FunctionalInterfaceHelper.FourFunction<Integer, Integer, ComparisonOperatorsInteger, String, String> compareIntegerVariableAndIntegerNumber = (reg, var, comparisonOperator, nb) ->
            "\t%" + reg + " = icmp " + comparisonOperator.getName() + " i32 %" + var + ", " + nb + "\n";

    static final FunctionalInterfaceHelper.FourFunction<Integer, String, ComparisonOperatorsDouble, Integer, String> compareDoubleNumberAndDoubleVariable = (reg, nb, comparisonOperator, var) ->
            "\t%" + reg + " = fcmp " + comparisonOperator.getName() + " double " + nb + ", %" + var + "\n";

    static final FunctionalInterfaceHelper.FourFunction<Integer, Integer, ComparisonOperatorsDouble, String, String> compareDoubleVariableAndDoubleNumber = (reg, var, comparisonOperator, nb) ->
            "\t%" + reg + " = fcmp " + comparisonOperator.getName() + " double %" + var + ", " + nb + "\n";

}
