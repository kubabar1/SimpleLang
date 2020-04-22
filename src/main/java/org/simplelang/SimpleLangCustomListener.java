package org.simplelang;

import java.util.HashSet;
import java.util.Objects;

public class SimpleLangCustomListener extends org.simplelang.SimpleLangBaseListener {

    private HashSet<String> variableSet;

    public SimpleLangCustomListener() {
        variableSet = new HashSet<>();
    }

    @Override
    public void exitAssignment(org.simplelang.SimpleLangParser.AssignmentContext ctx) {
        String variableName = ctx.VariableName().getText();
        String variableValue = ctx.literal().getText();
        if (!variableSet.contains(variableName)) {
            variableSet.add(variableName);
            LLVMGenerator.declare(variableName);
        }
        LLVMGenerator.assign(variableName, variableValue);
    }

    @Override
    public void exitFunctionInvocation(org.simplelang.SimpleLangParser.FunctionInvocationContext ctx) {
        if (Objects.nonNull(ctx.buildInFunctions())) {
            String buildInFunctionName = ctx.buildInFunctions().getText().toUpperCase();
            try {
                BuildInFunctions buildInFunctionType = BuildInFunctions.valueOf(buildInFunctionName);
                handleBuildInFunctions(buildInFunctionType, ctx);
            } catch (IllegalArgumentException e) {
                System.err.println("Function '" + buildInFunctionName + "' was not defined");
            }
        }
    }

    @Override
    public void exitProgram(org.simplelang.SimpleLangParser.ProgramContext ctx) {
        System.out.println(LLVMGenerator.generate());
    }

    private void handleBuildInFunctions(BuildInFunctions buildInFunction, org.simplelang.SimpleLangParser.FunctionInvocationContext ctx) {
        switch (buildInFunction) {
            case PRINT: {
                handlePrint(ctx);
                break;
            }
            case READ: {
                handleRead(ctx);
                break;
            }
        }
    }

    private void handlePrint(org.simplelang.SimpleLangParser.FunctionInvocationContext ctx) {
        String variableName = ctx.VariableName().getText();
        if (variableSet.contains(variableName)) {
            LLVMGenerator.printf(variableName);
        } else {
            ctx.getStart().getLine();
            System.err.println("Line " + ctx.getStart().getLine() + ", unknown variable: " + variableName);
        }
    }

    private void handleRead(org.simplelang.SimpleLangParser.FunctionInvocationContext ctx) {
        String ID = ctx.VariableName().getText();
        if (!variableSet.contains(ID)) {
            variableSet.add(ID);
            LLVMGenerator.declare(ID);
        }
        LLVMGenerator.scanf(ID);
    }
}