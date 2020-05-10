package org.simplelang.llvm.string;

import org.simplelang.utils.FunctionalInterfaceHelper.ThreeFunction;
import org.simplelang.utils.FunctionalInterfaceHelper.FourFunction;

import java.util.function.Function;

abstract class LLVMConstantsString {

    static final ThreeFunction<String, String, Integer, String> printlnStringHeader = (methodName, stringValue, textLength) ->
            "@str-" + methodName + " = constant [" + textLength + " x i8] c\"" + stringValue + "\\0A\\00\"\n";

    static final ThreeFunction<Integer, String, Integer, String> printlnString = (reg, methodName, textLength) ->
            "\t%" + reg + " = call i32 (i8*, ...) "
                    + "@printf(i8* getelementptr inbounds ([" + textLength + " x i8], [" + textLength + " x i8]* "
                    + "@str-" + methodName + ", i64 0, i64 0))\n";

    static final Function<String, String> printlnStringByVariableHeader = (methodName) ->
            "@str-" + methodName + " = private unnamed_addr constant [4 x i8] c\"%s\\0A\\00\"\n";

    static final ThreeFunction<Integer, String, Integer, String> printlnStringByVariable = (reg, methodName, variableReg) ->
            "\t%" + reg + " = call i32 (i8*, ...) "
                    + "@printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* "
                    + "@str-" + methodName + ", i64 0, i64 0), i8* %" + variableReg + ")\n";

    static final ThreeFunction<String, String, Integer, String> declareAssignStringHeader = (methodName, stringValue, stringLength) ->
            "@str-" + methodName + " = private unnamed_addr constant [" + stringLength + " x i8] c\"" + stringValue + "\\00\"\n";

    static final FourFunction<Integer, Integer, Integer, String, String> strcpyString = (reg, stringToCopyId, stringToCopyLength, methodName) ->
            "\t%" + reg + " = call i8* @strcpy(i8* %" + stringToCopyId + ", i8* getelementptr inbounds (["
                    + stringToCopyLength + " x i8], [" + stringToCopyLength + " x i8]* "
                    + "@str-" + methodName + ", i64 0, i64 0))\n";


}
