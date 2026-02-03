package com.hanggrian.rulebook.checkstyle.checks;

class ChainCallWrap {
    void foo() {
        new StringBuilder(
            "Lorem ipsum"
        ).append(1)
            .append(
                2
            );
    }

    void baz() {
        baz()
            .baz(
                new String("Lorem ipsum")
            ).baz();
    }

    ChainCallWrap baz() {
        return this;
    }
}
