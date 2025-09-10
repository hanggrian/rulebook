package com.hanggrian.rulebook.checkstyle.checks;

class BuiltInFunctionPosition {
    class Foo {
        @Override
        String toString() {
            return "foo";
        }

        static void baz() {}
    }
}
