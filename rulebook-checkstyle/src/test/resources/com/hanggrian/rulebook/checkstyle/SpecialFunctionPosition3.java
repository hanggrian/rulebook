package com.hanggrian.rulebook.checkstyle;

public class SpecialFunctionPosition {
    public class Foo {
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
