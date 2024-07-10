package com.example.groovy

class UtilityClassConstructorHiding {
    static class Foo {
        private Foo() {}

        static void bar() {}
    }
}
