package org.simplelang.listener.containers.model;

public class Value {

    private String value;

    private VariableType type;

    public Value(String value, VariableType type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public VariableType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Value{" +
                "value='" + value + '\'' +
                ", type=" + type +
                '}';
    }

}