package com.example.java;

public class UnnecessaryAbstract {
    static class Foo {
        void bar() {}
    }

    abstract static class Baz extends Object {}
}
