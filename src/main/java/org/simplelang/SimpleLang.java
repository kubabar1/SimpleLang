package org.simplelang;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimpleLang {

    public static void main(String[] args) {
        try {
            CharStream input = CharStreams.fromFileName("test.smplang");
            org.simplelang.SimpleLangLexer lexer = new org.simplelang.SimpleLangLexer(input);
            CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
            org.simplelang.SimpleLangParser parser = new org.simplelang.SimpleLangParser(commonTokenStream);
            parser.addParseListener(new SimpleLangCustomListener());
            parser.program();
        } catch (IOException ex) {
            Logger.getLogger(SimpleLang.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
