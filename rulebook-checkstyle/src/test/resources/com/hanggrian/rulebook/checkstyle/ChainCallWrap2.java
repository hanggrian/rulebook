package com.hanggrian.rulebook.checkstyle;

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
            .baz();
    }

    static class Baz {
        Baz baz() {
            return this;
        }
    }
}
