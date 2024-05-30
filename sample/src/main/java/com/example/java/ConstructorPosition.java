package com.example.java;

public class ConstructorPosition {
    public static class Foo {
        public int bar = 0;

        public Foo() {
            this(0);
        }

        public Foo(final int a) {}

        public void baz() {}
    }
}
