package org.simplelang.listener;

import org.simplelang.listener.container.VariablesContainer;
import org.simplelang.listener.container.base.Value;
import org.simplelang.listener.container.base.VariableType;
import org.simplelang.llvm.LLVMGenerator;

public class CastingListener extends org.simplelang.SimpleLangBaseListener {

    @Override
    public void exitToIntCasting(org.simplelang.SimpleLangParser.ToIntCastingContext ctx) {
        Value v = VariablesContainer.getInstance().popFromStack();
        LLVMGenerator.fptosi(v.getName());
        VariablesContainer.getInstance().pushToStack(new Value("%" + (LLVMGenerator.reg - 1), VariableType.INT));
    }

    @Override
    public void exitToFloatCasting(org.simplelang.SimpleLangParser.ToFloatCastingContext ctx) {
        Value v = VariablesContainer.getInstance().popFromStack();
        LLVMGenerator.sitofp(v.getName());
        VariablesContainer.getInstance().pushToStack(new Value("%" + (LLVMGenerator.reg - 1), VariableType.FLOAT));
    }

}
