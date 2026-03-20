package com.hanggrian.rulebook.checkstyle.checks;

class ComplicatedAssignment {
    void foo() {
        int bar = 0;
        bar = bar + 1;
        bar = bar - 1;
        bar = bar * 1;
        bar = bar / 1;
        bar = bar % 1;
    }
}
