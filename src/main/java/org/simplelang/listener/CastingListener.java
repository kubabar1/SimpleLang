package org.simplelang.listener;

import org.simplelang.listener.container.VariablesContainer;
import org.simplelang.listener.container.base.Value;
import org.simplelang.listener.container.base.VariableType;
import org.simplelang.llvm.LLVMGeneratorBase;
import org.simplelang.llvm.casting.LLVMGeneratorCasting;

public class CastingListener extends org.simplelang.SimpleLangBaseListener {

    @Override
    public void exitToIntCasting(org.simplelang.SimpleLangParser.ToIntCastingContext ctx) {
        Value v = VariablesContainer.getInstance().popFromStack();
        LLVMGeneratorCasting.doubleToInteger(v.getName());
        VariablesContainer.getInstance().pushToStack(new Value("%" + (LLVMGeneratorBase.reg - 1), VariableType.INT));
    }

    @Override
    public void exitToFloatCasting(org.simplelang.SimpleLangParser.ToFloatCastingContext ctx) {
        Value v = VariablesContainer.getInstance().popFromStack();
        LLVMGeneratorCasting.integerToDouble(v.getName());
        VariablesContainer.getInstance().pushToStack(new Value("%" + (LLVMGeneratorBase.reg - 1), VariableType.FLOAT));
    }

}
