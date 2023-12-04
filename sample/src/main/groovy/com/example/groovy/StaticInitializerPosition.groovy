package com.example.groovy

class StaticInitializerPosition {
    private final int num = InnerClass.DEFAULT_VALUE

    StaticInitializerPosition(String name) {
        num = InnerClass.DEFAULT_VALUE
    }

    void log() {
        System.out.print(InnerClass.DEFAULT_VALUE)
    }

    static class InnerClass {
        static final int DEFAULT_VALUE = 0
    }
}
