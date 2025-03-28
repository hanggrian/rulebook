package com.hanggrian.rulebook.checkstyle;

class BuiltInFunctionPosition {
    class Foo {
        @Override
        String toString() {
            return "foo";
        }

        static void baz() {}
    }
}
