package com.hanggrian.rulebook.checkstyle;

class CaseSeparator {
    foo(int bar) {
        switch (bar) {
            // Lorem ipsum.
            case 0:
                baz();
            /* Lorem ipsum. */
            case 1:
                baz();
            /** Lorem ipsum. */
            case 2:
                baz();
            default:
                baz();
        }
    }

    void baz() {}
}
