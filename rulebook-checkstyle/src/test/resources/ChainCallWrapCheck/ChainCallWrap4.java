package com.hanggrian.rulebook.checkstyle.checks;

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
