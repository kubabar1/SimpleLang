package org.simplelang.utils;

public class FunctionalInterfaceHelper {

    @FunctionalInterface
    public interface ThreeFunction<One, Two, Three, Four> {
        Four apply(One one, Two two, Three three);
    }

    @FunctionalInterface
    public interface FourFunction<One, Two, Three, Four, Five> {
        Five apply(One one, Two two, Three three, Four four);
    }

}
