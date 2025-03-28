package com.hanggrian.rulebook.checkstyle;

class ChainCallWrap {
    void foo() {
        new StringBuilder(
            "Lorem ipsum"
        ).append(0).append(2);
    }

    void bar() {
        new Baz().baz()
            .baz();
    }

    static class Baz {
        Baz baz() {
            return this;
        }
    }
}
