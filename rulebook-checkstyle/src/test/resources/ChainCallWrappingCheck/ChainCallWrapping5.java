package com.hanggrian.rulebook.checkstyle.checks;

class ChainCallWrapping {
    void foo() {
        new StringBuilder(
            0
        ).append(1).append(2);
    }

    void bar() {
        new StringBuilder(0).append(1).append(
            2
        );
    }
}
