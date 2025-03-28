package com.hanggrian.rulebook.checkstyle;

class CaseSeparator {
    foo(int bar) {
        switch (bar) {
            case 0:
                baz();

            default:
                baz();
        }
    }

    void baz() {}
}
