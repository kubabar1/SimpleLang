package org.simplelang.listener;

import org.simplelang.SimpleLangParser;
import org.simplelang.listener.container.VariablesContainer;
import org.simplelang.llvm.loop.LLVMGeneratorLoop;

public class LoopListener extends org.simplelang.SimpleLangBaseListener {

    @Override
    public void enterLoopStatement(SimpleLangParser.LoopStatementContext ctx) {
        int brLoop = LLVMGeneratorLoop.startLoop();
        VariablesContainer.getInstance().pushToBrIfStack(brLoop);
    }

    @Override
    public void enterBlockLoop(SimpleLangParser.BlockLoopContext ctx) {
        LLVMGeneratorLoop.startLoopBlock();
    }

    @Override
    public void exitBlockLoop(SimpleLangParser.BlockLoopContext ctx) {
        int brLoop = VariablesContainer.getInstance().popFromBrIfStack();
        LLVMGeneratorLoop.endLoopBlock(brLoop);
    }

}
