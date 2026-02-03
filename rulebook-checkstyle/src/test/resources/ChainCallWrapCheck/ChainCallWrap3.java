package com.hanggrian.rulebook.checkstyle.checks;

class ChainCallWrap {
    void foo() {
        new StringBuilder("Lorem ipsum")
            .append(0)
            .append(1).append(2);
    }

    void bar() {
        baz().baz()
            .baz();
    }

    ChainCallWrap baz() {
        return this;
    }
}
