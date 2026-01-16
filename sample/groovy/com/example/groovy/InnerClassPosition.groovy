package com.example.groovy

class InnerClassPosition {
    static class Foo {
        Foo() {
            this(0)
        }

        Foo(var a) {}

        static class Bar {}
    }
}
