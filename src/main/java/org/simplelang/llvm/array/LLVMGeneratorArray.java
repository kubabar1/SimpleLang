package org.simplelang.llvm.array;

import org.simplelang.listener.container.base.VariableType;
import org.simplelang.llvm.LLVMGeneratorBase;

public class LLVMGeneratorArray {

    public static void declareArray(String variableName, int arraySize) {
        LLVMGeneratorBase.mainText += "\t%" + LLVMGeneratorBase.reg + " = alloca i32\n";
        LLVMGeneratorBase.reg++;
        LLVMGeneratorBase.mainText += "\t%" + variableName + " = alloca [" + arraySize + " x i32], align 16\n";
        LLVMGeneratorBase.mainText += "\tstore i32 0, i32* %" + (LLVMGeneratorBase.reg - 1) + "\n";
    }

    public static void assignArray(String variableName, String[] array, VariableType variableType) {
        String functName = "@__const.main.a-" + LLVMGeneratorBase.reg;
        if (variableType.equals(VariableType.INT)) {
            LLVMGeneratorBase.headerText += functName + " = private unnamed_addr constant [" + array.length + " x i32] " + getIntegerArrayAsignString(array) + ", align 16\n";
            LLVMGeneratorBase.mainText += "\t%" + LLVMGeneratorBase.reg + "= bitcast [" + array.length + " x i32]* %" + variableName + " to i8*\n";
            LLVMGeneratorBase.reg++;
            LLVMGeneratorBase.mainText += "\tcall void @llvm.memcpy.p0i8.p0i8.i64(i8* align 16 %" + (LLVMGeneratorBase.reg - 1) + ", i8* align 16 bitcast ([" + array.length + " x i32]* " + functName + " to i8*), i64 16, i1 false)\n";
        } else if (variableType.equals(VariableType.FLOAT)) {
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
