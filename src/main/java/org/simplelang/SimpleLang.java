package org.simplelang;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
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
            parser.program();
        } catch (IOException ex) {
            Logger.getLogger(SimpleLang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
