package org.simplelang.listener;

import org.simplelang.SimpleLangParser;
import org.simplelang.error.ErrorMessages;
import org.simplelang.listener.containers.BaseContainer;
import org.simplelang.listener.built_in_functions.BuiltInFunctions;
import org.simplelang.listener.built_in_functions.BuiltInFunctionsHandler;
import org.simplelang.llvm.function.LLVMGeneratorFunction;

import java.util.Objects;

public class FunctionListener extends org.simplelang.SimpleLangBaseListener {

    //private String currentFunction;

    private BaseContainer baseContainer;

    public FunctionListener() {
        this.baseContainer = BaseContainer.getInstance();
    }

    @Override
    public void exitFunctionInvocation(org.simplelang.SimpleLangParser.FunctionInvocationContext ctx) {
        if (Objects.nonNull(ctx.buildInFunction())) {
            String buildInFunctionName = ctx.buildInFunction().getText().toUpperCase();
            try {
                BuiltInFunctions buildInFunctionType = BuiltInFunctions.valueOf(buildInFunctionName);
                handleBuildInFunctions(buildInFunctionType, ctx.functionInvocationParams());
            } catch (IllegalArgumentException e) {
                ErrorMessages.error(ctx.getStart().getLine(), "Function '" + buildInFunctionName + "' was not defined");
            }
        } else if (Objects.nonNull(ctx.functionInvocationName())) {
            String functionName = ctx.functionInvocationName().VARIABLE_NAME().getText();
            if (!this.baseContainer.functionsContains(functionName)) {
                ErrorMessages.error(ctx.getStart().getLine(), "Function '" + functionName + "' was not defined");
            } else {
                LLVMGeneratorFunction.callFunction(functionName);
            }
        }
    }

    @Override
    public void exitFunctionName(SimpleLangParser.FunctionNameContext ctx) {
        String functionName = ctx.VARIABLE_NAME().getText();
        this.baseContainer.addFunction(functionName);
        //this.currentFunction = functionName;
        LLVMGeneratorFunction.functionDefinitionStart(functionName);
    }

    @Override
    public void enterBlockFunction(SimpleLangParser.BlockFunctionContext ctx) {
        this.baseContainer.setGlobalFalse();
    }

    @Override
    public void exitBlockFunction(SimpleLangParser.BlockFunctionContext ctx) {
        /*if (!baseContainer.localNamesContains(this.currentFunction)) {
            LLVMGeneratorBase.assignInteger(setVariable(this.currentFunction, VariableType.INT), "0");
        }
        LLVMGeneratorBase.loadInteger("%" + this.currentFunction);*/
        LLVMGeneratorFunction.functionDefinitionEnd();
        this.baseContainer.clearLocalNames();
        this.baseContainer.setGlobalTrue();
    }

    private void handleBuildInFunctions(BuiltInFunctions buildInFunction, SimpleLangParser.FunctionInvocationParamsContext ctx) {
        if (ctx.functionParam().size() != 1) {
            ErrorMessages.error(ctx.getStart().getLine(), "Incorrect number of arguments");
        }
        SimpleLangParser.FunctionParamContext param = ctx.functionParam(0);
        switch (buildInFunction) {
            case PRINT: {
                BuiltInFunctionsHandler.handlePrint(param);
                break;
            }
            case READ: {
                BuiltInFunctionsHandler.handleRead(param);
                break;
            }
            case SIN: {
                BuiltInFunctionsHandler.handleSin(param);
                break;
            }
            case COS: {
                BuiltInFunctionsHandler.handleCos(param);
                break;
            }
            case TAN: {
                BuiltInFunctionsHandler.handleTan(param);
                break;
            }
            case CTG: {
                BuiltInFunctionsHandler.handleCtg(param);
                break;
            }
        }
    }

}
