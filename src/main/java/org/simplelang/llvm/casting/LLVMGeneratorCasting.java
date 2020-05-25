package org.simplelang.llvm.casting;

import static org.simplelang.llvm.casting.LLVMConstantsCasting.integerToDouble;
import static org.simplelang.llvm.casting.LLVMConstantsCasting.doubleToInteger;
import static org.simplelang.llvm.LLVMGeneratorBase.buffer;
import static org.simplelang.llvm.LLVMGeneratorBase.tmp;

public class LLVMGeneratorCasting {

    public static void integerToDouble(String id) {
        buffer += integerToDouble.apply(tmp, id);
        tmp++;
    }

    public static void doubleToInteger(String id) {
        buffer += doubleToInteger.apply(tmp, id);
        tmp++;
    }

}
