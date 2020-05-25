package org.simplelang.llvm.function;

import static org.simplelang.llvm.LLVMGeneratorBase.*;
import static org.simplelang.llvm.function.LLVMConstantsFunction.*;

public class LLVMGeneratorFunction {

    public static void functionDefinitionStart(String functionName) {
        mainText += buffer;
        mainTmp = tmp;
        buffer = functionDefStart.apply(functionName);
        tmp = 1;
    }

    public static void functionDefinitionEnd() {
        buffer += functionDefEnd;
        headerText += buffer;
        buffer = "";
        tmp = mainTmp;
    }

    public static void callFunction(String functionName) {
        mainText += functionCall.apply(functionName);
    }

}
