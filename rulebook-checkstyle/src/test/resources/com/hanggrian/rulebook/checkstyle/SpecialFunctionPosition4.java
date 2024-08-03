package com.hanggrian.rulebook.checkstyle;

public class SpecialFunctionPosition {
    public class Foo {
        @Override
        public String toString() {
            return "foo";
        }

        public static void baz() {}
    }
}
