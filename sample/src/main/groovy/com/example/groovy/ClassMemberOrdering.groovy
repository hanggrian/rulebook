package com.example.groovy

class ClassMemberOrdering {
    static class Foo {
        var bar = 0

        Foo() {
            this(0)
        }

        Foo(var a) {}

        def baz() {}
    }
}
