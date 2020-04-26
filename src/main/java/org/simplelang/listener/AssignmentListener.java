package org.simplelang.listener;

import org.simplelang.listener.container.VariablesContainer;
import org.simplelang.listener.container.base.Value;
import org.simplelang.listener.container.base.VariableType;
import org.simplelang.llvm.LLVMGenerator;


public class AssignmentListener extends org.simplelang.SimpleLangBaseListener {

    @Override
    public void exitAssignment(org.simplelang.SimpleLangParser.AssignmentContext ctx) {
        String variableName = ctx.VARIABLE_NAME().getText();
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
