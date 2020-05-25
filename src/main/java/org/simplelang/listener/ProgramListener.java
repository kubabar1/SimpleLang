package org.simplelang.listener;

import org.apache.commons.io.FilenameUtils;
import org.simplelang.SimpleLangParser;
import org.simplelang.listener.containers.BaseContainer;
import org.simplelang.llvm.LLVMGeneratorBase;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class ProgramListener extends org.simplelang.SimpleLangBaseListener {

    private File inputFile;

    private BaseContainer baseContainer;

    public ProgramListener(File inputFile) {
        this.inputFile = inputFile;
        this.baseContainer = BaseContainer.getInstance();
    }

    @Override
    public void enterProgram(SimpleLangParser.ProgramContext ctx) {
        LLVMGeneratorBase.closeMain();
        baseContainer.setGlobalTrue();
    }

    @Override
    public void exitProgram(org.simplelang.SimpleLangParser.ProgramContext ctx) {
        try (PrintWriter out = new PrintWriter(FilenameUtils.removeExtension(inputFile.getName()) + ".ll")) {
            out.println(LLVMGeneratorBase.generate());
        } catch (FileNotFoundException e) {
            System.err.println("Error during writing compiled code to file");
        }
    }

}
