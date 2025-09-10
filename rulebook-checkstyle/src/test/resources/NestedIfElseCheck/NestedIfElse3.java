package com.hanggrian.rulebook.checkstyle.checks;

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
