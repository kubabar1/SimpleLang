package org.simplelang.llvm;

import org.simplelang.utils.FunctionalInterfaceHelper.ThreeFunction;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class LLVMConstantsBase {

    public static int readBufferSize = 1024;

    public static final String methodNamePrefix = "method";

    public static final BiFunction<Integer, String, String> loadInteger = (reg, id) ->
            "\t%" + reg + " = load i32, i32* " + id + "\n";

    public static final BiFunction<Integer, String, String> loadDouble = (reg, id) ->
            "\t%" + reg + " = load double, double* " + id + "\n";

    public static final BiFunction<Integer, String, String> loadByte = (reg, name) ->
            "\t%" + reg + " = load i8*, i8** " + name + "\n";

    public static final Function<String, String> declareI32Global = (id) -> "@" + id + " = global i32 0\n";

    public static final Function<String, String> declareI32 = (id) -> "\t%" + id + " = alloca i32\n";

    public static final Function<String, String> declareDoubleGlobal = (id) -> "@" + id + " = global double 0\n";

    public static final Function<String, String> declareDouble = (id) -> "\t%" + id + " = alloca double\n";

    public static final Function<String, String> declareByte = (id) -> "\t%" + id + " = alloca i8*\n";

    public static final BiFunction<String, String, String> assignI32 = (id, value) ->
            "\tstore i32 " + value + ", i32* " + id + "\n";

    public static final BiFunction<String, String, String> assignDouble = (id, value) ->
            "\tstore double " + value + ", double* " + id + "\n";

    public static final BiFunction<String, String, String> assignByte = (id, variableName) ->
            "\tstore i8* %" + id + ", i8** " + variableName + "\n";

    public static final BiFunction<Integer, Integer, String> malloc = (reg, bufferSize) ->
            "\t%" + reg + " = call noalias i8* @malloc(i64 " + bufferSize + ")\n";

    public static final ThreeFunction<Integer, String, Integer, String> scanf = (reg, methodName, byteId) ->
            "\t%" + reg + " = call i32 (i8*, ...) "
                    + "@__isoc99_scanf(i8* getelementptr inbounds ([3 x i8], [3 x i8]* "
                    + "@str-" + methodName + ", i64 0, i64 0), i8* %" + byteId + ")\n";


    static final String mainDef = "\ndefine i32 @main() nounwind{\n";

    static final String mainReturn = "\tret i32 0 \n}\n";

    static final String declarePrintf = "declare i32 @printf(i8*, ...)\n";

    static final String declareScanf = "declare i32 @__isoc99_scanf(i8*, ...)\n";

    static final String declareMalloc = "declare noalias i8* @malloc(i64)\n";

    static final String declareRealloc = "declare i8* @realloc(i8*, i64)\n";

    static final String declareStrcpy = "declare i8* @strcpy(i8*, i8*)\n";

    static final String declareStrlen = "declare i64 @strlen(i8*)\n";

    static final String declareMemcpy = "declare void " +
            "@llvm.memcpy.p0i8.p0i8.i64(i8* nocapture writeonly, i8* nocapture readonly, i64, i1 immarg)\n";

    static final String printIntegerNewLine = "@strpi = constant [4 x i8] c\"%d\\0A\\00\"\n";

    static final String printFloatNewLine = "@strpd = constant [4 x i8] c\"%f\\0A\\00\"\n";

}
