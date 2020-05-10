package org.simplelang.llvm;

import static org.simplelang.llvm.LLVMConstantsBase.mainDef;
import static org.simplelang.llvm.LLVMConstantsBase.printFloatNewLine;
import static org.simplelang.llvm.LLVMConstantsBase.declarePrintf;
import static org.simplelang.llvm.LLVMConstantsBase.declareScanf;
import static org.simplelang.llvm.LLVMConstantsBase.declareMalloc;
import static org.simplelang.llvm.LLVMConstantsBase.declareStrcpy;
import static org.simplelang.llvm.LLVMConstantsBase.declareRealloc;
import static org.simplelang.llvm.LLVMConstantsBase.declareStrlen;
import static org.simplelang.llvm.LLVMConstantsBase.declareMemcpy;
import static org.simplelang.llvm.LLVMConstantsBase.printIntegerNewLine;
import static org.simplelang.llvm.LLVMConstantsBase.mainReturn;
import static org.simplelang.llvm.LLVMConstantsBase.declareI32;
import static org.simplelang.llvm.LLVMConstantsBase.declareDouble;
import static org.simplelang.llvm.LLVMConstantsBase.assignI32;
import static org.simplelang.llvm.LLVMConstantsBase.assignDouble;
import static org.simplelang.llvm.LLVMConstantsBase.loadInteger;
import static org.simplelang.llvm.LLVMConstantsBase.loadDouble;

public abstract class LLVMGeneratorBase {

    public static String headerText = "";

    public static String mainText = "";

    public static int reg = 1;

    public static int methodReg = 1;

    static {
        headerText += declarePrintf;
        headerText += declareScanf;
        headerText += declareMalloc;
        headerText += declareStrcpy;
        headerText += declareRealloc;
        headerText += declareStrlen;
        headerText += declareMemcpy;
        headerText += printIntegerNewLine;
        headerText += printFloatNewLine;
    }

    public static void declareInteger(String id) {
        mainText += declareI32.apply(id);
    }

    public static void declareDouble(String id) {
        mainText += declareDouble.apply(id);
    }

    public static void assignInteger(String id, String value) {
        mainText += assignI32.apply(id, value);
    }

    public static void assignDouble(String id, String value) {
        mainText += assignDouble.apply(id, value);
    }

    public static void loadInteger(String id) {
        mainText += loadInteger.apply(reg, id);
        reg++;
    }

    public static void loadDouble(String id) {
        mainText += loadDouble.apply(reg, id);
        reg++;
    }

    public static String generate() {
        return headerText +
                mainDef +
                mainText +
                mainReturn;
    }

}
