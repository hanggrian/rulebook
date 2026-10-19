package com.hanggrian.rulebook.checkstyle.checks;

class NoNestedIfElse {
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
