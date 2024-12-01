package com.hanggrian.rulebook.checkstyle;

public class ChainCallContinuation {
    public void foo() {
        new StringBuilder(
            "Lorem ipsum"
        ).append(0)
            .append(1);
    }

    public void baz() {
        new Bar()
            .baz(
                new String("Lorem ipsum")
            ).baz();
    }

    public static class Baz {
        public Baz baz(String s) {
            return this;
        }

        public Baz baz() {
            return this;
        }
    }
}
