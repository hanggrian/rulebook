package com.hanggrian.rulebook.checkstyle;

class RedundantElse {
    void foo() {
        if (true) {
            return;
        } else if (false) {
            baz();
        } else {
            baz();
        }
    }

    void baz() {}
}
