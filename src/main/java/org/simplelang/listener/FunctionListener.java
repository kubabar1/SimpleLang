package org.simplelang.listener;

import org.simplelang.listener.functions.BuildInFunctions;
import org.simplelang.listener.functions.BuildInFunctionsHandler;

import java.util.Objects;

public class FunctionListener extends org.simplelang.SimpleLangBaseListener {

    @Override
    public void exitFunctionInvocation(org.simplelang.SimpleLangParser.FunctionInvocationContext ctx) {
        if (Objects.nonNull(ctx.buildInFunction())) {
            String buildInFunctionName = ctx.buildInFunction().getText().toUpperCase();
            try {
                BuildInFunctions buildInFunctionType = BuildInFunctions.valueOf(buildInFunctionName);
                handleBuildInFunctions(buildInFunctionType, ctx);
            } catch (IllegalArgumentException e) {
                System.err.println("Function '" + buildInFunctionName + "' was not defined");
            }
        }
    }

    private void handleBuildInFunctions(BuildInFunctions buildInFunction, org.simplelang.SimpleLangParser.FunctionInvocationContext ctx) {
        switch (buildInFunction) {
            case PRINT: {
                BuildInFunctionsHandler.handlePrint(ctx);
                break;
            }
            case READ: {
                BuildInFunctionsHandler.handleRead(ctx);
                break;
            }
            case SIN: {
                BuildInFunctionsHandler.handleSin(ctx);
                break;
            }
            case COS: {
                BuildInFunctionsHandler.handleCos(ctx);
                break;
            }
            case TAN: {
                BuildInFunctionsHandler.handleTan(ctx);
                break;
            }
            case CTG: {
                BuildInFunctionsHandler.handleCtg(ctx);
                break;
            }
        }
    }

}
