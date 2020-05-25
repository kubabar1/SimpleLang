package org.simplelang.llvm.expression;

import static org.simplelang.llvm.expression.LLVMConstantsExpression.addInteger;
import static org.simplelang.llvm.expression.LLVMConstantsExpression.addDouble;
import static org.simplelang.llvm.expression.LLVMConstantsExpression.subInteger;
import static org.simplelang.llvm.expression.LLVMConstantsExpression.subDouble;
import static org.simplelang.llvm.expression.LLVMConstantsExpression.multInteger;
import static org.simplelang.llvm.expression.LLVMConstantsExpression.multDouble;
import static org.simplelang.llvm.expression.LLVMConstantsExpression.divInteger;
import static org.simplelang.llvm.expression.LLVMConstantsExpression.divDouble;
import static org.simplelang.llvm.LLVMGeneratorBase.buffer;
import static org.simplelang.llvm.LLVMGeneratorBase.tmp;

public class LLVMGeneratorExpression {

    public static void addInteger(String val1, String val2) {
        buffer += addInteger.apply(tmp, val1, val2);
        tmp++;
    }

    public static void addDouble(String val1, String val2) {
        buffer += addDouble.apply(tmp, val1, val2);
        tmp++;
    }

    public static void subInteger(String val1, String val2) {
        buffer += subInteger.apply(tmp, val1, val2);
        tmp++;
    }

    public static void subDouble(String val1, String val2) {
        buffer += subDouble.apply(tmp, val1, val2);
        tmp++;
    }

    public static void multInteger(String val1, String val2) {
        buffer += multInteger.apply(tmp, val1, val2);
        tmp++;
    }

    public static void multDouble(String val1, String val2) {
        buffer += multDouble.apply(tmp, val1, val2);
        tmp++;
    }

    public static void divInteger(String val1, String val2) {
        buffer += divInteger.apply(tmp, val1, val2);
        tmp++;
    }

    public static void divDouble(String val1, String val2) {
        buffer += divDouble.apply(tmp, val1, val2);
        tmp++;
    }

}
