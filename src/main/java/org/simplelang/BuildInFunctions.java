package org.simplelang;

public enum BuildInFunctions {
    PRINT("print"),
    READ("read");

    private final String name;

    BuildInFunctions(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
