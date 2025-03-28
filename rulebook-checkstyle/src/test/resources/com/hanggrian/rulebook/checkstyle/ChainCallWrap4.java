package com.hanggrian.rulebook.checkstyle;

class ChainCallWrap {
    void foo() {
        new Baz()
            .baz().baz
            .baz();
    }

    static class Baz {
        Baz baz = this;

        Baz baz() {
            return this;
        }
    }
}
