package com.hanggrian.rulebook.checkstyle.checks;

class RedundantDefault {
    void foo(int bar) {
        switch (bar) {
            default:
                baz();
                break;
        }
    }

    void baz() {}
}
