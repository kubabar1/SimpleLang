package org.simplelang.listener;

import org.simplelang.listener.containers.BaseContainer;
import org.simplelang.listener.containers.model.Value;
import org.simplelang.listener.containers.model.VariableType;
import org.simplelang.llvm.LLVMGeneratorBase;
import org.simplelang.llvm.casting.LLVMGeneratorCasting;

public class CastingListener extends org.simplelang.SimpleLangBaseListener {

    @Override
    public void exitToIntCasting(org.simplelang.SimpleLangParser.ToIntCastingContext ctx) {
        /*Value v = BaseContainer.getInstance().popFromStack();
        LLVMGeneratorCasting.doubleToInteger(v.getValue());
        BaseContainer.getInstance().pushToStack(new Value("%" + (LLVMGeneratorBase.reg - 1), VariableType.INT));*/
    }

    @Override
    public void exitToFloatCasting(org.simplelang.SimpleLangParser.ToFloatCastingContext ctx) {
        /*Value v = BaseContainer.getInstance().popFromStack();
        LLVMGeneratorCasting.integerToDouble(v.getValue());
        BaseContainer.getInstance().pushToStack(new Value("%" + (LLVMGeneratorBase.reg - 1), VariableType.DOUBLE));*/
    }

}
