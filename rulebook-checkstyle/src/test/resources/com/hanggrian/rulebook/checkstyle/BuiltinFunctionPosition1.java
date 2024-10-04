package com.hanggrian.rulebook.checkstyle;

public class BuiltinFunctionPosition {
    public class Foo {
        public void bar() {}

        public void baz() {}

        @Override
        public String toString() {
            return "foo";
        }

        @Override
        public int hashCode() {
            return 0;
        }
    }
}
