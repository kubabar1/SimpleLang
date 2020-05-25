package org.simplelang.llvm.function;

import java.util.function.Function;

abstract class LLVMConstantsFunction {

    static final Function<String, String> functionDefStart = (functionName) ->
            "define void @" + functionName + "() {\n";

    static final String functionDefEnd = "\tret void\n"
            + "}\n";

    static final Function<String, String> functionCall = (functionName) ->
            "\tcall void @" + functionName + "()\n";

}
