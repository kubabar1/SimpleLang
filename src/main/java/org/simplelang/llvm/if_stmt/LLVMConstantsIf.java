package org.simplelang.llvm.if_stmt;

import org.simplelang.llvm.if_stmt.comparison.ComparisonOperatorsDouble;
import org.simplelang.llvm.if_stmt.comparison.ComparisonOperatorsInteger;
import org.simplelang.utils.FunctionalInterfaceHelper.FourFunction;

import java.util.function.BiFunction;
import java.util.function.Function;

abstract class LLVMConstantsIf {

    static final FourFunction<Integer, Integer, ComparisonOperatorsInteger, Integer, String> compareIntegerVariables = (reg, var1, comparisonOperator, var2) ->
            "\t%" + reg + " = icmp " + comparisonOperator.getName() + " i32 %" + var1 + ", %" + var2 + "\n";

    static final FourFunction<Integer, Integer, ComparisonOperatorsDouble, Integer, String> compareDoubleVariables = (reg, var1, comparisonOperator, var2) ->
            "\t%" + reg + " = fcmp " + comparisonOperator.getName() + " double %" + var1 + ", %" + var2 + "\n";

    static final FourFunction<Integer, String, ComparisonOperatorsInteger, String, String> compareIntegerNumbers = (reg, nb1, comparisonOperator, nb2) ->
            "\t%" + reg + " = icmp " + comparisonOperator.getName() + " i32 " + nb1 + ", " + nb2 + "\n";

    static final FourFunction<Integer, String, ComparisonOperatorsDouble, String, String> compareDoubleNumbers = (reg, nb1, comparisonOperator, nb2) ->
            "\t%" + reg + " = fcmp " + comparisonOperator.getName() + " double " + nb1 + ", " + nb2 + "\n";

    static final FourFunction<Integer, String, ComparisonOperatorsInteger, Integer, String> compareIntegerNumberAndIntegerVariable = (reg, nb, comparisonOperator, var) ->
            "\t%" + reg + " = icmp " + comparisonOperator.getName() + " i32 " + nb + ", %" + var + "\n";

    static final FourFunction<Integer, Integer, ComparisonOperatorsInteger, String, String> compareIntegerVariableAndIntegerNumber = (reg, var, comparisonOperator, nb) ->
            "\t%" + reg + " = icmp " + comparisonOperator.getName() + " i32 %" + var + ", " + nb + "\n";

    static final FourFunction<Integer, String, ComparisonOperatorsDouble, Integer, String> compareDoubleNumberAndDoubleVariable = (reg, nb, comparisonOperator, var) ->
            "\t%" + reg + " = fcmp " + comparisonOperator.getName() + " double " + nb + ", %" + var + "\n";

    static final FourFunction<Integer, Integer, ComparisonOperatorsDouble, String, String> compareDoubleVariableAndDoubleNumber = (reg, var, comparisonOperator, nb) ->
            "\t%" + reg + " = fcmp " + comparisonOperator.getName() + " double %" + var + ", " + nb + "\n";

    static final BiFunction<Integer, Integer, String> ifStart = (ifIndex, breakIndex) ->
            "\tbr i1 %" + ifIndex + ", label %true-" + breakIndex + ", label %false-" + breakIndex + "\n"
                    + "\ttrue-" + breakIndex + ":\n";

    static final Function<Integer, String> ifEnd = (breakIndex) ->
            "\tbr label %false-" + breakIndex + "\n"
                    + "\tfalse-" + breakIndex + ":\n";

}
