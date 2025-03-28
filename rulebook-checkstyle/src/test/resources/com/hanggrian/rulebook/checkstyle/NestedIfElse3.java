package com.hanggrian.rulebook.checkstyle;

class NestedIfElse {
    void foo() {
        if (true) {
            baz();
        } else {
            baz();
            baz();
        }
    }

    void baz() {}
}
