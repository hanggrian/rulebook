package com.hanggrian.rulebook.checkstyle;

class NestedIfElse {
    void foo() {
        if (true) {
            if (true) {
                baz();
                baz();
            }
        }
        baz();
    }

    void baz() {}
}
