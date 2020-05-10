package org.simplelang.listener.functions;

import org.simplelang.SimpleLangParser;
import org.simplelang.error.ErrorMessages;
import org.simplelang.listener.container.VariablesContainer;
import org.simplelang.listener.container.base.Value;
import org.simplelang.listener.container.base.VariableType;
import org.simplelang.llvm.io.LLVMGeneratorIO;
import org.simplelang.llvm.string.LLVMGeneratorString;

import java.util.Objects;

public class BuildInFunctionsHandler {

    public static void handlePrint(org.simplelang.SimpleLangParser.FunctionInvocationContext ctx) {
        if (!ctx.variable().isEmpty()) {
            printVariable(ctx);
        } else if (!ctx.literal().isEmpty()) {
            printLiteral(ctx);
        } else if (!ctx.expression().isEmpty()) {
            Value value = VariablesContainer.getInstance().popFromStack();
            if (value.getType().equals(VariableType.INT)) {
                LLVMGeneratorIO.printlnInteger(value.getName());
            } else if (value.getType().equals(VariableType.FLOAT)) {
                LLVMGeneratorIO.printlnDouble(value.getName());
            }
        }
    }

    public static void handleRead(org.simplelang.SimpleLangParser.FunctionInvocationContext ctx) {
        if (Objects.nonNull(ctx.variable())) {
            if (ctx.variable().isEmpty() || ctx.variable().size() > 1) {
                ErrorMessages.error(ctx.getStart().getLine(), "Incorrect number of arguments");
            }
            String variableName = ctx.variable().get(0).getText();
            if (VariablesContainer.getInstance().variableExists(variableName)) {
                ErrorMessages.error(ctx.getStart().getLine(), "Cannot read to existing variable");
            } else {
                VariablesContainer.getInstance().putVariable(variableName, VariableType.STRING);
                LLVMGeneratorIO.scanf(variableName);
            }
        } else {
            ErrorMessages.error(ctx.getStart().getLine(), "Read function accept only variable as argument");
        }
    }

    public static void handleSin(org.simplelang.SimpleLangParser.FunctionInvocationContext ctx) {

    }

    public static void handleCos(org.simplelang.SimpleLangParser.FunctionInvocationContext ctx) {

    }

    public static void handleTan(org.simplelang.SimpleLangParser.FunctionInvocationContext ctx) {

    }

    public static void handleCtg(org.simplelang.SimpleLangParser.FunctionInvocationContext ctx) {

    }

    private static void printLiteral(SimpleLangParser.FunctionInvocationContext ctx) {
        if (ctx.literal().size() > 1) {
            ErrorMessages.error(ctx.getStart().getLine(), "Invalid number of arguments");
        }

        SimpleLangParser.LiteralContext literalContext = ctx.literal().get(0);

        if (Objects.nonNull(literalContext.StringLiteral())) {
            String stringValue = literalContext.StringLiteral().getText();
            LLVMGeneratorString.printlnString(stringValue.substring(1, stringValue.length() - 1));
        } else if (Objects.nonNull(literalContext.BooleanLiteral())) {
            LLVMGeneratorString.printlnString(literalContext.BooleanLiteral().getText());
        } else if (Objects.nonNull(literalContext.NullLiteral())) {
            LLVMGeneratorString.printlnString(literalContext.NullLiteral().getText());
        } else if (Objects.nonNull(literalContext.numberLiteral())) {
            if (Objects.nonNull(literalContext.numberLiteral().IntegerLiteral())) {
                LLVMGeneratorIO.printlnInteger(literalContext.numberLiteral().IntegerLiteral().getText());
            } else if (Objects.nonNull(literalContext.numberLiteral().FloatingPointLiteral())) {
                LLVMGeneratorIO.printlnDouble(literalContext.numberLiteral().FloatingPointLiteral().getText());
            } else if (Objects.nonNull(literalContext.numberLiteral().ScientificNumberLiteral())) {
                // TODO
            }
        }
    }

    private static void printVariable(SimpleLangParser.FunctionInvocationContext ctx) {
        if (ctx.variable().size() > 1) {
            ErrorMessages.error(ctx.getStart().getLine(), "Invalid number of arguments");
        }

        String variableName = ctx.variable().get(0).getText();
        VariableType type = VariablesContainer.getInstance().getVariableType(variableName);

        if (Objects.nonNull(type)) {
            if (type.equals(VariableType.INT)) {
                LLVMGeneratorIO.printlnIntegerFromVariable(variableName);
            } else if (type.equals(VariableType.FLOAT)) {
                LLVMGeneratorIO.printfDoubleFromVariable(variableName);
            } else if (type.equals(VariableType.STRING)) {
                LLVMGeneratorString.printlnStringByVariableName(variableName);
            }
        } else {
            ErrorMessages.error(ctx.getStart().getLine(), "unknown variable " + variableName);
        }
    }


}
