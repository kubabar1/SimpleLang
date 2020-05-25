package org.simplelang.llvm.io;

import static org.simplelang.llvm.LLVMConstantsBase.methodNamePrefix;
import static org.simplelang.llvm.LLVMConstantsBase.declareI32;
import static org.simplelang.llvm.LLVMConstantsBase.declareByte;
import static org.simplelang.llvm.LLVMConstantsBase.assignByte;
import static org.simplelang.llvm.LLVMConstantsBase.loadByte;
import static org.simplelang.llvm.LLVMConstantsBase.scanf;
import static org.simplelang.llvm.LLVMConstantsBase.malloc;
import static org.simplelang.llvm.LLVMConstantsBase.readBufferSize;
import static org.simplelang.llvm.LLVMConstantsBase.assignI32;
import static org.simplelang.llvm.LLVMGeneratorBase.tmp;
import static org.simplelang.llvm.LLVMGeneratorBase.buffer;
import static org.simplelang.llvm.LLVMGeneratorBase.methodReg;
import static org.simplelang.llvm.LLVMGeneratorBase.loadInteger;
import static org.simplelang.llvm.LLVMGeneratorBase.loadDouble;
import static org.simplelang.llvm.io.LLVMConstantsIO.printlnInteger;
import static org.simplelang.llvm.io.LLVMConstantsIO.printlnDouble;
import static org.simplelang.llvm.io.LLVMConstantsIO.printlnIntegerFromVariable;
import static org.simplelang.llvm.io.LLVMConstantsIO.printlnDoubleFromVariable;
import static org.simplelang.llvm.io.LLVMConstantsIO.scanfHeader;

public class LLVMGeneratorIO {

    public static void printlnInteger(String number) {
        buffer += printlnInteger.apply(tmp, number);
        tmp++;
    }

    public static void printlnDouble(String number) {
        buffer += printlnDouble.apply(tmp, number);
        tmp++;
    }

    public static void printlnIntegerFromVariable(String id) {
        buffer += printlnIntegerFromVariable.apply(tmp, tmp - 1);
        tmp++;
    }

    public static void printfDoubleFromVariable(String id) {
        buffer += printlnDoubleFromVariable.apply(tmp, tmp - 1);
        tmp++;
    }

    public static void scanf(String variableName) { // TODO FIX
        String methodName = methodNamePrefix + methodReg;
        buffer += scanfHeader.apply(methodName);
        methodReg++;

        // char *string;
        buffer += declareI32.apply(String.valueOf(tmp));
        tmp++;
        buffer += declareByte.apply(variableName);
        buffer += assignI32.apply(String.valueOf(tmp - 1), "0");

        // string = (char *) malloc((6+1)*sizeof(char));
        buffer += malloc.apply(tmp, readBufferSize);
        buffer += assignByte.apply(String.valueOf(tmp), variableName);
        tmp++;

        // scanf("%s", string);
        buffer += loadByte.apply(tmp, variableName);
        tmp++;
        buffer += scanf.apply(tmp, methodName, tmp - 1);
        tmp++;
    }
}
