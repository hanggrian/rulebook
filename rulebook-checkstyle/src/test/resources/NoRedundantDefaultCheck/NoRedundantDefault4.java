package com.hanggrian.rulebook.checkstyle.checks;

class NoRedundantDefault {
    void foo(int bar) {
        switch (bar) {
            default:
                baz();
                break;
        }
    }

    void baz() {}
}
