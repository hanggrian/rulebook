package com.example.java;

public class ChainCallWrap {
    public void foo() {
        new StringBuilder(
            "Lorem ipsum"
        ).append(0)
            .append(1);
    }

    public void bar() {
        Baz bar =
            new Baz()
                .baz()
                .baz
                .baz();
    }

    public static class Baz {
        public Baz baz = this;

        public Baz baz() {
            return this;
        }
    }
}
