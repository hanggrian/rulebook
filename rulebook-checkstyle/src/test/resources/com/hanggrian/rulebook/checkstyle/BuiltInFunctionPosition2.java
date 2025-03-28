package com.hanggrian.rulebook.checkstyle;

class BuiltInFunctionPosition {
    class Foo {
        @Override
        String toString() {
            return "foo";
        }

        void bar() {}

        @Override
        int hashCode() {
            return 0;
        }

        void baz() {}
    }
}
