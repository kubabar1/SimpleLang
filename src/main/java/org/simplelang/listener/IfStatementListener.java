package org.simplelang.listener;

import org.simplelang.SimpleLangParser;
import org.simplelang.listener.container.VariablesContainer;
import org.simplelang.llvm.if_stmt.LLVMGeneratorIf;

public class IfStatementListener extends org.simplelang.SimpleLangBaseListener {

    @Override
    public void enterBlockif(SimpleLangParser.BlockifContext ctx) {
        int brIf = LLVMGeneratorIf.ifStart();
        VariablesContainer.getInstance().pushToBrIfStack(brIf);
    }

    @Override
    public void exitBlockif(SimpleLangParser.BlockifContext ctx) {
        int brIf = VariablesContainer.getInstance().popFromBrIfStack();
        LLVMGeneratorIf.ifEnd(brIf);
    }

}
