package com.example.java;

public class ChainCallContinuation {
    public void foo() {
        Bar bar =
            new Bar()
                .baz()
                .baz
                .baz();
    }

    public static class Bar {
        public Bar baz = this;

        public Bar baz() {
            return this;
        }
    }
}
