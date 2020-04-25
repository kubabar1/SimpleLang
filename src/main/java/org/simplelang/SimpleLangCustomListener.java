package org.simplelang;

import org.simplelang.llvm.LLVMGenerator;

import java.util.HashMap;
import java.util.Objects;
import java.util.Stack;

public class SimpleLangCustomListener extends org.simplelang.SimpleLangBaseListener {

    private HashMap<String, VarType> variables;

    private Stack<Value> stack;

    public SimpleLangCustomListener() {
        this.variables = new HashMap<>();
        this.stack = new Stack<>();
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
    public void exitLiteral(org.simplelang.SimpleLangParser.LiteralContext ctx) {
        if (Objects.nonNull(ctx.FloatingPointLiteral())) {
            stack.push(new Value(ctx.FloatingPointLiteral().getText(), VarType.FLOAT));
        } else if (Objects.nonNull(ctx.IntegerLiteral())) {
            stack.push(new Value(ctx.IntegerLiteral().getText(), VarType.INT));
        } else if (Objects.nonNull(ctx.BooleanLiteral())) {
            stack.push(new Value(ctx.BooleanLiteral().getText(), VarType.BOOLEAN));
        } else if (Objects.nonNull(ctx.StringLiteral())) {
            stack.push(new Value(ctx.StringLiteral().getText(), VarType.STRING));
        } else if (Objects.nonNull(ctx.NullLiteral())) {
            stack.push(new Value(ctx.NullLiteral().getText(), VarType.NULL));
        }
    }

    @Override
    public void exitAssignment(org.simplelang.SimpleLangParser.AssignmentContext ctx) {
        String variableName = ctx.VariableName().getText();
        Value value = stack.pop();
        variables.put(variableName, value.type);

        if (value.type.equals(VarType.INT)) {
            LLVMGenerator.declareI32(variableName);
            LLVMGenerator.assignI32(variableName, value.name);
        } else if (value.type.equals(VarType.FLOAT)) {
            LLVMGenerator.declareDouble(variableName);
            LLVMGenerator.assignDouble(variableName, value.name);
        }
    }

    @Override
    public void exitPlus(org.simplelang.SimpleLangParser.PlusContext ctx) {
        Value v1 = stack.pop();
        Value v2 = stack.pop();
        if (v1.type.equals(v2.type)) {
            if (v1.type.equals(VarType.INT)) {
                LLVMGenerator.addI32(v1.name, v2.name);
                stack.push(new Value("%" + (LLVMGenerator.reg - 1), VarType.INT));
            } else if (v1.type.equals(VarType.FLOAT)) {
                LLVMGenerator.addDouble(v1.name, v2.name);
                stack.push(new Value("%" + (LLVMGenerator.reg - 1), VarType.FLOAT));
            }
        } else {
            error(ctx.getStart().getLine(), "add type mismatch");
        }
    }

    @Override
    public void exitMultiply(org.simplelang.SimpleLangParser.MultiplyContext ctx) {
        Value v1 = stack.pop();
        Value v2 = stack.pop();
        if (v1.type.equals(v2.type)) {
            if (v1.type.equals(VarType.INT)) {
                LLVMGenerator.multI32(v1.name, v2.name);
                stack.push(new Value("%" + (LLVMGenerator.reg - 1), VarType.INT));
            } else if (v1.type.equals(VarType.FLOAT)) {
                LLVMGenerator.multDouble(v1.name, v2.name);
                stack.push(new Value("%" + (LLVMGenerator.reg - 1), VarType.FLOAT));
            }
        } else {
            error(ctx.getStart().getLine(), "mult type mismatch");
        }
    }

    @Override
    public void exitToIntCasting(org.simplelang.SimpleLangParser.ToIntCastingContext ctx) {
        Value v = stack.pop();
        LLVMGenerator.fptosi(v.name);
        stack.push(new Value("%" + (LLVMGenerator.reg - 1), VarType.INT));
    }

    @Override
    public void exitToFloatCasting(org.simplelang.SimpleLangParser.ToFloatCastingContext ctx) {
        Value v = stack.pop();
        LLVMGenerator.sitofp(v.name);
        stack.push(new Value("%" + (LLVMGenerator.reg - 1), VarType.FLOAT));
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
        String variableName = ctx.VariableName().get(0).getText();
        VarType type = variables.get(variableName);

        if (Objects.nonNull(type)) {
            if (type.equals(VarType.INT)) {
                LLVMGenerator.printfI32(variableName);
            } else if (type.equals(VarType.FLOAT)) {
                LLVMGenerator.printfDouble(variableName);
            }
        } else {
            error(ctx.getStart().getLine(), "unknown variable " + variableName);
        }
    }

    private void handleRead(org.simplelang.SimpleLangParser.FunctionInvocationContext ctx) {
        /*String ID = ctx.VariableName().get(0).getText();
        if (!variableSet.contains(ID)) {
            variableSet.add(ID);
            LLVMGenerator.declare(ID);
        }
        LLVMGenerator.scanf(ID);*/
    }

    void error(int line, String msg) {
        System.err.println("Error, line " + line + ", " + msg);
        System.exit(1);
    }
}

