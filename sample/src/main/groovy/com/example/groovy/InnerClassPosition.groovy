package com.example.groovy

class InnerClassPosition {
    static class Foo {
        Foo() {
            this(0)
        }

        Foo(int a) {}

        static class Bar {}
    }
}
