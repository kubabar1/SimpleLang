package org.simplelang.llvm.casting;

import org.simplelang.llvm.LLVMGeneratorBase;

import java.util.function.Function;

abstract class LLVMConstantsCasting {

    static final Function<String, String> integerToDouble = (id) ->
            "\t%" + LLVMGeneratorBase.reg + " = sitofp i32 " + id + " to double\n";

    static final Function<String, String> doubleToInteger = (id) ->
            "\t%" + LLVMGeneratorBase.reg + " = fptosi double " + id + " to i32\n";

}
