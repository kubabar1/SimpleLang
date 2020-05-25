package org.simplelang.llvm.conditional_operation;

import static org.simplelang.llvm.LLVMGeneratorBase.brIf;
import static org.simplelang.llvm.LLVMGeneratorBase.tmp;
import static org.simplelang.llvm.LLVMGeneratorBase.buffer;
import static org.simplelang.llvm.conditional_operation.LLVMConstantsIf.ifStart;
import static org.simplelang.llvm.conditional_operation.LLVMConstantsIf.ifEnd;

public class LLVMGeneratorIf {

    public static int ifStart() {
        brIf++;
        int conditionResultIndex = tmp - 1;
        buffer += ifStart.apply(conditionResultIndex, brIf);
        return brIf;
    }

    public static void ifEnd(int breakIndex) {
        buffer += ifEnd.apply(breakIndex);
    }

}
