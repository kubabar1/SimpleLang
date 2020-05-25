package org.simplelang.listener.built_in_functions;

import org.simplelang.SimpleLangParser;
import org.simplelang.error.ErrorMessages;
import org.simplelang.listener.containers.BaseContainer;
import org.simplelang.listener.containers.model.VariableType;
import org.simplelang.llvm.io.LLVMGeneratorIO;
import org.simplelang.llvm.string.LLVMGeneratorString;

import java.util.Objects;

import static org.simplelang.listener.utils.VariableUtils.getVariableType;

public class BuiltInFunctionsHandler {

    public static void handlePrint(SimpleLangParser.FunctionParamContext ctx) {
        if (Objects.nonNull(ctx.variable())) {
            printVariable(ctx.variable());
        } else if (Objects.nonNull(ctx.literal())) {
            printLiteral(ctx.literal());
        } else if (Objects.nonNull(ctx.expression())) {
            /*Value value = baseContainer.popFromStack();
            if (value.getType().equals(VariableType.INT)) {
                LLVMGeneratorIO.printlnInteger(value.getValue());
            } else if (value.getType().equals(VariableType.DOUBLE)) {
                LLVMGeneratorIO.printlnDouble(value.getValue());
            }*/
        }
    }

    public static void handleRead(SimpleLangParser.FunctionParamContext ctx) {
        /*if (Objects.nonNull(ctx.variable())) {
            if (Objects.isNull(ctx.variable())) {
                ErrorMessages.error(ctx.getStart().getLine(), "Incorrect number of arguments");
            }
            String variableName = ctx.variable().VARIABLE_NAME().getText();
            if (baseContainer.variableExists(variableName)) {
                ErrorMessages.error(ctx.getStart().getLine(), "Cannot read to existing variable");
            } else {
                baseContainer.putVariable(variableName, VariableType.STRING);
                LLVMGeneratorIO.scanf(variableName);
            }
        } else {
            ErrorMessages.error(ctx.getStart().getLine(), "Read function accept only variable as argument");
        }*/
    }

    public static void handleSin(org.simplelang.SimpleLangParser.FunctionParamContext ctx) {

    }

    public static void handleCos(org.simplelang.SimpleLangParser.FunctionParamContext ctx) {

    }

    public static void handleTan(org.simplelang.SimpleLangParser.FunctionParamContext ctx) {

    }

    public static void handleCtg(org.simplelang.SimpleLangParser.FunctionParamContext ctx) {

    }

    private static void printLiteral(SimpleLangParser.LiteralContext ctx) {
        if (Objects.nonNull(ctx.StringLiteral())) {
            String stringValue = ctx.StringLiteral().getText();
            LLVMGeneratorString.printlnString(stringValue.substring(1, stringValue.length() - 1));
        } else if (Objects.nonNull(ctx.BooleanLiteral())) {
            LLVMGeneratorString.printlnString(ctx.BooleanLiteral().getText());
        } else if (Objects.nonNull(ctx.NullLiteral())) {
            LLVMGeneratorString.printlnString(ctx.NullLiteral().getText());
        } else if (Objects.nonNull(ctx.numberLiteral())) {
            if (Objects.nonNull(ctx.numberLiteral().IntegerLiteral())) {
                LLVMGeneratorIO.printlnInteger(ctx.numberLiteral().IntegerLiteral().getText());
            } else if (Objects.nonNull(ctx.numberLiteral().FloatingPointLiteral())) {
                LLVMGeneratorIO.printlnDouble(ctx.numberLiteral().FloatingPointLiteral().getText());
            } else if (Objects.nonNull(ctx.numberLiteral().ScientificNumberLiteral())) {
                // TODO
            }
        }
    }

    private static void printVariable(SimpleLangParser.VariableContext ctx) {
        String variableName = ctx.VARIABLE_NAME().getText();
        VariableType variableType = getVariableType(variableName);

        if (Objects.nonNull(variableType)) {
            if (variableType.equals(VariableType.INT)) {
                LLVMGeneratorIO.printlnIntegerFromVariable(variableName);
            } else if (variableType.equals(VariableType.DOUBLE)) {
                LLVMGeneratorIO.printfDoubleFromVariable(variableName);
            } else if (variableType.equals(VariableType.STRING)) {
                LLVMGeneratorString.printlnStringByVariableName(variableName);
            }
        } else {
            ErrorMessages.error(ctx.getStart().getLine(), "Unknown variable " + variableName);
        }
    }


}
