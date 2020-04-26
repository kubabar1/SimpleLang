package org.simplelang.listener;

import org.apache.commons.io.FilenameUtils;
import org.simplelang.llvm.LLVMGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class ProgramListener extends org.simplelang.SimpleLangBaseListener {

    private File inputFile;

    public ProgramListener(File inputFile) {
        this.inputFile = inputFile;
    }

    @Override
    public void exitProgram(org.simplelang.SimpleLangParser.ProgramContext ctx) {
        try (PrintWriter out = new PrintWriter(FilenameUtils.removeExtension("test.xml") + ".ll")) {
            out.println(LLVMGenerator.generate());
        } catch (FileNotFoundException e) {
            System.err.println("Error during writing compiled code to file");
        }
    }

}
