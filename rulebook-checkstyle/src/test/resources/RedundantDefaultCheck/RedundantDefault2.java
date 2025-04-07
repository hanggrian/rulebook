package com.hanggrian.rulebook.checkstyle;

class RedundantDefault {
    void foo(int bar) {
        switch (bar) {
            case 0:
                throw new Exception();
            case 1:
                return;
            default:
                baz();
                break;
        }
    }

    void baz() {}
}
