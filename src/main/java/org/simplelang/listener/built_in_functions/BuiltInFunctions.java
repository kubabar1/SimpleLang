package org.simplelang.listener.built_in_functions;

public enum BuiltInFunctions {
    PRINT("print"),
    READ("read"),
    SIN("sin"),
    COS("cos"),
    TAN("tan"),
    CTG("ctg");

    private final String name;

    BuiltInFunctions(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
