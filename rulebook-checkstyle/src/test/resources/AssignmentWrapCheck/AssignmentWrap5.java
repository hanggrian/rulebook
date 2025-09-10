package com.hanggrian.rulebook.checkstyle.checks;

class AssignmentWrap {
    void foo(Bar bar) {
        int bar =
            // Comment
            1 +
                2;
        int baz = /* Short comment */
            1 +
                2;
        int qux =
            /** Long comment */1 +
            2;
    }
}
