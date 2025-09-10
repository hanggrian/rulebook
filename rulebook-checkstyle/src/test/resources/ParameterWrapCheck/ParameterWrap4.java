package com.hanggrian.rulebook.checkstyle.checks;

class ParameterWrap {
    void foo() {
        new StringBuilder()
            .append(1)
            .append("Hello", 1, 2);
    }
}
