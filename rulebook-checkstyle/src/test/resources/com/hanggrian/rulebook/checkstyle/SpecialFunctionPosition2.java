package com.hanggrian.rulebook.checkstyle;

public class SpecialFunctionPosition {
    public class Foo {
        @Override
        public String toString() {
            return "baz";
        }

        public void bar() {}
    }
}
