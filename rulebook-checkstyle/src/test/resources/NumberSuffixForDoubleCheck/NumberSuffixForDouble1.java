package com.hanggrian.rulebook.checkstyle.checks;

class NumberSuffixForDouble {
    double foo = 0d;

    void bar() {
        System.out.println(123d);
    }
}
