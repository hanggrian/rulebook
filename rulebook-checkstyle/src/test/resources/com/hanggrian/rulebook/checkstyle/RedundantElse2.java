package com.hanggrian.rulebook.checkstyle;

class RedundantElse {
    void foo() {
        if (true) {
            throw new Exception();
        } else if (false) {
            return;
        } else {
            baz();
        }
    }

    void baz() {}
}
