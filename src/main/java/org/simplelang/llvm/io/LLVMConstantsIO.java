package org.simplelang.llvm.io;

import java.util.function.BiFunction;
import java.util.function.Function;

abstract class LLVMConstantsIO {

    static final BiFunction<Integer, String, String> printlnInteger = (reg, number) ->
            "\t%" + reg + " = call i32 (i8*, ...) "
                    + "@printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @strpi, i32 0, i32 0), i32 " + number + ")\n";

    static final BiFunction<Integer, String, String> printlnDouble = (reg, number) ->
            "\t%" + reg + " = call i32 (i8*, ...) "
                    + "@printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @strpd, i32 0, i32 0), double " + number + ")\n";

    static final BiFunction<Integer, Integer, String> printlnIntegerFromVariable = (reg, variableReg) ->
            "\t%" + reg + " = call i32 (i8*, ...) "
                    + "@printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @strpi, i32 0, i32 0), i32 %" + variableReg + ")\n";

    static final BiFunction<Integer, Integer, String> printlnDoubleFromVariable = (reg, variableReg) ->
            "\t%" + reg + " = call i32 (i8*, ...) "
                    + "@printf(i8* getelementptr inbounds ([4 x i8], [4 x i8]* @strpd, i32 0, i32 0), double %" + variableReg + ")\n";

    static final Function<String, String> scanfHeader = (methodName) ->
            "@str-" + methodName + " = private unnamed_addr constant [3 x i8] c\"%s\\00\"\n";

}
