package com.hanggrian.rulebook.checkstyle.checks;

class CommonFunctionPosition {
    class Foo {
        @Override
        String toString() {
            return "foo";
        }

        static void baz() {}
    }
}
