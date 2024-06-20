package com.example.groovy

class UtilityClassInstanceHiding {
    static class Foo {
        private Foo() {}

        static void bar() {}
    }
}
