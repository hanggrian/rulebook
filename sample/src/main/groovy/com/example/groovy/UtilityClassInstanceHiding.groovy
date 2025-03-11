package com.example.groovy

class UtilityClassInstanceHiding {
    final static class Foo {
        private Foo() {}

        static bar() {}
    }
}
