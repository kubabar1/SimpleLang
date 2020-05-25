package org.simplelang.llvm.array;

import org.simplelang.listener.containers.model.VariableType;
import static org.simplelang.llvm.LLVMGeneratorBase.tmp;
import static org.simplelang.llvm.LLVMGeneratorBase.buffer;
import static org.simplelang.llvm.LLVMGeneratorBase.headerText;

public class LLVMGeneratorArray {

    public static void declareArray(String variableName, int arraySize) {
        buffer += "\t%" + tmp + " = alloca i32\n";
        tmp++;
        buffer += "\t%" + variableName + " = alloca [" + arraySize + " x i32], align 16\n";
        buffer += "\tstore i32 0, i32* %" + (tmp - 1) + "\n";
    }

    public static void assignArray(String variableName, String[] array, VariableType variableType) {
        String functName = "@__const.main.a-" + tmp;
        if (variableType.equals(VariableType.INT)) {
            headerText += functName + " = private unnamed_addr constant [" + array.length + " x i32] " + getIntegerArrayAsignString(array) + ", align 16\n";
            buffer += "\t%" + tmp + "= bitcast [" + array.length + " x i32]* %" + variableName + " to i8*\n";
            tmp++;
            buffer += "\tcall void @llvm.memcpy.p0i8.p0i8.i64(i8* align 16 %" + (tmp - 1) + ", i8* align 16 bitcast ([" + array.length + " x i32]* " + functName + " to i8*), i64 16, i1 false)\n";
        } else if (variableType.equals(VariableType.DOUBLE)) {
            // TODO
        }
    }

    public static String getIntegerArrayAsignString(String[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");

        for (String element : array) {
            if (stringBuilder.length() > 1) {
                stringBuilder.append(",");
            }
            stringBuilder.append(" i32 ").append(element);
        }

        stringBuilder.append("]");

        return stringBuilder.toString();
    }

}
