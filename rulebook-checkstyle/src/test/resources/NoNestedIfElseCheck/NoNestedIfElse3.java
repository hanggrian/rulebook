package com.hanggrian.rulebook.checkstyle.checks;

class NoNestedIfElse {
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
