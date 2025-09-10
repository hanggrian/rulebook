package com.hanggrian.rulebook.checkstyle.checks;

class NestedIfElse {
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
