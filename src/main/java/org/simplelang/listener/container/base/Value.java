package org.simplelang.listener.container.base;

public class Value {

    private String name;

    private VariableType type;

    public Value(String name, VariableType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public VariableType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Value{" +
                "name='" + name + '\'' +
                ", type=" + type +
                '}';
    }

}