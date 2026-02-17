package com.example.groovy

class MissingPrivateConstructor {
    static final class Foo {
        private Foo() {}

        static bar() {}
    }

    static class Bar {
        static void main(String[] args) {}
    }
}
