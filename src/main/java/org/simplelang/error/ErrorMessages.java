package org.simplelang.error;

public class ErrorMessages {

    public static void error(int line, String msg) {
        System.err.println("Error, line " + line + ", " + msg);
        System.exit(1);
    }

}
