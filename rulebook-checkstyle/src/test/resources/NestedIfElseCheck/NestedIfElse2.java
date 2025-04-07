package com.hanggrian.rulebook.checkstyle;

class NestedIfElse {
    void foo() {
        if (true) {
            baz();
            baz();
        }
    }

    void baz() {
        if (true) {
            baz(
                0
            );
        }
    }

    void baz() {}
}
