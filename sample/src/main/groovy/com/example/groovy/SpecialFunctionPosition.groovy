package com.example.groovy

class SpecialFunctionPosition {
    static class Foo {
        void bar() {}

        @Override
        String toString() {
            return "baz"
        }

        @Override
        int hashCode() {
            return 0
        }

        @Override
        boolean equals(Object obj) {
            return false
        }
    }
}
