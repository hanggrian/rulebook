package com.hanggrian.rulebook.checkstyle.checks;

class NoRedundantDefault {
    void foo(int bar) {
        switch (bar) {
            case 0:
                baz();
                break;
            case 1:
                baz();
                break;
            default:
                baz();
                break;
        }
    }

    void baz() {}
}
