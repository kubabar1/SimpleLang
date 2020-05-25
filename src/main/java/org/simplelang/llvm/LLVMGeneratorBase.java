package org.simplelang.llvm;

import static org.simplelang.llvm.LLVMConstantsBase.*;

public abstract class LLVMGeneratorBase {

    public static String headerText = "";

    public static String mainText = "";

    public static String buffer = "";


    public static int mainTmp = 1;

    public static int tmp = 1;


    public static int brIf = 0;

    public static int brLoop = 0;

    public static int methodReg = 1;

    static {
        headerText += declarePrintf;
        headerText += declareScanf;
        headerText += declareMalloc;
        headerText += declareStrcpy;
        headerText += declareRealloc;
        headerText += declareStrlen;
        headerText += declareMemcpy;
        headerText += printIntegerNewLine;
        headerText += printFloatNewLine;
    }

    public static void declareInteger(String id, boolean global) {
        if (global) {
            headerText += declareI32Global.apply(id);
        } else {
            buffer += declareI32.apply(id);
        }
    }

    public static void declareDouble(String id, boolean global) {
        if (global) {
            headerText += declareDoubleGlobal.apply(id);
        } else {
            buffer += declareDouble.apply(id);
        }
    }

    public static void assignInteger(String id, String value) {
        buffer += assignI32.apply(id, value);
    }

    public static void assignDouble(String id, String value) {
        buffer += assignDouble.apply(id, value);
    }

    public static void loadInteger(String id) {
        buffer += loadInteger.apply(tmp, id);
        tmp++;
    }

    public static void loadDouble(String id) {
        mainText += loadDouble.apply(tmp, id);
        tmp++;
    }

    public static String generate() {
        return headerText +
                mainDef +
                mainText +
                mainReturn;
    }

    public static void closeMain() {
        mainText += buffer;
    }
}
