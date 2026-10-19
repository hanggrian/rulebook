package com.hanggrian.rulebook.checkstyle.checks;

class NoNestedIfElse {
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
