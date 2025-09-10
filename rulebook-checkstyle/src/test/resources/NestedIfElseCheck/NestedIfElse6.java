package com.hanggrian.rulebook.checkstyle.checks;

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
