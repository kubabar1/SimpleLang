package org.simplelang.listener.functions;

import org.simplelang.SimpleLangParser;
import org.simplelang.error.ErrorMessages;
import org.simplelang.listener.container.VariablesContainer;
import org.simplelang.listener.container.base.VariableType;
import org.simplelang.llvm.LLVMGenerator;

import java.util.Objects;

public class BuildInFunctionsHandler {

    public static void handlePrint(org.simplelang.SimpleLangParser.FunctionInvocationContext ctx) {
        if (!ctx.variable().isEmpty()) {
            printVariable(ctx);
        } else if (!ctx.literal().isEmpty()) {
            printLiteral(ctx);
        } else if (!ctx.expression().isEmpty()) {
            // TODO
        }
    }

    public static void handleRead(org.simplelang.SimpleLangParser.FunctionInvocationContext ctx) {
        /*String ID = ctx.VariableName().get(0).getText();
        if (!variableSet.contains(ID)) {
            variableSet.add(ID);
            LLVMGenerator.declare(ID);
        }
        LLVMGenerator.scanf(ID);*/
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
            LLVMGenerator.printfString(stringValue.substring(1, stringValue.length() - 1));
        } else if (Objects.nonNull(literalContext.BooleanLiteral())) {
            LLVMGenerator.printfString(literalContext.BooleanLiteral().getText());
        } else if (Objects.nonNull(literalContext.NullLiteral())) {
            LLVMGenerator.printfString(literalContext.NullLiteral().getText());
        } else if (Objects.nonNull(literalContext.numberLiteral())) {
            if (Objects.nonNull(literalContext.numberLiteral().IntegerLiteral())) {
                LLVMGenerator.printfI32(literalContext.numberLiteral().IntegerLiteral().getText());
            } else if (Objects.nonNull(literalContext.numberLiteral().FloatingPointLiteral())) {
                LLVMGenerator.printfDouble(literalContext.numberLiteral().FloatingPointLiteral().getText());
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
                LLVMGenerator.printfI32FromVariable(variableName);
            } else if (type.equals(VariableType.FLOAT)) {
                LLVMGenerator.printfDoubleFromVariable(variableName);
            }
        } else {
            ErrorMessages.error(ctx.getStart().getLine(), "unknown variable " + variableName);
        }
    }


}