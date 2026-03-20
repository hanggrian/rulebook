package com.hanggrian.rulebook.checkstyle.checks;

class ComplicatedAssignment {
    void foo() {
        int bar = 0;
        bar = bar + 1 - 2 * 3 / 4 % 5;
    }
}
