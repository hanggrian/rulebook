package com.hanggrian.rulebook.checkstyle.checks;

class ChainCallWrap {
    void foo() {
        baz()
            .baz().baz
            .baz();
    }

    ChainCallWrap baz() {
        return this;
    }
}
