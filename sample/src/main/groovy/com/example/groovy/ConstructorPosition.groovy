package com.example.groovy

class ConstructorPosition {
    static class Foo {
        final int bar = 0

        Foo() {
            this(0)
        }

        Foo(int a) {}

        void baz() {}
    }
}
