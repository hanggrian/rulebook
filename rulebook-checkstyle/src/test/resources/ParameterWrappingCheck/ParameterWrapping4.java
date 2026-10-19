package com.hanggrian.rulebook.checkstyle.checks;

class ParameterWrapping {
    void foo() {
        new StringBuilder()
            .append(1)
            .append("Hello", 1, 2);
    }
}
