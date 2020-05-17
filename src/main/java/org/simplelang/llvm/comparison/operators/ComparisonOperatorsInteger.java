package org.simplelang.llvm.comparison.operators;

import java.util.HashMap;
import java.util.Map;

public enum ComparisonOperatorsInteger {
    EQUAL("eq", "=="),
    NOT_EQUAL("ne", "!="),
    GREATER_OR_EQUAL_THAN("sge", ">="),
    GREATER_THAN("sgt", ">"),
    LESS_OR_EQUAL_THAN("sle", "<="),
    LESS_THAN("slt", "<");

    private final String name;

    private final String operator;

    private static final Map<String, ComparisonOperatorsInteger> map;

    static {
        map = new HashMap<>();
        for (ComparisonOperatorsInteger v : ComparisonOperatorsInteger.values()) {
            map.put(v.operator, v);
        }
    }

    ComparisonOperatorsInteger(final String name, final String operator) {
        this.name = name;
        this.operator = operator;
    }

    public static ComparisonOperatorsInteger findByKey(String key) {
        return map.get(key);
    }

    public String getName() {
        return name;
    }

    public String getOperator() {
        return operator;
    }
}
