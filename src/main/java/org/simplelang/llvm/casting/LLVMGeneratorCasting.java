package org.simplelang.llvm.casting;

import static org.simplelang.llvm.casting.LLVMConstantsCasting.integerToDouble;
import static org.simplelang.llvm.casting.LLVMConstantsCasting.doubleToInteger;
import static org.simplelang.llvm.LLVMGeneratorBase.mainText;
import static org.simplelang.llvm.LLVMGeneratorBase.reg;

public class LLVMGeneratorCasting {

    public static void integerToDouble(String id) {
        mainText += integerToDouble.apply(id);
        reg++;
    }

    public static void doubleToInteger(String id) {
        mainText += doubleToInteger.apply(id);
        reg++;
    }

}
