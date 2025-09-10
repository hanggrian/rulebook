package com.hanggrian.rulebook.checkstyle.checks;

class AssignmentWrap {
    void foo() {
        int bar =
            1 +
                2;
    }
}
