package com.hanggrian.rulebook.checkstyle.checks;

class ComplicatedAssignment {
    void foo() {
        int bar = 0;
        bar += 1;
        bar -= 1;
        bar *= 1;
        bar /= 1;
        bar %= 1;
    }
}
