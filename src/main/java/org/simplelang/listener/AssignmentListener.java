package org.simplelang.listener;

import org.simplelang.SimpleLangParser;
import org.simplelang.error.ErrorMessages;
import org.simplelang.listener.container.VariablesContainer;
import org.simplelang.listener.container.base.Value;
import org.simplelang.listener.container.base.VariableType;
import org.simplelang.llvm.LLVMGeneratorBase;
import org.simplelang.llvm.array.LLVMGeneratorArray;
import org.simplelang.llvm.string.LLVMGeneratorString;

import java.util.ArrayList;
import java.util.List;
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
        } else if (Objects.nonNull(ctx.expression())) {
            assignNumber(variableName);
        } else if (Objects.nonNull(ctx.array())) {
            assignArray(ctx, variableName);
        }
    }

    private void assignArray(SimpleLangParser.AssignmentContext ctx, String variableName) {
        boolean arrayNotEmpty = Objects.nonNull(ctx.array().literal()) && !ctx.array().literal().isEmpty();
        int arraySize = arrayNotEmpty ? ctx.array().literal().size() : 0;

        if (!VariablesContainer.getInstance().variableExists(variableName)) {
            LLVMGeneratorArray.declareArray(variableName, arraySize);
            VariablesContainer.getInstance().putVariable(variableName, VariableType.ARRAY);
        }
        if (arrayNotEmpty) {
            SimpleLangParser.LiteralContext tmpObject = ctx.array().literal().get(0);
            if (Objects.nonNull(tmpObject.numberLiteral())) {
                if (Objects.nonNull(tmpObject.numberLiteral().IntegerLiteral())) {
                    String[] array = mapLiteralListToArray(ctx.array().literal(), VariableType.INT, ctx);
                    LLVMGeneratorArray.assignArray(variableName, array, VariableType.INT);
                } else if (Objects.nonNull(tmpObject.numberLiteral().FloatingPointLiteral())) {
                    String[] array = mapLiteralListToArray(ctx.array().literal(), VariableType.FLOAT, ctx);
                    LLVMGeneratorArray.assignArray(variableName, array, VariableType.FLOAT);
                } else if (Objects.nonNull(tmpObject.numberLiteral().ScientificNumberLiteral())) {
                    // TODO
                }
            } else {
                ErrorMessages.error(ctx.getStart().getLine(), "Temporary array can store only numbers");
            }
        }
    }

    private String[] mapLiteralListToArray(List<SimpleLangParser.LiteralContext> literal, VariableType variableType, SimpleLangParser.AssignmentContext ctx) {
        List<String> arrayElementsList = new ArrayList<>();
        for (SimpleLangParser.LiteralContext element : literal) {
            if (Objects.nonNull(element.numberLiteral())) {
                if (Objects.nonNull(element.numberLiteral().IntegerLiteral())) {
                    if (!variableType.equals(VariableType.INT)) {
                        ErrorMessages.error(ctx.getStart().getLine(), "Incorrect element type inside array");
                    }
                    arrayElementsList.add(element.getText());
                } else if (Objects.nonNull(element.numberLiteral().FloatingPointLiteral())) {
                    if (!variableType.equals(VariableType.FLOAT)) {
                        ErrorMessages.error(ctx.getStart().getLine(), "Incorrect element type inside array");
                    }
                    arrayElementsList.add(element.getText());
                } else if (Objects.nonNull(element.numberLiteral().ScientificNumberLiteral())) {
                    // TODO
                }
            }
        }
        return arrayElementsList.toArray(new String[0]);
    }

    private void assignString(SimpleLangParser.AssignmentContext ctx, String variableName) {
        if (!VariablesContainer.getInstance().variableExists(variableName)) {
            String stringValue = ctx.literal().getText();
            LLVMGeneratorString.declareAndAssignString(variableName, stringValue.substring(1, stringValue.length() - 1));
            VariablesContainer.getInstance().putVariable(variableName, VariableType.STRING);
        } else {
            if (VariablesContainer.getInstance().getVariableType(variableName).equals(VariableType.STRING)) {
                String stringValue = ctx.literal().getText();
                LLVMGeneratorString.reasignString(variableName, stringValue.substring(1, stringValue.length() - 1));
            } else {
                ErrorMessages.error(ctx.getStart().getLine(), "Cannot string to variable of different type");
            }
        }
    }

    private void assignNumber(String variableName) {
        Value value = VariablesContainer.getInstance().popFromStack();

        if (!VariablesContainer.getInstance().variableExists(variableName)) {
            if (value.getType().equals(VariableType.INT)) {
                LLVMGeneratorBase.declareInteger(variableName);
            } else if (value.getType().equals(VariableType.FLOAT)) {
                LLVMGeneratorBase.declareDouble(variableName);
            }
            VariablesContainer.getInstance().putVariable(variableName, value.getType());
        }

        if (value.getType().equals(VariableType.INT)) {
            LLVMGeneratorBase.assignInteger(variableName, value.getName());
        } else if (value.getType().equals(VariableType.FLOAT)) {
            LLVMGeneratorBase.assignDouble(variableName, value.getName());
        }
    }

}
