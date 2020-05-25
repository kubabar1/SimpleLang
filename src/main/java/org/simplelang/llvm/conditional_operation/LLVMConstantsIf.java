package org.simplelang.llvm.conditional_operation;

import java.util.function.BiFunction;
import java.util.function.Function;

abstract class LLVMConstantsIf {

    static final BiFunction<Integer, Integer, String> ifStart = (ifIndex, breakIndex) ->
            "\tbr i1 %" + ifIndex + ", label %if-true-" + breakIndex + ", label %if-false-" + breakIndex + "\n"
                    + "\tif-true-" + breakIndex + ":\n";

    static final Function<Integer, String> ifEnd = (breakIndex) ->
            "\tbr label %if-false-" + breakIndex + "\n"
                    + "\tif-false-" + breakIndex + ":\n";

}
