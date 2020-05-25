package org.simplelang.listener.utils;

import org.simplelang.listener.containers.BaseContainer;
import org.simplelang.listener.containers.model.VariableType;
import org.simplelang.llvm.LLVMGeneratorBase;

public class VariableUtils {

    public static String declareValue(String variableName, VariableType variableType) {
        BaseContainer baseContainer = BaseContainer.getInstance();
        String variableId;

        if (baseContainer.isGlobal()) {
            if (!baseContainer.globalNamesContains(variableName)) {
                if (variableType.equals(VariableType.INT)) {
                    baseContainer.putGlobalName(variableName, VariableType.INT);
                    LLVMGeneratorBase.declareInteger(variableName, true);
                } else if (variableType.equals(VariableType.DOUBLE)) {
                    baseContainer.putGlobalName(variableName, VariableType.DOUBLE);
                    LLVMGeneratorBase.declareDouble(variableName, true);
                }
            }
            variableId = "@" + variableName;
        } else {
            if (!baseContainer.localNamesContains(variableName)) {
                if (variableType.equals(VariableType.INT)) {
                    baseContainer.putLocalName(variableName, VariableType.INT);
                    LLVMGeneratorBase.declareInteger(variableName, false);
                } else if (variableType.equals(VariableType.DOUBLE)) {
                    baseContainer.putLocalName(variableName, VariableType.DOUBLE);
                    LLVMGeneratorBase.declareDouble(variableName, false);
                }
            }
            variableId = "%" + variableName;
        }
        return variableId;
    }

    public static VariableType getVariableType(String varName) {
        BaseContainer baseContainer = BaseContainer.getInstance();
        VariableType variableType = null;

        if (baseContainer.globalNamesContains(varName)) {
            variableType = baseContainer.getGlobalNameVariableType(varName);
        } else if (baseContainer.localNamesContains(varName)) {
            variableType = baseContainer.getLocalNameVariableType(varName);
        }
        return variableType;
    }

}
