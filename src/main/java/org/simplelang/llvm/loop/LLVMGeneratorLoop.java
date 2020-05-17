package org.simplelang.llvm.loop;

import static org.simplelang.llvm.LLVMGeneratorBase.reg;
import static org.simplelang.llvm.LLVMGeneratorBase.brLoop;
import static org.simplelang.llvm.LLVMGeneratorBase.mainText;
import static org.simplelang.llvm.loop.LLVMConstantsLoop.loopBlockStart;
import static org.simplelang.llvm.loop.LLVMConstantsLoop.loopBlockEnd;
import static org.simplelang.llvm.loop.LLVMConstantsLoop.loopStart;

public class LLVMGeneratorLoop {

    public static int startLoop() {
        brLoop++;
        mainText += loopStart.apply(brLoop);
        return brLoop;
    }

    public static void startLoopBlock() {
        mainText += loopBlockStart.apply(reg - 1, brLoop);
    }

    public static void endLoopBlock(int brLoop) {
        mainText += loopBlockEnd.apply(brLoop);
    }
}
