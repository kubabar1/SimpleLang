package org.simplelang.llvm;

final class LLVMConstants {

    static final String declarePrintf = "declare i32 @printf(i8*, ...)\n";

    static final String declareScanf = "declare i32 @__isoc99_scanf(i8*, ...)\n";

    static final String declareMalloc = "declare noalias i8* @malloc(i64)\n";

    static final String declareRealloc = "declare i8* @realloc(i8*, i64)\n";

    static final String declareStrcpy = "declare i8* @strcpy(i8*, i8*)\n";

    static final String declareStrlen = "declare i64 @strlen(i8*)\n";

    static final String printIntegerNewLine = "@strpi = constant [4 x i8] c\"%d\\0A\\00\"\n";

    static final String printFloatNewLine = "@strpd = constant [4 x i8] c\"%f\\0A\\00\"\n";

}
