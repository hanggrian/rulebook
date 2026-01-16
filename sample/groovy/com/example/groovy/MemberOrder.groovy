package com.example.groovy

class MemberOrder {
    static class Foo {
        var bar = 0

        Foo() {
            this(0)
        }

        Foo(var a) {}

        def baz() {}
    }
}
