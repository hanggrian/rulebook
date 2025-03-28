package com.example.groovy

class BuiltInFunctionPosition {
    static class Foo {
        def bar() {}

        @Override
        String toString() {
            return 'baz'
        }

        @Override
        int hashCode() {
            return 0
        }

        @Override
        boolean equals(var obj) {
            return false
        }
    }
}
