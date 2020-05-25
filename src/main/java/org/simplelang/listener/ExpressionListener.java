package org.simplelang.listener;

import org.antlr.v4.runtime.ParserRuleContext;
import org.simplelang.SimpleLangParser;
import org.simplelang.error.ErrorMessages;
import org.simplelang.listener.containers.BaseContainer;
import org.simplelang.listener.containers.model.Value;
import org.simplelang.listener.containers.model.VariableType;
import org.simplelang.llvm.expression.LLVMGeneratorExpression;

import static org.simplelang.llvm.LLVMGeneratorBase.loadInteger;
import static org.simplelang.llvm.LLVMGeneratorBase.loadDouble;
import static org.simplelang.llvm.LLVMGeneratorBase.tmp;
import static org.simplelang.listener.utils.VariableUtils.getVariableType;

import java.util.Objects;


public class ExpressionListener extends org.simplelang.SimpleLangBaseListener {

    private BaseContainer baseContainer;

    public ExpressionListener() {
        baseContainer = BaseContainer.getInstance();
    }

    @Override
    public void exitVariable(SimpleLangParser.VariableContext ctx) {
        String variableName = ctx.VARIABLE_NAME().getText();
        VariableType variableType = getVariableType(variableName);

        if (Objects.nonNull(variableName)) {
            if (this.baseContainer.localNamesContains(variableName)) {
                if (variableType.equals(VariableType.INT)) {
                    loadInteger("%" + variableName);
                } else if (variableType.equals(VariableType.DOUBLE)) {
                    loadDouble("%" + variableName);
                }
            } else if (this.baseContainer.globalNamesContains(variableName)) {
                if (variableType.equals(VariableType.INT)) {
                    loadInteger("@" + variableName);
                } else if (variableType.equals(VariableType.DOUBLE)) {
                    loadDouble("@" + variableName);
                }
            }
            this.baseContainer.pushToStack(new Value("%" + (tmp - 1), variableType));
        }
    }

    @Override
    public void exitNumberLiteral(SimpleLangParser.NumberLiteralContext ctx) {
        if (Objects.nonNull(ctx.IntegerLiteral())) {
            this.baseContainer.pushToStack(new Value(ctx.IntegerLiteral().getText(), VariableType.INT)); // TODO: fix
        } else if (Objects.nonNull(ctx.FloatingPointLiteral())) {
            this.baseContainer.pushToStack(new Value(ctx.FloatingPointLiteral().getText(), VariableType.DOUBLE)); // TODO: fix
        } else if (Objects.nonNull(ctx.ScientificNumberLiteral())) {
            // TODO
        }
    }

    @Override
    public void exitExpression(SimpleLangParser.ExpressionContext ctx) {
        if (Objects.nonNull(ctx.add()) && !ctx.add().isEmpty()) {
            Value v1 = this.baseContainer.popFromStack();
            Value v2 = this.baseContainer.popFromStack();
            validateValuesType(v1, v2, ctx);
            addValues(v1, v2);
        } else if (Objects.nonNull(ctx.subtract()) && !ctx.subtract().isEmpty()) {
            Value v1 = this.baseContainer.popFromStack();
            Value v2 = this.baseContainer.popFromStack();
            validateValuesType(v1, v2, ctx);
            subtractValues(v1, v2);
        }
    }

    @Override
    public void exitMultiplyingExpression(SimpleLangParser.MultiplyingExpressionContext ctx) {
        if (Objects.nonNull(ctx.multiply()) && !ctx.multiply().isEmpty()) {
            Value v1 = this.baseContainer.popFromStack();
            Value v2 = this.baseContainer.popFromStack();
            validateValuesType(v1, v2, ctx);
            multiplyValues(v1, v2);
        } else if (Objects.nonNull(ctx.divide()) && !ctx.divide().isEmpty()) {
            Value v1 = this.baseContainer.popFromStack();
            Value v2 = this.baseContainer.popFromStack();
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
            LLVMGeneratorExpression.addInteger(v1.getValue(), v2.getValue());
            this.baseContainer.pushToStack(new Value("%" + (tmp - 1), VariableType.INT));
        } else if (v1.getType().equals(VariableType.DOUBLE)) {
            LLVMGeneratorExpression.addDouble(v1.getValue(), v2.getValue());
            this.baseContainer.pushToStack(new Value("%" + (tmp - 1), VariableType.DOUBLE));
        }
    }

    private void subtractValues(Value v1, Value v2) {
        if (v1.getType().equals(VariableType.INT)) {
            LLVMGeneratorExpression.subInteger(v1.getValue(), v2.getValue());
            this.baseContainer.pushToStack(new Value("%" + (tmp - 1), VariableType.INT));
        } else if (v1.getType().equals(VariableType.DOUBLE)) {
            LLVMGeneratorExpression.subDouble(v1.getValue(), v2.getValue());
            this.baseContainer.pushToStack(new Value("%" + (tmp - 1), VariableType.DOUBLE));
        }
    }

    public void multiplyValues(Value v1, Value v2) {
        if (v1.getType().equals(VariableType.INT)) {
            LLVMGeneratorExpression.multInteger(v1.getValue(), v2.getValue());
            this.baseContainer.pushToStack(new Value("%" + (tmp - 1), VariableType.INT));
        } else if (v1.getType().equals(VariableType.DOUBLE)) {
            LLVMGeneratorExpression.multDouble(v1.getValue(), v2.getValue());
            this.baseContainer.pushToStack(new Value("%" + (tmp - 1), VariableType.DOUBLE));
        }
    }

    public void divideValues(Value v1, Value v2) {
        if (v1.getType().equals(VariableType.INT)) {
            LLVMGeneratorExpression.divInteger(v1.getValue(), v2.getValue());
            this.baseContainer.pushToStack(new Value("%" + (tmp - 1), VariableType.INT));
        } else if (v1.getType().equals(VariableType.DOUBLE)) {
            LLVMGeneratorExpression.divDouble(v1.getValue(), v2.getValue());
            this.baseContainer.pushToStack(new Value("%" + (tmp - 1), VariableType.DOUBLE));
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
