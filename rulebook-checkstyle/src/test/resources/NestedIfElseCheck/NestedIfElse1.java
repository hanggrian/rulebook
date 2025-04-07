package com.hanggrian.rulebook.checkstyle;

class NestedIfElse {
    void foo() {
        if (true) {
        }
    }

    void bar() {
        if (true) {
            baz();
        }
    }

    void baz() {}
}
