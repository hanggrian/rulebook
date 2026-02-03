package com.hanggrian.rulebook.checkstyle.checks;

class CommonFunctionPosition {
    class Foo {
        @Override
        String toString() {
            return "foo";
        }

        @Override
        int hashCode() {
            return 0;
        }

        @Override
        boolean equals(Object obj) {
            return false;
        }
    }
}
