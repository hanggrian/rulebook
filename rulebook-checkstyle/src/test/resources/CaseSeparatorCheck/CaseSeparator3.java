package com.hanggrian.rulebook.checkstyle;

class CaseSeparator {
    void foo(int bar) {
        switch (bar) {
            case 0:
                baz();
                break;
            default:
                baz();
                break;
        }
    }

    void baz() {}
}
