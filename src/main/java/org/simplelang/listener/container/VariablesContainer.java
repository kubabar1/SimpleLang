package org.simplelang.listener.container;

import org.simplelang.listener.container.base.Value;
import org.simplelang.listener.container.base.VariableType;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Objects;
import java.util.Stack;

public class VariablesContainer implements Serializable, Cloneable {

    private static volatile VariablesContainer instance;

    private HashMap<String, VariableType> variables;

    private Stack<Value> stack;

    private Stack<Integer> brstack;

    private VariablesContainer() {
        if (instance != null) {
            throw new IllegalStateException("Cannot create new instance, please use getInstance method instead.");
        }
        this.variables = new HashMap<>();
        this.stack = new Stack<>();
        this.brstack = new Stack<>();
    }

    public synchronized static VariablesContainer getInstance() {
        if (Objects.isNull(instance)) {
            instance = new VariablesContainer();
        }
        return instance;
    }

    public void pushToStack(Value value) {
        this.stack.push(value);
    }

    public Value popFromStack() {
        return this.stack.pop();
    }

    public void pushToBrStack(Integer value) {
        this.brstack.push(value);
    }

    public Integer popFromBrStack() {
        return this.brstack.pop();
    }

    public void putVariable(String variableName, VariableType variableType) {
        this.variables.put(variableName, variableType);
    }

    public VariableType getVariableType(String variableName) {
        return this.variables.get(variableName);
    }

    public boolean variableExists(String variableName) {
        return this.variables.containsKey(variableName);
    }

    private Object readResolve() throws ObjectStreamException {
        return getInstance();
    }

    @Override
    public VariablesContainer clone() {
        return getInstance();
    }

}
