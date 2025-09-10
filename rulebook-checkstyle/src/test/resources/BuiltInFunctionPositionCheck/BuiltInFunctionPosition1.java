package com.hanggrian.rulebook.checkstyle.checks;

class BuiltInFunctionPosition {
    class Foo {
        void bar() {}

        void baz() {}

        @Override
        String toString() {
            return "foo";
        }

        @Override
        int hashCode() {
            return 0;
        }
    }
}
