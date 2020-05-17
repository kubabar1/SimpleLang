package org.simplelang.llvm.if_stmt;

import static org.simplelang.llvm.LLVMGeneratorBase.brIf;
import static org.simplelang.llvm.LLVMGeneratorBase.reg;
import static org.simplelang.llvm.LLVMGeneratorBase.mainText;
import static org.simplelang.llvm.if_stmt.LLVMConstantsIf.ifStart;
import static org.simplelang.llvm.if_stmt.LLVMConstantsIf.ifEnd;

public class LLVMGeneratorIf {

    public static int ifStart() {
        brIf++;
        mainText += ifStart.apply(reg-1, brIf);
        return brIf;
    }

    public static void ifEnd(int breakIndex) {
        mainText += ifEnd.apply(breakIndex);
    }

}
