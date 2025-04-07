package com.hanggrian.rulebook.checkstyle;

class RedundantElse {
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
