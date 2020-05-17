package org.simplelang.llvm.comparison.operators;

import java.util.HashMap;
import java.util.Map;

public enum ComparisonOperatorsDouble {
    EQUAL("ueq", "=="),
    NOT_EQUAL("une", "!="),
    GREATER_OR_EQUAL_THAN("uge", ">="),
    GREATER_THAN("ugt", ">"),
    LESS_OR_EQUAL_THAN("ule", "<=>"),
    LESS_THAN("ult", "<");

    private final String name;

    private final String operator;

    private static final Map<String, ComparisonOperatorsDouble> map;

    static {
        map = new HashMap<>();
        for (ComparisonOperatorsDouble v : ComparisonOperatorsDouble.values()) {
            map.put(v.operator, v);
        }
    }

    ComparisonOperatorsDouble(final String name, final String operator) {
        this.name = name;
        this.operator = operator;
    }

    public static ComparisonOperatorsDouble findByKey(String key) {
        return map.get(key);
    }

    public String getName() {
        return name;
    }

    public String getOperator() {
        return operator;
    }
}
