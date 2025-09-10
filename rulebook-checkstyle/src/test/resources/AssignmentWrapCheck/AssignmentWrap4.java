package com.hanggrian.rulebook.checkstyle.checks;

class AssignmentWrap {
    void foo(Bar bar) {
        bar
            .baz = 0;
    }

    static class Bar {
        int baz;
    }
}
