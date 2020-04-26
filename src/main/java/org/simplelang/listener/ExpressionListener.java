package org.simplelang.listener;

import org.antlr.v4.runtime.ParserRuleContext;
import org.simplelang.SimpleLangParser;
import org.simplelang.error.ErrorMessages;
import org.simplelang.listener.container.VariablesContainer;
import org.simplelang.listener.container.base.Value;
import org.simplelang.listener.container.base.VariableType;
import org.simplelang.llvm.LLVMGenerator;

import java.util.Objects;


public class ExpressionListener extends org.simplelang.SimpleLangBaseListener {

    @Override
    public void exitNumberLiteral(SimpleLangParser.NumberLiteralContext ctx) {
        if (Objects.nonNull(ctx.IntegerLiteral())) {
            VariablesContainer.getInstance().pushToStack(new Value(ctx.IntegerLiteral().getText(), VariableType.INT));
        } else if (Objects.nonNull(ctx.FloatingPointLiteral())) {
            VariablesContainer.getInstance().pushToStack(new Value(ctx.FloatingPointLiteral().getText(), VariableType.FLOAT));
        } else if (Objects.nonNull(ctx.ScientificNumberLiteral())) {
            // TODO
        }
    }

    @Override
    public void exitExpression(SimpleLangParser.ExpressionContext ctx) {
        if (!ctx.add().isEmpty()) {
            Value v1 = VariablesContainer.getInstance().popFromStack();
            Value v2 = VariablesContainer.getInstance().popFromStack();
            System.out.println(v1);
            System.out.println(v2);
            validateValuesType(v1, v2, ctx);
            addValues(v1, v2);
        } else if (!ctx.subtract().isEmpty()) {
            Value v1 = VariablesContainer.getInstance().popFromStack();
            Value v2 = VariablesContainer.getInstance().popFromStack();
            validateValuesType(v1, v2, ctx);
            subtractValues(v1, v2);
        }
    }

    @Override
    public void exitMultiplyingExpression(SimpleLangParser.MultiplyingExpressionContext ctx) {
        if (!ctx.multiply().isEmpty()) {
            Value v1 = VariablesContainer.getInstance().popFromStack();
            Value v2 = VariablesContainer.getInstance().popFromStack();
            validateValuesType(v1, v2, ctx);
            multiplyValues(v1, v2);
        } else if (!ctx.divide().isEmpty()) {
            Value v1 = VariablesContainer.getInstance().popFromStack();
            Value v2 = VariablesContainer.getInstance().popFromStack();
            validateValuesType(v1, v2, ctx);
            divideValues(v1, v2);
        }
    }

    @Override
    public void exitPowExpression(SimpleLangParser.PowExpressionContext ctx) {

    }

    public void addValues(Value v1, Value v2) {
        if (v1.getType().equals(VariableType.INT)) {
            LLVMGenerator.addI32(v1.getName(), v2.getName());
            VariablesContainer.getInstance().pushToStack(new Value("%" + (LLVMGenerator.reg - 1), VariableType.INT));
        } else if (v1.getType().equals(VariableType.FLOAT)) {
            LLVMGenerator.addDouble(v1.getName(), v2.getName());
            VariablesContainer.getInstance().pushToStack(new Value("%" + (LLVMGenerator.reg - 1), VariableType.FLOAT));
        }
    }

    private void subtractValues(Value v1, Value v2) {

    }

    public void multiplyValues(Value v1, Value v2) {
        if (v1.getType().equals(VariableType.INT)) {
            LLVMGenerator.multI32(v1.getName(), v2.getName());
            VariablesContainer.getInstance().pushToStack(new Value("%" + (LLVMGenerator.reg - 1), VariableType.INT));
        } else if (v1.getType().equals(VariableType.FLOAT)) {
            LLVMGenerator.multDouble(v1.getName(), v2.getName());
            VariablesContainer.getInstance().pushToStack(new Value("%" + (LLVMGenerator.reg - 1), VariableType.FLOAT));
        }
    }

    public void divideValues(Value v1, Value v2) {

    }

    public void powValues(Value v1, Value v2) {

    }

    private void validateValuesType(Value v1, Value v2, ParserRuleContext ctx) {
        if (!v1.getType().equals(v2.getType())) {
            ErrorMessages.error(ctx.getStart().getLine(), "add type mismatch");
        }
    }

}
