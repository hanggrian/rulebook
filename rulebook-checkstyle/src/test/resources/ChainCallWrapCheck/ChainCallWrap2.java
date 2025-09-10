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
        new Bar()
            .baz(
                new String("Lorem ipsum")
            )
            .baz(
                new String("Lorem ipsum")
            );
    }

    static class Baz {
        Baz baz() {
            return this;
        }
    }
}
