package org.simplelang.llvm.expression;

import org.simplelang.utils.FunctionalInterfaceHelper.ThreeFunction;

abstract class LLVMConstantsExpression {

    static final ThreeFunction<Integer, String, String, String> addInteger = (reg, val1, val2) ->
            "\t%" + reg + " = add i32 " + val1 + ", " + val2 + "\n";

    static final ThreeFunction<Integer, String, String, String> addDouble = (reg, val1, val2) ->
            "\t%" + reg + " = fadd double " + val1 + ", " + val2 + "\n";

    static final ThreeFunction<Integer, String, String, String> subInteger = (reg, val1, val2) ->
            "\t%" + reg + " = sub i32 " + val2 + ", " + val1 + "\n";

    static final ThreeFunction<Integer, String, String, String> subDouble = (reg, val1, val2) ->
            "\t%" + reg + " = fsub double " + val2 + ", " + val1 + "\n";

    static final ThreeFunction<Integer, String, String, String> multInteger = (reg, val1, val2) ->
            "\t%" + reg + " = mul i32 " + val1 + ", " + val2 + "\n";

    static final ThreeFunction<Integer, String, String, String> multDouble = (reg, val1, val2) ->
            "\t%" + reg + " = fmul double " + val1 + ", " + val2 + "\n";

    static final ThreeFunction<Integer, String, String, String> divInteger = (reg, val1, val2) ->
            "\t%" + reg + " = sdiv i32 " + val2 + ", " + val1 + "\n";

    static final ThreeFunction<Integer, String, String, String> divDouble = (reg, val1, val2) ->
            "\t%" + reg + " = fdiv double " + val2 + ", " + val1 + "\n";

}
