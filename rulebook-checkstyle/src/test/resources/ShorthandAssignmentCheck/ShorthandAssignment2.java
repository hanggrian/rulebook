package com.hanggrian.rulebook.checkstyle.checks;

class ShorthandAssignment {
    void foo() {
        int bar = 0;
        bar = bar + 1;
        bar = bar - 1;
        bar = bar * 1;
        bar = bar / 1;
        bar = bar % 1;
    }
}
