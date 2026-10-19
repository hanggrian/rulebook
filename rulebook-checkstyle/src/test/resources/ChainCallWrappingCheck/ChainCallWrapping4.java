package com.hanggrian.rulebook.checkstyle.checks;

class ChainCallWrapping {
    void foo() {
        baz()
            .baz().baz
            .baz();
    }

    ChainCallWrap baz() {
        return this;
    }
}
