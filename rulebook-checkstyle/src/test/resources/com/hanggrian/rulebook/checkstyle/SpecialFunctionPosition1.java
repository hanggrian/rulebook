package com.hanggrian.rulebook.checkstyle;

public class SpecialFunctionPosition {
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
