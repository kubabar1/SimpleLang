package org.simplelang.llvm.string;

import static org.simplelang.llvm.LLVMConstantsBase.*;
import static org.simplelang.llvm.LLVMGeneratorBase.reg;
import static org.simplelang.llvm.LLVMGeneratorBase.methodReg;
import static org.simplelang.llvm.LLVMGeneratorBase.headerText;
import static org.simplelang.llvm.LLVMGeneratorBase.mainText;
import static org.simplelang.llvm.string.LLVMConstantsString.printlnStringHeader;
import static org.simplelang.llvm.string.LLVMConstantsString.printlnString;
import static org.simplelang.llvm.string.LLVMConstantsString.printlnStringByVariableHeader;
import static org.simplelang.llvm.string.LLVMConstantsString.printlnStringByVariable;
import static org.simplelang.llvm.string.LLVMConstantsString.declareAssignStringHeader;
import static org.simplelang.llvm.string.LLVMConstantsString.strcpyString;

public class LLVMGeneratorString {

    public static void printlnString(String string) {
        int textLength = string.length() + 2;
        String methodName = methodNamePrefix + methodReg;
        headerText += printlnStringHeader.apply(methodName, string, textLength);
        methodReg++;
        mainText += printlnString.apply(reg, methodName, textLength);
        reg++;
    }

    public static void printlnStringByVariableName(String variableName) {
        String methodName = methodNamePrefix + methodReg;
        headerText += printlnStringByVariableHeader.apply(methodName);
        methodReg++;
        mainText += loadByte.apply(reg, variableName);
        reg++;
        mainText += printlnStringByVariable.apply(reg, methodName, reg - 1);
        reg++;
    }


    public static void declareAndAssignString(String variableName, String stringValue) {
        String methodName = methodNamePrefix + methodReg;
        headerText += declareAssignStringHeader.apply(methodName, stringValue, stringValue.length() + 1);
        methodReg++;

        // char *string;
        mainText += declareI32.apply(String.valueOf(reg));
        reg++;
        mainText += declareByte.apply(variableName);
        mainText += assignI32.apply(String.valueOf(reg - 1), "0");

        // string = (char *) malloc((6+1)*sizeof(char));
        mainText += malloc.apply(reg, stringValue.length() + 1);
        mainText += assignByte.apply(String.valueOf(reg), variableName);
        reg++;

        // strcpy(string, "string");
        mainText += loadByte.apply(reg, variableName);
        reg++;
        mainText += strcpyString.apply(reg, reg - 1, stringValue.length() + 1, methodName);
        reg++;
    }


    public static void reasignString(String variableName, String stringValue) {
        String methodName = methodNamePrefix + methodReg;
        headerText += declareAssignStringHeader.apply(methodName, stringValue, stringValue.length() + 1);
        methodReg++;

        // if(strlen(string)<(7+1))
        mainText += "\t%" + reg + " = load i8*, i8** %" + variableName + "\n";
        reg++;
        mainText += "\t%" + reg + " = call i64 @strlen(i8* %" + (reg - 1) + ")\n";
        reg++;
        mainText += "\t%" + reg + " = icmp ult i64 %" + (reg - 1) + ", " + (stringValue.length() + 1) + "\n";
        reg++;
        mainText += "\tbr i1 %" + (reg - 1) + ", label %" + reg + ", label %" + (reg + 3) + "\n";

        int pred = reg;

        // string3 = (char *) realloc(string3, (6+1)*sizeof(char));
        mainText += "\t" + pred + ":                                                ; preds = %0\n";
        reg++;
        mainText += "\t\t%" + reg + " = load i8*, i8** %" + variableName + "\n";
        reg++;
        mainText += "\t\t%" + reg + " = call i8* @realloc(i8* %" + (reg - 1) + ", i64 " + (stringValue.length() + 1) + ")\n";
        reg++;
        mainText += "\t\tstore i8* %" + (reg - 1) + ", i8** %" + variableName + "\n";
        mainText += "\t\tbr label %" + reg + "\n";

        // strcpy(string, "string2");
        mainText += "\t" + reg + ":                                               ; preds = %" + pred + ", %0\n";
        reg++;
        mainText += "\t\t%" + reg + " = load i8*, i8** %" + variableName + "\n";
        reg++;
        mainText += "\t\t%" + reg + " = call i8* @strcpy(i8* %" + (reg - 1) + ", i8* getelementptr inbounds ([" + (stringValue.length() + 1) + " x i8], [" + (stringValue.length() + 1) + " x i8]* " + "@str-" + methodName + ", i64 0, i64 0))\n";
        reg++;
    }

}
