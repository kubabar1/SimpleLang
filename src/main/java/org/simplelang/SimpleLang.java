package org.simplelang;

import org.antlr.v4.runtime.*;
import org.graalvm.compiler.core.common.cfg.Loop;
import org.simplelang.error.SimpleLangErrorListener;
import org.simplelang.listener.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleLang {

    public static void main(String[] args) {
        try {
            String filePath = "test.smplang";
            File inputFile = Paths.get(filePath).toFile();
            CharStream input = CharStreams.fromFileName(inputFile.getName());
            org.simplelang.SimpleLangLexer lexer = new org.simplelang.SimpleLangLexer(input);
            CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
            org.simplelang.SimpleLangParser parser = new org.simplelang.SimpleLangParser(commonTokenStream);
            parser.addParseListener(new ProgramListener(inputFile));
            parser.addParseListener(new FunctionListener());
            parser.addParseListener(new ExpressionListener());
            parser.addParseListener(new AssignmentListener());
            parser.addParseListener(new CastingListener());
            parser.addParseListener(new ConditionListener());
            parser.addParseListener(new IfStatementListener());
            parser.addParseListener(new LoopListener());
            parser.addErrorListener(new SimpleLangErrorListener());
            parser.program();
        } catch (IOException ex) {
            Logger.getLogger(SimpleLang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
