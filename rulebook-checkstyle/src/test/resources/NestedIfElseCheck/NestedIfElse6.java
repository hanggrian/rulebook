package com.hanggrian.rulebook.checkstyle;

class NestedIfElse {
    void foo() {
        if (true) {
            baz();
            baz();
        }

        // Lorem ipsum.
    }

    void baz() {}
}
