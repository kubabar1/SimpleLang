package org.simplelang.llvm.expression;

import static org.simplelang.llvm.expression.LLVMConstantsExpression.addInteger;
import static org.simplelang.llvm.expression.LLVMConstantsExpression.addDouble;
import static org.simplelang.llvm.expression.LLVMConstantsExpression.subInteger;
import static org.simplelang.llvm.expression.LLVMConstantsExpression.subDouble;
import static org.simplelang.llvm.expression.LLVMConstantsExpression.multInteger;
import static org.simplelang.llvm.expression.LLVMConstantsExpression.multDouble;
import static org.simplelang.llvm.expression.LLVMConstantsExpression.divInteger;
import static org.simplelang.llvm.expression.LLVMConstantsExpression.divDouble;
import static org.simplelang.llvm.LLVMGeneratorBase.mainText;
import static org.simplelang.llvm.LLVMGeneratorBase.reg;

public interface LLVMGeneratorExpression {

    static void addInteger(String val1, String val2) {
        mainText += addInteger.apply(reg, val1, val2);
        reg++;
    }

    static void addDouble(String val1, String val2) {
        mainText += addDouble.apply(reg, val1, val2);
        reg++;
    }

    static void subInteger(String val1, String val2) {
        mainText += subInteger.apply(reg, val1, val2);
        reg++;
    }

    static void subDouble(String val1, String val2) {
        mainText += subDouble.apply(reg, val1, val2);
        reg++;
    }

    static void multInteger(String val1, String val2) {
        mainText += multInteger.apply(reg, val1, val2);
        reg++;
    }

    static void multDouble(String val1, String val2) {
        mainText += multDouble.apply(reg, val1, val2);
        reg++;
    }

    static void divInteger(String val1, String val2) {
        mainText += divInteger.apply(reg, val1, val2);
        reg++;
    }

    static void divDouble(String val1, String val2) {
        mainText += divDouble.apply(reg, val1, val2);
        reg++;
    }

}
