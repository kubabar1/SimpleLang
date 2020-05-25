package org.simplelang.llvm.casting;

import java.util.function.BiFunction;

abstract class LLVMConstantsCasting {

    static final BiFunction<Integer, String, String> integerToDouble = (reg, id) ->
            "\t%" + reg + " = sitofp i32 " + id + " to double\n";

    static final BiFunction<Integer, String, String> doubleToInteger = (reg, id) ->
            "\t%" + reg + " = fptosi double " + id + " to i32\n";

}
