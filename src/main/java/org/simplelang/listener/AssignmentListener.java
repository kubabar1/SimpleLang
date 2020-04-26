package org.simplelang.listener;

import org.simplelang.SimpleLangParser;
import org.simplelang.listener.container.VariablesContainer;
import org.simplelang.listener.container.base.Value;
import org.simplelang.listener.container.base.VariableType;
import org.simplelang.llvm.LLVMGenerator;

import java.util.Objects;


public class AssignmentListener extends org.simplelang.SimpleLangBaseListener {

    @Override
    public void exitAssignment(org.simplelang.SimpleLangParser.AssignmentContext ctx) {
        String variableName = ctx.VARIABLE_NAME().getText();

        if (Objects.nonNull(ctx.literal())) {
            SimpleLangParser.LiteralContext assignmentContext = ctx.literal();
            if (Objects.nonNull(assignmentContext.StringLiteral())) {
                assignString(ctx, variableName);
            } else if (Objects.nonNull(assignmentContext.BooleanLiteral())) {
                // TODO
            } else if (Objects.nonNull(assignmentContext.NullLiteral())) {
                // TODO
            }
        } else {
            assignNumber(variableName);
        }
    }

    private void assignString(SimpleLangParser.AssignmentContext ctx, String variableName) {
        if (!VariablesContainer.getInstance().variableExists(variableName)) {
            String stringValue = ctx.literal().getText();
            LLVMGenerator.declareAndAssignString(variableName, stringValue.substring(1, stringValue.length() - 1));
            VariablesContainer.getInstance().putVariable(variableName, VariableType.STRING);
        } else {
            String stringValue = ctx.literal().getText();
            LLVMGenerator.reasignString(variableName, stringValue.substring(1, stringValue.length() - 1));
        }
    }

    private void assignNumber(String variableName) {
        Value value = VariablesContainer.getInstance().popFromStack();

        if (!VariablesContainer.getInstance().variableExists(variableName)) {
            if (value.getType().equals(VariableType.INT)) {
                LLVMGenerator.declareI32(variableName);
            } else if (value.getType().equals(VariableType.FLOAT)) {
                LLVMGenerator.declareDouble(variableName);
            }
            VariablesContainer.getInstance().putVariable(variableName, value.getType());
        }

        if (value.getType().equals(VariableType.INT)) {
            LLVMGenerator.assignI32(variableName, value.getName());
        } else if (value.getType().equals(VariableType.FLOAT)) {
            LLVMGenerator.assignDouble(variableName, value.getName());
        }
    }

}
