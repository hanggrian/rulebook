package com.example.java;

public class MemberOrder {
    public static class Foo {
        public int bar = 0;

        static {
            int a = 0;
        }

        public Foo() {
            this(0);
        }

        public Foo(int a) {}

        public void baz() {}
    }
}
