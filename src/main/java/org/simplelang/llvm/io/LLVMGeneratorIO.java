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
import static org.simplelang.llvm.LLVMGeneratorBase.mainText;
import static org.simplelang.llvm.LLVMGeneratorBase.headerText;
import static org.simplelang.llvm.LLVMGeneratorBase.reg;
import static org.simplelang.llvm.LLVMGeneratorBase.methodReg;
import static org.simplelang.llvm.LLVMGeneratorBase.loadInteger;
import static org.simplelang.llvm.LLVMGeneratorBase.loadDouble;
import static org.simplelang.llvm.io.LLVMConstantsIO.printlnInteger;
import static org.simplelang.llvm.io.LLVMConstantsIO.printlnDouble;
import static org.simplelang.llvm.io.LLVMConstantsIO.printlnIntegerFromVariable;
import static org.simplelang.llvm.io.LLVMConstantsIO.printlnDoubleFromVariable;
import static org.simplelang.llvm.io.LLVMConstantsIO.scanfHeader;

public interface LLVMGeneratorIO {

    static void printlnInteger(String number) {
        mainText += printlnInteger.apply(reg, number);
        reg++;
    }

    static void printlnDouble(String number) {
        mainText += printlnDouble.apply(reg, number);
        reg++;
    }

    static void printlnIntegerFromVariable(String id) {
        loadInteger(id);
        mainText += printlnIntegerFromVariable.apply(reg, reg - 1);
        reg++;
    }

    static void printfDoubleFromVariable(String id) {
        loadDouble(id);
        mainText += printlnDoubleFromVariable.apply(reg, reg - 1);
        reg++;
    }

    static void scanf(String variableName) {
        String methodName = methodNamePrefix + methodReg;
        headerText += scanfHeader.apply(methodName);
        methodReg++;

        // char *string;
        mainText += declareI32.apply(String.valueOf(reg));
        reg++;
        mainText += declareByte.apply(variableName);
        mainText += assignI32.apply(String.valueOf(reg - 1), "0");

        // string = (char *) malloc((6+1)*sizeof(char));
        mainText += malloc.apply(reg, readBufferSize);
        mainText += assignByte.apply(String.valueOf(reg), variableName);
        reg++;

        // scanf("%s", string);
        mainText += loadByte.apply(reg, variableName);
        reg++;
        mainText += scanf.apply(reg, methodName, reg - 1);
        reg++;
    }
}
