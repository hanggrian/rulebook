package com.hanggrian.rulebook.checkstyle.checks;

class ChainCallWrap {
    void foo() {
        new StringBuilder(
            "Lorem ipsum"
        )
            .append(0)
            .append(2);
    }

    void bar() {
        baz()
            .baz(
                new String("Lorem ipsum")
            )
            .baz(
                new String("Lorem ipsum")
            );
    }

    ChainCallWrap baz() {
        return this;
    }
}
