package com.hanggrian.rulebook.checkstyle;

class ParameterWrap {
    void foo() {
        new StringBuilder()
            .append(1)
            .append("Hello", 1, 2);
    }
}
