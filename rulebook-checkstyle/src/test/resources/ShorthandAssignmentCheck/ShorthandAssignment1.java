package com.hanggrian.rulebook.checkstyle.checks;

class ShorthandAssignment {
    void foo() {
        int bar = 0;
        bar += 1;
        bar -= 1;
        bar *= 1;
        bar /= 1;
        bar %= 1;
    }
}
