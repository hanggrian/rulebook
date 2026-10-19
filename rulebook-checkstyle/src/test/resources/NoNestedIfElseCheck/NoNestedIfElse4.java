package com.hanggrian.rulebook.checkstyle.checks;

class NoNestedIfElse {
    void foo() {
        if (true) {
            baz();
            baz();
        } else if (false) {
            baz();
        }
    }

    void baz() {}
}
