package com.example.java;

public class SpecialFunctionPosition {
    public static class Foo {
        public void bar() {}

        @Override
        public String toString() {
            return "baz";
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public boolean equals(final Object obj) {
            return false;
        }
    }
}
