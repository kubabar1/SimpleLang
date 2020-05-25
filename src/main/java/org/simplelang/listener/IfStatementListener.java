package org.simplelang.listener;

import org.simplelang.SimpleLangParser;
import org.simplelang.listener.containers.BaseContainer;
import org.simplelang.llvm.conditional_operation.LLVMGeneratorIf;

public class IfStatementListener extends org.simplelang.SimpleLangBaseListener {

    private BaseContainer baseContainer;

    public IfStatementListener() {
        this.baseContainer = BaseContainer.getInstance();
    }

    @Override
    public void enterBlockif(SimpleLangParser.BlockifContext ctx) {
        int brIf = LLVMGeneratorIf.ifStart();
        baseContainer.pushToBrIfStack(brIf);
    }

    @Override
    public void exitBlockif(SimpleLangParser.BlockifContext ctx) {
        int brIf = baseContainer.popFromBrIfStack();
        LLVMGeneratorIf.ifEnd(brIf);
    }

}
