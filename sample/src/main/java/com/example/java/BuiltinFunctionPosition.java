package com.example.java;

public class BuiltinFunctionPosition {
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
        public boolean equals(Object obj) {
            return false;
        }
    }
}
