package org.simplelang.listener;

import org.simplelang.SimpleLangParser;
import org.simplelang.listener.containers.BaseContainer;
import org.simplelang.llvm.loop.LLVMGeneratorLoop;

public class LoopListener extends org.simplelang.SimpleLangBaseListener {

    private BaseContainer baseContainer;

    public LoopListener() {
        this.baseContainer = BaseContainer.getInstance();
    }

    @Override
    public void enterLoopStatement(SimpleLangParser.LoopStatementContext ctx) {
        int brLoop = LLVMGeneratorLoop.startLoop();
        baseContainer.pushToBrIfStack(brLoop);
    }

    @Override
    public void enterBlockLoop(SimpleLangParser.BlockLoopContext ctx) {
        LLVMGeneratorLoop.startLoopBlock();
    }

    @Override
    public void exitBlockLoop(SimpleLangParser.BlockLoopContext ctx) {
        int brLoop = baseContainer.popFromBrIfStack();
        LLVMGeneratorLoop.endLoopBlock(brLoop);
    }

}
