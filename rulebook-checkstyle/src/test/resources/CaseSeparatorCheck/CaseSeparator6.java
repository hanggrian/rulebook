package com.hanggrian.rulebook.checkstyle.checks;

class CaseSeparator {
    void foo(int bar) {
        switch (bar) {
            case 0:
            case 1: baz();
            case 2: baz();
        }
    }

    void baz() {}
}
