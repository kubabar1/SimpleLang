package org.simplelang.listener.containers;

import org.simplelang.listener.containers.model.Value;
import org.simplelang.listener.containers.model.VariableType;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Stack;

public class BaseContainer implements Serializable, Cloneable {

    private static volatile BaseContainer instance;

    private Stack<Value> stack;

    private Stack<Integer> brIfStack;

    private Stack<Integer> brLoopStack;

    private HashMap<String, VariableType> globalNames;

    private HashMap<String, VariableType> localNames;

    private HashSet<String> functions;

    private boolean global;

    private BaseContainer() {
        if (instance != null) {
            throw new IllegalStateException("Cannot create new instance, please use getInstance method instead.");
        }
        this.stack = new Stack<>();
        this.brIfStack = new Stack<>();
        this.brLoopStack = new Stack<>();
        this.globalNames = new HashMap<>();
        this.localNames = new HashMap<>();
        this.functions = new HashSet<>();
        this.global = true;
    }

    public synchronized static BaseContainer getInstance() {
        if (Objects.isNull(instance)) {
            instance = new BaseContainer();
        }
        return instance;
    }

    public void pushToStack(Value value) {
        this.stack.push(value);
    }

    public Value popFromStack() {
        return this.stack.pop();
    }

    public void pushToBrIfStack(Integer value) {
        this.brIfStack.push(value);
    }

    public Integer popFromBrIfStack() {
        return this.brIfStack.pop();
    }

    public void pushToBrLoopStack(Integer value) {
        this.brLoopStack.push(value);
    }

    public Integer popFromBrLoopStack() {
        return this.brLoopStack.pop();
    }

    private Object readResolve() throws ObjectStreamException {
        return getInstance();
    }

    public boolean isGlobal() {
        return this.global;
    }

    public void setGlobalTrue() {
        this.global = true;
    }

    public void setGlobalFalse() {
        this.global = false;
    }

    public void putGlobalName(String globalName, VariableType variableType) {
        this.globalNames.put(globalName, variableType);
    }

    public void putLocalName(String localName, VariableType variableType) {
        this.localNames.put(localName, variableType);
    }

    public void clearLocalNames() {
        this.localNames.clear();
    }

    public boolean localNamesContains(String localName) {
        return this.localNames.containsKey(localName);
    }

    public boolean globalNamesContains(String globalName) {
        return this.globalNames.containsKey(globalName);
    }

    public boolean functionsContains(String functionName) {
        return this.functions.contains(functionName);
    }

    public void addFunction(String functionName) {
        this.functions.add(functionName);
    }

    public VariableType getGlobalNameVariableType(String globalVarName) {
        return this.globalNames.get(globalVarName);
    }

    public VariableType getLocalNameVariableType(String localVarName) {
        return this.localNames.get(localVarName);
    }

    @Override
    public BaseContainer clone() {
        return getInstance();
    }

}
