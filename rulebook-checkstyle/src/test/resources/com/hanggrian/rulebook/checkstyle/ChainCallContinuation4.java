package com.hanggrian.rulebook.checkstyle;

public class ChainCallContinuation {
    public void foo() {
        new Baz()
            .baz().baz
            .baz();
    }

    public static class Baz {
        public Baz baz = this;

        public Baz baz() {
            return this;
        }
    }
}
