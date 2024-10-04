package com.hanggrian.rulebook.checkstyle;

public class BuiltinFunctionPosition {
    public class Foo {
        @Override
        public String toString() {
            return "foo";
        }

        public void bar() {}

        @Override
        public int hashCode() {
            return 0;
        }

        public void baz() {}
    }
}
