package org.simplelang.listener.functions;

public enum BuildInFunctions {
    PRINT("print"),
    READ("read"),
    SIN("sin"),
    COS("cos"),
    TAN("tan"),
    CTG("ctg");

    private final String name;

    BuildInFunctions(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
