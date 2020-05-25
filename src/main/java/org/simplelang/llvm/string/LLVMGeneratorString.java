package org.simplelang.llvm.string;

import static org.simplelang.llvm.LLVMConstantsBase.loadByte;
import static org.simplelang.llvm.LLVMConstantsBase.declareI32;
import static org.simplelang.llvm.LLVMConstantsBase.declareByte;
import static org.simplelang.llvm.LLVMConstantsBase.assignI32;
import static org.simplelang.llvm.LLVMConstantsBase.assignByte;
import static org.simplelang.llvm.LLVMConstantsBase.malloc;
import static org.simplelang.llvm.LLVMConstantsBase.methodNamePrefix;
import static org.simplelang.llvm.LLVMGeneratorBase.methodReg;
import static org.simplelang.llvm.LLVMGeneratorBase.headerText;
import static org.simplelang.llvm.LLVMGeneratorBase.buffer;
import static org.simplelang.llvm.LLVMGeneratorBase.tmp;
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
        buffer += printlnString.apply(tmp, methodName, textLength);
        tmp++;
    }

    public static void printlnStringByVariableName(String variableName) {
        String methodName = methodNamePrefix + methodReg;
        headerText += printlnStringByVariableHeader.apply(methodName);
        methodReg++;
        buffer += loadByte.apply(tmp, variableName);
        tmp++;
        int variableId = tmp - 1;
        buffer += printlnStringByVariable.apply(tmp, methodName, variableId);
        tmp++;
    }


    public static void declareAndAssignString(String variableName, String stringValue) {
        String methodName = methodNamePrefix + methodReg;
        headerText += declareAssignStringHeader.apply(methodName, stringValue, stringValue.length() + 1);
        methodReg++;

        createPointerForString(variableName);
        allocMemoryForString(variableName, stringValue.length() + 1);
        assignStringValueToVariable(variableName, methodName, stringValue.length() + 1);
    }

    /*public static void reAssignString(String variableName, String stringValue) {
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

        LLVMGeneratorComparison.compareIntegerNumberAndVariable(, ComparisonOperatorsInteger.LESS_THAN, variableName);

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
    }*/

    /**
     * Perform operation:
     * char *string;
     *
     * @param variableName name of variable
     */
    private static void createPointerForString(String variableName) {
        buffer += declareI32.apply(String.valueOf(tmp));
        tmp++;
        buffer += declareByte.apply(variableName);
        buffer += assignI32.apply(String.valueOf(tmp - 1), "0");
    }

    /**
     * Perform operation:
     * string = (char *) malloc((...)*sizeof(char));
     *
     * @param variableName      name of the variable
     * @param stringValueLength length of the string value
     */
    private static void allocMemoryForString(String variableName, int stringValueLength) {
        buffer += malloc.apply(tmp, stringValueLength);
        buffer += assignByte.apply(String.valueOf(tmp), variableName);
        tmp++;
    }

    /**
     * Perform operation:
     * strcpy(string, "string");
     *
     * @param variableName      name of the variable
     * @param methodName        name of the method used in strcpy
     * @param stringValueLength length of the string value
     */
    private static void assignStringValueToVariable(String variableName, String methodName, int stringValueLength) {
        buffer += loadByte.apply(tmp, variableName);
        tmp++;
        buffer += strcpyString.apply(tmp, tmp - 1, stringValueLength, methodName);
        tmp++;
    }

}
