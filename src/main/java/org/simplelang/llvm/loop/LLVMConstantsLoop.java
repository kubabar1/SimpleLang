package org.simplelang.llvm.loop;

import java.util.function.BiFunction;
import java.util.function.Function;

abstract class LLVMConstantsLoop {

    static final Function<Integer, String> loopStart = (loopStartIndex) ->
            "\tbr label %loop-start-" + loopStartIndex + "\n"
                    + "\tloop-start-" + loopStartIndex + ":\n";

    static final BiFunction<Integer, Integer, String> loopBlockStart = (conditionResIndex, loopBreakIndex) ->
            "\tbr i1 %" + conditionResIndex + ", label %loop-block-start-" + loopBreakIndex + ", label %loop-block-end-" + loopBreakIndex + "\n"
                    + "\tloop-block-start-" + loopBreakIndex + ":\n";

    static final Function<Integer, String> loopBlockEnd = (brLoop) ->
            "\tbr label %loop-start-" + brLoop + "\n"
                    + "\tloop-block-end-" + brLoop + ":\n";

}
