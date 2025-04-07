package com.hanggrian.rulebook.checkstyle;

class RedundantDefault {
    void foo(int bar) {
        switch (bar) {
            case 0:
                return;
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
