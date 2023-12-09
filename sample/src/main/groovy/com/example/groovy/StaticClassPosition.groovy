package com.example.groovy

class StaticClassPosition {
    static class Foo {
        Foo() {
            this(Bar.VALUE)
        }

        Foo(int a) {}

        static class Bar {
            static final int VALUE = 0
        }
    }
}
