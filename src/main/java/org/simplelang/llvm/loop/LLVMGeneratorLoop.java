package org.simplelang.llvm.loop;

import static org.simplelang.llvm.LLVMGeneratorBase.buffer;
import static org.simplelang.llvm.LLVMGeneratorBase.tmp;
import static org.simplelang.llvm.LLVMGeneratorBase.brLoop;
import static org.simplelang.llvm.loop.LLVMConstantsLoop.loopBlockStart;
import static org.simplelang.llvm.loop.LLVMConstantsLoop.loopBlockEnd;
import static org.simplelang.llvm.loop.LLVMConstantsLoop.loopStart;

public class LLVMGeneratorLoop {

    public static int startLoop() {
        brLoop++;
        buffer += loopStart.apply(brLoop);
        return brLoop;
    }

    public static void startLoopBlock() {
        int conditionResultIndex = tmp - 1;
        buffer += loopBlockStart.apply(conditionResultIndex, brLoop);
    }

    public static void endLoopBlock(int brLoop) {
        buffer += loopBlockEnd.apply(brLoop);
    }

}
