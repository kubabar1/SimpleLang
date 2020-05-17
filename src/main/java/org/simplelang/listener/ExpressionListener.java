package org.simplelang.listener;

import org.antlr.v4.runtime.ParserRuleContext;
import org.simplelang.SimpleLangParser;
import org.simplelang.error.ErrorMessages;
import org.simplelang.listener.container.VariablesContainer;
import org.simplelang.listener.container.base.Value;
import org.simplelang.listener.container.base.VariableType;
import org.simplelang.llvm.LLVMGeneratorBase;
import org.simplelang.llvm.expression.LLVMGeneratorExpression;

import java.util.Objects;


public class ExpressionListener extends org.simplelang.SimpleLangBaseListener {

    @Override
    public void exitVariable(SimpleLangParser.VariableContext ctx) {
        String variableName = ctx.VARIABLE_NAME().getText();
        if (VariablesContainer.getInstance().variableExists(variableName)) {
            VariableType variableType = VariablesContainer.getInstance().getVariableType(variableName);
            VariablesContainer.getInstance().pushToStack(new Value("%" + LLVMGeneratorBase.reg, variableType)); // TODO: fix
            if (VariableType.INT.equals(variableType)) {
                LLVMGeneratorBase.loadInteger(variableName); // TODO: fix
            } else if (VariableType.FLOAT.equals(variableType)) {
                LLVMGeneratorBase.loadDouble(variableName); // TODO: fix
            }
        }
    }

    @Override
    public void exitNumberLiteral(SimpleLangParser.NumberLiteralContext ctx) {
        if (Objects.nonNull(ctx.IntegerLiteral())) {
            VariablesContainer.getInstance().pushToStack(new Value(ctx.IntegerLiteral().getText(), VariableType.INT)); // TODO: fix
        } else if (Objects.nonNull(ctx.FloatingPointLiteral())) {
            VariablesContainer.getInstance().pushToStack(new Value(ctx.FloatingPointLiteral().getText(), VariableType.FLOAT)); // TODO: fix
        } else if (Objects.nonNull(ctx.ScientificNumberLiteral())) {
            // TODO
        }
    }

    @Override
    public void exitExpression(SimpleLangParser.ExpressionContext ctx) {
        if (Objects.nonNull(ctx.add()) && !ctx.add().isEmpty()) {
            Value v1 = VariablesContainer.getInstance().popFromStack();
            Value v2 = VariablesContainer.getInstance().popFromStack();
            validateValuesType(v1, v2, ctx);
            addValues(v1, v2);
        } else if (Objects.nonNull(ctx.subtract()) && !ctx.subtract().isEmpty()) {
            Value v1 = VariablesContainer.getInstance().popFromStack();
            Value v2 = VariablesContainer.getInstance().popFromStack();
            validateValuesType(v1, v2, ctx);
            subtractValues(v1, v2);
        }
    }

    @Override
    public void exitMultiplyingExpression(SimpleLangParser.MultiplyingExpressionContext ctx) {
        if (Objects.nonNull(ctx.multiply()) && !ctx.multiply().isEmpty()) {
            Value v1 = VariablesContainer.getInstance().popFromStack();
            Value v2 = VariablesContainer.getInstance().popFromStack();
            validateValuesType(v1, v2, ctx);
            multiplyValues(v1, v2);
        } else if (Objects.nonNull(ctx.divide()) && !ctx.divide().isEmpty()) {
            Value v1 = VariablesContainer.getInstance().popFromStack();
            Value v2 = VariablesContainer.getInstance().popFromStack();
            validateValuesType(v1, v2, ctx);
            divideValues(v1, v2);
        }
    }

    @Override
    public void exitPowExpression(SimpleLangParser.PowExpressionContext ctx) {
        // TODO: Add pow expression
    }

    public void addValues(Value v1, Value v2) {
        if (v1.getType().equals(VariableType.INT)) {
            LLVMGeneratorExpression.addInteger(v1.getName(), v2.getName());
            VariablesContainer.getInstance().pushToStack(new Value("%" + (LLVMGeneratorBase.reg - 1), VariableType.INT));
        } else if (v1.getType().equals(VariableType.FLOAT)) {
            LLVMGeneratorExpression.addDouble(v1.getName(), v2.getName());
            VariablesContainer.getInstance().pushToStack(new Value("%" + (LLVMGeneratorBase.reg - 1), VariableType.FLOAT));
        }
    }

    private void subtractValues(Value v1, Value v2) {
        if (v1.getType().equals(VariableType.INT)) {
            LLVMGeneratorExpression.subInteger(v1.getName(), v2.getName());
            VariablesContainer.getInstance().pushToStack(new Value("%" + (LLVMGeneratorBase.reg - 1), VariableType.INT));
        } else if (v1.getType().equals(VariableType.FLOAT)) {
            LLVMGeneratorExpression.subDouble(v1.getName(), v2.getName());
            VariablesContainer.getInstance().pushToStack(new Value("%" + (LLVMGeneratorBase.reg - 1), VariableType.FLOAT));
        }
    }

    public void multiplyValues(Value v1, Value v2) {
        if (v1.getType().equals(VariableType.INT)) {
            LLVMGeneratorExpression.multInteger(v1.getName(), v2.getName());
            VariablesContainer.getInstance().pushToStack(new Value("%" + (LLVMGeneratorBase.reg - 1), VariableType.INT));
        } else if (v1.getType().equals(VariableType.FLOAT)) {
            LLVMGeneratorExpression.multDouble(v1.getName(), v2.getName());
            VariablesContainer.getInstance().pushToStack(new Value("%" + (LLVMGeneratorBase.reg - 1), VariableType.FLOAT));
        }
    }

    public void divideValues(Value v1, Value v2) {
        if (v1.getType().equals(VariableType.INT)) {
            LLVMGeneratorExpression.divInteger(v1.getName(), v2.getName());
            VariablesContainer.getInstance().pushToStack(new Value("%" + (LLVMGeneratorBase.reg - 1), VariableType.INT));
        } else if (v1.getType().equals(VariableType.FLOAT)) {
            LLVMGeneratorExpression.divDouble(v1.getName(), v2.getName());
            VariablesContainer.getInstance().pushToStack(new Value("%" + (LLVMGeneratorBase.reg - 1), VariableType.FLOAT));
        }
    }

    public void powValues(Value v1, Value v2) {

    }

    private void validateValuesType(Value v1, Value v2, ParserRuleContext ctx) {
        if (!v1.getType().equals(v2.getType())) {
            ErrorMessages.error(ctx.getStart().getLine(), " type mismatch");
        }
    }

}
