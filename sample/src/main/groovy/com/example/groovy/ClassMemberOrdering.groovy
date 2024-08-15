package com.example.groovy

class ClassMemberOrdering {
    static class Foo {
        int bar = 0

        Foo() {
            this(0)
        }

        Foo(int a) {}

        void baz() {}
    }
}
