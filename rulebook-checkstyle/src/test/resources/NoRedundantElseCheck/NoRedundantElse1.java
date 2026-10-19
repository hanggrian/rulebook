package com.hanggrian.rulebook.checkstyle.checks;

class NoRedundantElse {
    void foo() {
        if (true) {
            baz();
        } else if (false) {
            baz();
        } else {
            baz();
        }
    }

    void baz() {}
}
