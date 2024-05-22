package com.example.java;

public class StaticClassPosition {
    public static class Foo {
        public Foo() {
            this(Bar.VALUE);
        }

        public Foo(final int a) {
        }

        public static class Bar {
            public static final int VALUE = 0;
        }
    }
}
