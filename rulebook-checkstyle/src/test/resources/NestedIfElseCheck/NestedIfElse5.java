package com.hanggrian.rulebook.checkstyle;

class NestedIfElse {
    void foo() {
        if (true) {
            baz();
            return;
        }
    }

    void baz() {}
}
